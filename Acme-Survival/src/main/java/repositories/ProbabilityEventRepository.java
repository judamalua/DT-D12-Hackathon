
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ProbabilityEvent;

@Repository
public interface ProbabilityEventRepository extends JpaRepository<ProbabilityEvent, Integer> {

}
