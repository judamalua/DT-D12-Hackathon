
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.RoomDesign;

@Repository
public interface RoomDesignRepository extends JpaRepository<RoomDesign, Integer> {

}
