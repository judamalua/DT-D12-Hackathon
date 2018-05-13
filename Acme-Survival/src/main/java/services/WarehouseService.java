
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarehouseRepository;
import domain.Actor;
import domain.Designer;
import domain.Warehouse;

@Service
@Transactional
public class WarehouseService {

	// Managed repository --------------------------------------------------

	@Autowired
	private WarehouseRepository	werehouseRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	public Warehouse create() {
		Warehouse result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		// Checking that the user trying to create a product is a manager.
		Assert.isTrue(actor instanceof Designer);

		result = new Warehouse();

		// Setting final mode to false due to when the designer is creating the room design it cannot be in final mode
		result.setFinalMode(false);

		return result;
	}

	public Collection<Warehouse> findAll() {

		Collection<Warehouse> result;

		Assert.notNull(this.werehouseRepository);
		result = this.werehouseRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Warehouse findOne(final int werehouseId) {

		Warehouse result;

		result = this.werehouseRepository.findOne(werehouseId);

		return result;

	}

	public Warehouse save(final Warehouse werehouse) {

		assert werehouse != null;

		Warehouse result;

		result = this.werehouseRepository.save(werehouse);

		return result;

	}

	public void delete(final Warehouse werehouse) {

		assert werehouse != null;
		assert werehouse.getId() != 0;

		Assert.isTrue(this.werehouseRepository.exists(werehouse.getId()));

		this.werehouseRepository.delete(werehouse);

	}
}
