
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Attack;

@Repository
public interface AttackRepository extends JpaRepository<Attack, Integer> {

}
