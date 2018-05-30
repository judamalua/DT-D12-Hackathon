
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

	@Query("select c from Character c where c.shelter.id=?1")
	Collection<Character> findCharactersByShelter(int shelterId);

	@Query("select c from Character c where c.shelter.id=?1")
	Page<Character> findCharactersByShelterPageable(int shelterId, Pageable pageable);

	@Query("select c from Character c where c.room.id=?1")
	Collection<Character> findCharactersByRoom(int roomId);

	@Query("select c from Character c where c.shelter.id=?1 and c.currentlyInGatheringMission = true")
	Collection<Character> findCharactersCurrentlyInMission(int shelterId);

	@Query("select c from Character c where c.shelter.id=?1 and c.gatherNotificated = false")
	Collection<Character> findCharactersNotNotificatedOfGather(int shelterId);

	//Dashboard queries

	@Query("select count(c) from Character c")
	String findNumCharacters();

}
