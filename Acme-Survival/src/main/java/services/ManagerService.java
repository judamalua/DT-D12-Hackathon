package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ManagerRepository	managerRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Manager create() {
		Manager result;

		result = new Manager();

		return result;
	}

	public Collection<Manager> findAll() {

		Collection<Manager> result;

		Assert.notNull(this.managerRepository);
		result = this.managerRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Manager findOne(final int managerId) {

		Manager result;

		result = this.managerRepository.findOne(managerId);

		return result;

	}

	public Manager save(final Manager manager) {

		assert manager != null;

		Manager result;

		result = this.managerRepository.save(manager);

		return result;

	}

	public void delete(final Manager manager) {

		assert manager != null;
		assert manager.getId() != 0;

		Assert.isTrue(this.managerRepository.exists(manager.getId()));

		this.managerRepository.delete(manager);

	}
}

