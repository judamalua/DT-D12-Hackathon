
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ProbabilityItem;

@Repository
public interface ProbabilityItemRepository extends JpaRepository<ProbabilityItem, Integer> {

}
