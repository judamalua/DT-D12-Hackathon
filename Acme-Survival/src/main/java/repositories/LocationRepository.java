
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
