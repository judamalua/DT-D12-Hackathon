
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Tool;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer> {

}
