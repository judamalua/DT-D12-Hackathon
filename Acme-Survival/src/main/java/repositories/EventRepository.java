
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;
import domain.Item;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	
	@Query("select e from Event e where e.finalMode=true")
	Collection<Event> findFinal();
	
	@Query("select e from Event e where e.finalMode=false")
	Collection<Event> findNotFinal();
	
	@Query("select e from Event e where e.finalMode=true")
	Page<Event> findFinal(Pageable pageable);
	
	@Query("select e from Event e where e.finalMode=false")
	Page<Event> findNotFinal(Pageable pageable);
	
	
}
