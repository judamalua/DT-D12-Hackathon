package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefugeRepository;
import domain.Refuge;

@Service
@Transactional
public class RefugeService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RefugeRepository	refugeRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Refuge create() {
		Refuge result;

		result = new Refuge();

		return result;
	}

	public Collection<Refuge> findAll() {

		Collection<Refuge> result;

		Assert.notNull(this.refugeRepository);
		result = this.refugeRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Refuge findOne(final int refugeId) {

		Refuge result;

		result = this.refugeRepository.findOne(refugeId);

		return result;

	}

	public Refuge save(final Refuge refuge) {

		assert refuge != null;

		Refuge result;

		result = this.refugeRepository.save(refuge);

		return result;

	}

	public void delete(final Refuge refuge) {

		assert refuge != null;
		assert refuge.getId() != 0;

		Assert.isTrue(this.refugeRepository.exists(refuge.getId()));

		this.refugeRepository.delete(refuge);

	}
}

