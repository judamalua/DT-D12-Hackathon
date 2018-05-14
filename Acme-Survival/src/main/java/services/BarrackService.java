
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BarrackRepository;
import domain.Actor;
import domain.Barrack;
import domain.Designer;

@Service
@Transactional
public class BarrackService {

	// Managed repository --------------------------------------------------

	@Autowired
	private BarrackRepository	barrackRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	public Barrack create() {
		Barrack result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		// Checking that the user trying to create a product is a manager.
		Assert.isTrue(actor instanceof Designer);

		result = new Barrack();

		// Setting final mode to false due to when the designer is creating the room design it cannot be in final mode
		result.setFinalMode(false);

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
		Actor actor;
		Barrack result;

		actor = this.actorService.findActorByPrincipal();

		// To edit or create a room design the principal must be a designer
		Assert.isTrue(actor instanceof Designer);

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
