
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	@Query("select i from Inventory i where i.refuge.id=?1")
	Inventory findInventoryByRefuge(int refugeId);
}
