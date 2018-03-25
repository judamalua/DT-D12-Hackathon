
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Werehouse;

@Repository
public interface WerehouseRepository extends JpaRepository<Werehouse, Integer> {

}
