
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Attack;
import domain.Character;

@Repository
public interface AttackRepository extends JpaRepository<Attack, Integer> {

	@Query("select a from Attack a where a.attacker.id=?1")
	Attack findAttacksByAttacker(int refugeId);

	@Query("select a from Attack a where a.player.id=?1")
	Attack findAttackByPlayer(int playerId);

	@Query("select a from Attack a where a.defendant.id=?1")
	Collection<Attack> findAttacksByDefendant(int refugeId);

	@Query("select sum(c.strength) from Character c where c.refuge.id = ?1 and c.currentlyInGatheringMission = FALSE")
	Integer getStrengthSumByRefuge(int refugeId);

	@Query("select c from Character c where c.refuge.id = ?1 and c.currentlyInGatheringMission = FALSE")
	Collection<Character> findCharactersForAttackMission(int refugeId);
	//Query("select a from Attack a where a.endMoment > ?1 and a.attacker.id = ?2")
	//Collection<Attack> findAttacksThatEndsAfterDate(Date now, int refugeId);

	//@Query("select a from Attack a where a.attacker.id = ?1 or a.defendant.id = ?1")
	//Page<Attack> findAllAttacksByPlayer(int refugeId, Pageable pageable);

	//Dashboard queries

	@Query("select a.attacker.name, count(a) from Attack a group by a.attacker")
	Collection<String> findNumAttacksByRefuge();

	@Query("select a.defendant.name, count(a) from Attack a group by a.defendant")
	Collection<String> findNumDefensesByRefuge();
}
