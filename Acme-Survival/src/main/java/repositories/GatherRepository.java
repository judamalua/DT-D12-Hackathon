
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

	@Query("select g.character from Gather g where g.endMoment > ?1")
	Collection<Character> findCharactersInRecolectionMission(Date date);

	@Query("select g from Gather g where g.character.id = ?1 and g.endMoment > ?2")
	Collection<Gather> findRecolectionNotFinishedByCharacter(int characterId, Date date);

	@Query("select g from Gather g where g.player.id = ?1")
	Page<Gather> findRecolectionsByPlayer(int playerId, Pageable pageable);

}
