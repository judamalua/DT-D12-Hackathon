
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AttackRepository;
import domain.Attack;

@Service
@Transactional
public class AttackService {

	// Managed repository --------------------------------------------------

	@Autowired
	private AttackRepository	attackRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private MoveService			moveService;


	// Simple CRUD methods --------------------------------------------------

	public Attack create() {
		Attack result;

		result = new Attack();

		return result;
	}

	public Collection<Attack> findAll() {

		Collection<Attack> result;

		Assert.notNull(this.attackRepository);
		result = this.attackRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Attack findOne(final int attackId) {

		Attack result;

		result = this.attackRepository.findOne(attackId);

		return result;

	}

	public Attack save(final Attack attack) {

		Assert.notNull(attack);

		Attack result;
		Date startMoment, endMoment;
		Long time;

		startMoment = new Date(System.currentTimeMillis() - 10);
		time = this.moveService.timeBetweenLocations(attack.getAttacker().getLocation(), attack.getDefendant().getLocation());
		endMoment = new Date(System.currentTimeMillis() + time);

		if (attack.getId() == 0) {
			attack.setStartDate(startMoment);
			attack.setEndMoment(endMoment);
		}

		result = this.attackRepository.save(attack);

		return result;

	}
	public void delete(final Attack attack) {

		assert attack != null;
		assert attack.getId() != 0;

		Assert.isTrue(this.attackRepository.exists(attack.getId()));

		this.attackRepository.delete(attack);

	}

	//Business methods --------------------
	/**
	 * This methot returns the number of resources stolen in the Attack. If the attacker loses, it returns 0 or a negative number.
	 * 
	 * @param attack
	 * @return resources stolen of the Attack. If is a lost, then it returns 0.
	 */
	public Integer getResourcesOfAttack(final Attack attack) {
		Integer strengthSumAttacker, strengthSumDefendant;
		Integer result;

		strengthSumAttacker = this.getStrengthSumByRefuge(attack.getAttacker().getId());
		strengthSumDefendant = this.getStrengthSumByRefuge(attack.getDefendant().getId());

		result = strengthSumAttacker - strengthSumDefendant;

		return result;

	}
	public Collection<Attack> findAttacksByAttacker(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Attack> result;

		result = this.attackRepository.findAttacksByAttacker(refugeId);

		return result;
	}

	public Collection<Attack> findAttacksByDefendant(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Attack> result;

		result = this.attackRepository.findAttacksByDefendant(refugeId);

		return result;
	}

	public Integer getStrengthSumByRefuge(final int refugeId) {
		Integer result;

		result = this.attackRepository.getStrengthSumByRefuge(refugeId);

		return result;
	}
}
