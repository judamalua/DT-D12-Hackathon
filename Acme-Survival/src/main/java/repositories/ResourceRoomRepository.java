
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ResourceRoom;

@Repository
public interface ResourceRoomRepository extends JpaRepository<ResourceRoom, Integer> {

}
