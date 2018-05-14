
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;
import domain.Tool;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer> {

	@Query("select i from Item i where i.tool.id=?1")
	Collection<Item> findItemsByTool(int toolId);

	@Query("select t from Tool t where t.finalMode=true")
	Page<Tool> findFinal(Pageable pageable);

	@Query("select t from Tool t where t.finalMode=false")
	Page<Tool> findNotFinal(Pageable pageable);
}
