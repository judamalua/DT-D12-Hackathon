
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Query("select r from Room r where r.shelter.id=?1")
	Collection<Room> findRoomsByShelter(int shelterId);

	@Query("select r from Room r where r.shelter.id=?1")
	Page<Room> findRoomsByShelter(int shelterId, Pageable pageable);

	@Query("select r from Room r where r.shelter.id=?1 and (?2=null or r.id!=?2) and (r.roomDesign.maxCapacityCharacters-(select count(c) from Character c where c.room.id=r.id)>0)")
	Page<Room> findRoomsByShelterMove(int shelterId, int characterRoomId, Pageable pageable);

	// Dashboard queries

	@Query("select r.shelter.name, count(r) from Room r group by r.shelter")
	Collection<Collection<String>> findNumRoomsByShelter();

}
