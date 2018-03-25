package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BarrackRepository;
import domain.Barrack;

@Service
@Transactional
public class BarrackService {

	// Managed repository --------------------------------------------------

	@Autowired
	private BarrackRepository	barrackRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Barrack create() {
		Barrack result;

		result = new Barrack();

		return result;
	}

	public Collection<Barrack> findAll() {

		Collection<Barrack> result;

		Assert.notNull(this.barrackRepository);
		result = this.barrackRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Barrack findOne(final int barrackId) {

		Barrack result;

		result = this.barrackRepository.findOne(barrackId);

		return result;

	}

	public Barrack save(final Barrack barrack) {

		assert barrack != null;

		Barrack result;

		result = this.barrackRepository.save(barrack);

		return result;

	}

	public void delete(final Barrack barrack) {

		assert barrack != null;
		assert barrack.getId() != 0;

		Assert.isTrue(this.barrackRepository.exists(barrack.getId()));

		this.barrackRepository.delete(barrack);

	}
}

