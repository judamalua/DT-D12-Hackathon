
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
