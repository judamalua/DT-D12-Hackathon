
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Refuge;

@Repository
public interface RefugeRepository extends JpaRepository<Refuge, Integer> {

}
