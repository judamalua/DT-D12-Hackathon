
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select i from Item i where i.refuge.id=?1")
	Collection<Item> findItemsByRefuge(int refugeId);

	@Query("select i from Item i where i.refuge.id=?1")
	Page<Item> findItemsByRefuge(int refugeId, Pageable pageable);

}
