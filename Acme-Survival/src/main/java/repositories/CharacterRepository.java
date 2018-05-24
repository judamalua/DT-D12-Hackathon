
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {

	@Query("select c from Character c where c.refuge.id=?1")
	Collection<Character> findCharactersByRefuge(int refugeId);

	@Query("select c from Character c where c.refuge.id=?1")
	Page<Character> findCharactersByRefugePageable(int refugeId, Pageable pageable);

	@Query("select c from Character c where c.room.id=?1")
	Collection<Character> findCharactersByRoom(int roomId);

	@Query("select c from Character c where c.refuge.id=?1 and c.currentlyInGatheringMission = true")
	Collection<Character> findCharactersCurrentlyInMission(int refugeId);

	@Query("select c from Character c where c.refuge.id=?1 and c.gatherNotificated = false")
	Collection<Character> findCharactersNotNotificatedOfGather(int refugeId);
}
