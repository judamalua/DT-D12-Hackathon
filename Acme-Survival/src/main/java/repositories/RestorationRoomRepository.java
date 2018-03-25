
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.RestorationRoom;

@Repository
public interface RestorationRoomRepository extends JpaRepository<RestorationRoom, Integer> {

}
