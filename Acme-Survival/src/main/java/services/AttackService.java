package services;

import java.util.Collection;

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

		assert attack != null;

		Attack result;

		result = this.attackRepository.save(attack);

		return result;

	}

	public void delete(final Attack attack) {

		assert attack != null;
		assert attack.getId() != 0;

		Assert.isTrue(this.attackRepository.exists(attack.getId()));

		this.attackRepository.delete(attack);

	}
}

