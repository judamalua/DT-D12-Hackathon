
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

	@Query("select r from Room r where r.refuge.id=?1")
	Collection<Room> findRoomsByRefuge(int refugeId);

	@Query("select r from Room r where r.refuge.id=?1")
	Page<Room> findRoomsByRefuge(int refugeId, Pageable pageable);

	// Dashboard queries

	@Query("select r.refuge.name, count(r) from Room r group by r.refuge")
	Collection<Collection<String>> findNumRoomsByRefuge();
}
