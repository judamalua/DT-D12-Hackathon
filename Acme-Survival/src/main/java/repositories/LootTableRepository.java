
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

	@Query("select l from LootTable l join l.probabilityEvents e where e.id=?1")
	LootTable findLootTableByProbabilityEvent(int id);

	@Query("select l from LootTable l join l.probabilityItems i where i.id=?1")
	LootTable findLootTableByProbabilityItem(int id);

}
