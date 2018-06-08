
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	@Query("select i from Inventory i where i.shelter.id=?1")
	Inventory findInventoryByShelter(int shelterId);
}
