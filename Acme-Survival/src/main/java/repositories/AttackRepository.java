
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
	Attack findAttacksByAttacker(int shelterId);

	@Query("select a from Attack a where a.player.id=?1")
	Attack findAttackByPlayer(int playerId);

	@Query("select a from Attack a where a.defendant.id=?1")
	Collection<Attack> findAttacksByDefendant(int shelterId);

	@Query("select sum(c.strength) from Character c where c.shelter.id = ?1 and c.currentlyInGatheringMission = FALSE")
	Integer getStrengthSumByShelter(int shelterId);

	@Query("select c from Character c where c.shelter.id = ?1 and c.currentlyInGatheringMission = FALSE")
	Collection<Character> findCharactersForAttackMission(int shelterId);
	//Query("select a from Attack a where a.endMoment > ?1 and a.attacker.id = ?2")
	//Collection<Attack> findAttacksThatEndsAfterDate(Date now, int shelterId);

	//@Query("select a from Attack a where a.attacker.id = ?1 or a.defendant.id = ?1")
	//Page<Attack> findAllAttacksByPlayer(int shelterId, Pageable pageable);

	//Dashboard queries

	@Query("select a.attacker.name, count(a) from Attack a group by a.attacker")
	Collection<Collection<String>> findNumAttacksByShelter();

	@Query("select a.defendant.name, count(a) from Attack a group by a.defendant")
	Collection<Collection<String>> findNumDefensesByShelter();
}
