package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ResourceRepository;
import domain.Resource;

@Service
@Transactional
public class ResourceService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ResourceRepository	resourceRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Resource create() {
		Resource result;

		result = new Resource();

		return result;
	}

	public Collection<Resource> findAll() {

		Collection<Resource> result;

		Assert.notNull(this.resourceRepository);
		result = this.resourceRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Resource findOne(final int resourceId) {

		Resource result;

		result = this.resourceRepository.findOne(resourceId);

		return result;

	}

	public Resource save(final Resource resource) {

		assert resource != null;

		Resource result;

		result = this.resourceRepository.save(resource);

		return result;

	}

	public void delete(final Resource resource) {

		assert resource != null;
		assert resource.getId() != 0;

		Assert.isTrue(this.resourceRepository.exists(resource.getId()));

		this.resourceRepository.delete(resource);

	}
}

