
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Query("select sum(c.strength) from Character c where c.refuge.id = ?1")
	Integer getStrengthSumByRefuge(int refugeId);

	@Query("select a from Attack a where a.endMoment > ?1 and a.attacker.id = ?2")
	Collection<Attack> findAttacksThatEndsAfterDate(Date now, int refugeId);

	@Query("select a from Attack a where a.attacker.id = ?1 or a.defendant.id = ?1")
	Page<Attack> findAllAttacksByPlayer(int refugeId, Pageable pageable);
}
