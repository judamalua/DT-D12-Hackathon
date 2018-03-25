
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ItemDesign;

@Repository
public interface ItemDesignRepository extends JpaRepository<ItemDesign, Integer> {

}
