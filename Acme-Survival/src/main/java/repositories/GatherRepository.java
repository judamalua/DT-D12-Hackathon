
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Character;
import domain.Gather;

@Repository
public interface GatherRepository extends JpaRepository<Gather, Integer> {

	@Query("select g.character from Gather g where g.endMoment > ?1 and g.character.refuge.id = ?2")
	Collection<Character> findCharactersInGatheringMission(Date date, int refugeId);

	@Query("select g from Gather g where g.character.id = ?1 and g.endMoment > ?2")
	Gather findGatherNotFinishedByCharacter(int characterId, Date date);

	@Query("select g from Gather g where g.character.id = ?1 and g.endMoment > ?2")
	Collection<Gather> findGatherCollectionNotFinishedByCharacter(int characterId, Date date);

	@Query("select g from Gather g where g.character.id = ?1 and g.endMoment < CURRENT_TIMESTAMP")
	Gather findGatherFinishedByCharacter(int characterId);

	@Query("select g from Gather g where g.player.id = ?1")
	Page<Gather> findGathersByPlayer(int playerId, Pageable pageable);

	@Query("select g from Gather g where g.player.id = ?1 and g.endMoment < ?2")
	Collection<Gather> findGathersFinishedByPlayer(int playerId, Date date);

	@Query("select g from Gather g where g.player.id = ?1")
	Collection<Gather> findAllGathersOfPlayer(int playerId);

}
