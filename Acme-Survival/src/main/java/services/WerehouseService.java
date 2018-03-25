package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WerehouseRepository;
import domain.Werehouse;

@Service
@Transactional
public class WerehouseService {

	// Managed repository --------------------------------------------------

	@Autowired
	private WerehouseRepository	werehouseRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Werehouse create() {
		Werehouse result;

		result = new Werehouse();

		return result;
	}

	public Collection<Werehouse> findAll() {

		Collection<Werehouse> result;

		Assert.notNull(this.werehouseRepository);
		result = this.werehouseRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Werehouse findOne(final int werehouseId) {

		Werehouse result;

		result = this.werehouseRepository.findOne(werehouseId);

		return result;

	}

	public Werehouse save(final Werehouse werehouse) {

		assert werehouse != null;

		Werehouse result;

		result = this.werehouseRepository.save(werehouse);

		return result;

	}

	public void delete(final Werehouse werehouse) {

		assert werehouse != null;
		assert werehouse.getId() != 0;

		Assert.isTrue(this.werehouseRepository.exists(werehouse.getId()));

		this.werehouseRepository.delete(werehouse);

	}
}

