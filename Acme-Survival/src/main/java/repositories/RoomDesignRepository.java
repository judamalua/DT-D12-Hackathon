
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RoomDesign;

@Repository
public interface RoomDesignRepository extends JpaRepository<RoomDesign, Integer> {

	@Query("select rd from RoomDesign rd where rd.finalMode=true")
	Collection<RoomDesign> findFinalRoomDesign();

	@Query("select rd from RoomDesign rd where rd.finalMode=false")
	Page<RoomDesign> findDraftRoomDesigns(Pageable pageable);

	@Query("select rd from RoomDesign rd where rd.finalMode=true")
	Page<RoomDesign> findFinalRoomDesigns(Pageable pageable);

	//Dashboard queries

	@Query("select count(r) from RoomDesign r where r.finalMode=true")
	String findNumRoomDesigns();
}
