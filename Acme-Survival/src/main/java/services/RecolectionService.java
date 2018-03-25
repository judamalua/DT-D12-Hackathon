package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RecolectionRepository;
import domain.Recolection;

@Service
@Transactional
public class RecolectionService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RecolectionRepository	recolectionRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Recolection create() {
		Recolection result;

		result = new Recolection();

		return result;
	}

	public Collection<Recolection> findAll() {

		Collection<Recolection> result;

		Assert.notNull(this.recolectionRepository);
		result = this.recolectionRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Recolection findOne(final int recolectionId) {

		Recolection result;

		result = this.recolectionRepository.findOne(recolectionId);

		return result;

	}

	public Recolection save(final Recolection recolection) {

		assert recolection != null;

		Recolection result;

		result = this.recolectionRepository.save(recolection);

		return result;

	}

	public void delete(final Recolection recolection) {

		assert recolection != null;
		assert recolection.getId() != 0;

		Assert.isTrue(this.recolectionRepository.exists(recolection.getId()));

		this.recolectionRepository.delete(recolection);

	}
}

