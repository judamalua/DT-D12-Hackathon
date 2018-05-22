
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ResourceRepository;
import domain.Event;
import domain.ProbabilityItem;
import domain.Resource;

@Service
@Transactional
public class ResourceService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ResourceRepository		resourceRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ItemDesignService		itemDesignService;

	@Autowired
	private ProbabilityItemService	probabilityItemService;

	@Autowired
	private EventService			eventService;

	@Autowired
	private Validator				validator;


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

		Assert.notNull(resource);

		Resource result;
		final Collection<Event> events;
		final Collection<ProbabilityItem> propabilityItems;

		result = this.resourceRepository.save(resource);

		events = this.itemDesignService.findEventsByItemDesign(resource.getId());
		propabilityItems = this.itemDesignService.findProbabilityItemsByItemDesign(resource.getId());

		for (final Event event : events) {
			event.setItemDesign(result);
			this.eventService.save(event);
		}

		for (final ProbabilityItem probabilityItem : propabilityItems) {
			probabilityItem.setItemDesign(result);
			this.probabilityItemService.save(probabilityItem);
		}

		return result;

	}

	public void delete(final Resource resource) {

		Assert.notNull(resource);
		Assert.isTrue(resource.getId() != 0);

		Assert.isTrue(this.resourceRepository.exists(resource.getId()));

		final Collection<Event> events;
		final Collection<ProbabilityItem> propabilityItems;

		this.resourceRepository.delete(resource);

		events = this.itemDesignService.findEventsByItemDesign(resource.getId());
		propabilityItems = this.itemDesignService.findProbabilityItemsByItemDesign(resource.getId());

		for (final Event event : events) {
			event.setItemDesign(null);
			this.eventService.save(event);
		}

		for (final ProbabilityItem probabilityItem : propabilityItems)
			this.probabilityItemService.delete(probabilityItem);

	}

	public Page<Resource> findFinal(final Pageable pageable) {
		Page<Resource> result;

		result = this.resourceRepository.findFinal(pageable);

		return result;
	}

	public Page<Resource> findNotFinal(final Pageable pageable) {
		Page<Resource> result;

		result = this.resourceRepository.findNotFinal(pageable);

		return result;
	}

	public Resource reconstruct(final Resource resource, final BindingResult binding) {
		Resource result;

		if (resource.getId() == 0)
			result = resource;
		else {
			result = this.resourceRepository.findOne(resource.getId());

			result.setName(resource.getName());
			result.setDescription(resource.getDescription());
			result.setFinalMode(resource.getFinalMode());
			result.setImageUrl(resource.getImageUrl());
			result.setMetal(resource.getMetal());
			result.setWater(resource.getWater());
			result.setWood(resource.getWood());
			result.setFood(resource.getFood());

		}

		this.validator.validate(result, binding);
		this.resourceRepository.flush();

		return result;
	}
}
