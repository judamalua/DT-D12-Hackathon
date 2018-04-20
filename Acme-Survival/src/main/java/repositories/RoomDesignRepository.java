
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RoomDesign;

@Repository
public interface RoomDesignRepository extends JpaRepository<RoomDesign, Integer> {

	@Query("select rd from RoomDesign rd where rd.finalMode=true")
	Collection<RoomDesign> findFinalRoomDesign();
}
