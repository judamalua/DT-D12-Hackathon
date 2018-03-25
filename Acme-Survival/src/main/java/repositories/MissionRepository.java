
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Mission;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {

}
