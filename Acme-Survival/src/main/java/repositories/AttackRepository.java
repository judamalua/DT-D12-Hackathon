
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Attack;

@Repository
public interface AttackRepository extends JpaRepository<Attack, Integer> {

	@Query("select a from Attack a where a.attacker.id=?1")
	Collection<Attack> findAttacksByAttacker(int refugeId);

	@Query("select a from Attack a where a.defendant.id=?1")
	Collection<Attack> findAttacksByDefendant(int refugeId);
}
