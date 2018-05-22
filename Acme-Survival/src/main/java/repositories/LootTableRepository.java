
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LootTable;

@Repository
public interface LootTableRepository extends JpaRepository<LootTable, Integer> {

	@Query("select l from LootTable l where l.finalMode=true")
	Collection<LootTable> findAllFinal();

}
