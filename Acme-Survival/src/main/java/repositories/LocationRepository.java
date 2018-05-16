
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	@Query("select l from Location l where l.finalMode=1")
	Collection<Location> findAllLocationsByFinal();

	@Query("select l from Location l where l.finalMode=0")
	Collection<Location> findAllLocationsByNotFinal();

}
