
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Character;
import domain.Recolection;

@Repository
public interface RecolectionRepository extends JpaRepository<Recolection, Integer> {

	@Query("select r.character from Recolection r where r.endMoment > ?1")
	Collection<Character> findCharactersInRecolectionMission(Date date);

	@Query("select r from Recolection r where r.character.id = ?1 and r.endMoment > ?2")
	Collection<Recolection> findRecolectionNotFinishedByCharacter(int characterId, Date date);

	@Query("select r from Recolection r where r.player.id = ?1")
	Page<Recolection> findRecolectionsByPlayer(int playerId, Pageable pageable);

}
