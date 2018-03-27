package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarehouseRepository;
import domain.Warehouse;

@Service
@Transactional
public class WarehouseService {

	// Managed repository --------------------------------------------------

	@Autowired
	private WarehouseRepository	werehouseRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Warehouse create() {
		Warehouse result;

		result = new Warehouse();

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

