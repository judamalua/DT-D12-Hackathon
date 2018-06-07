
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProbabilityEvent;

@Repository
public interface ProbabilityEventRepository extends JpaRepository<ProbabilityEvent, Integer> {

	@Query("select pe from ProbabilityEvent pe where pe.event.id=?1")
	Collection<ProbabilityEvent> findProbabilityEventByEvent(int eventId);
}
