
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

import repositories.ToolRepository;
import domain.Event;
import domain.Item;
import domain.ProbabilityItem;
import domain.Tool;

@Service
@Transactional
public class ToolService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ToolRepository			toolRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ItemDesignService		itemDesignService;

	@Autowired
	private EventService			eventService;

	@Autowired
	private ItemService				itemService;

	@Autowired
	private ProbabilityItemService	probabilityItemService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods --------------------------------------------------

	public Tool create() {
		Tool result;

		result = new Tool();

		return result;
	}

	public Collection<Tool> findAll() {

		Collection<Tool> result;

		Assert.notNull(this.toolRepository);
		result = this.toolRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Tool findOne(final int toolId) {

		Tool result;

		result = this.toolRepository.findOne(toolId);

		return result;

	}

	public Tool save(final Tool tool) {

		Assert.notNull(tool);

		Tool result;
		final Collection<Event> events;
		final Collection<ProbabilityItem> propabilityItems;

		result = this.toolRepository.save(tool);

		events = this.itemDesignService.findEventsByItemDesign(tool.getId());
		propabilityItems = this.itemDesignService.findProbabilityItemsByItemDesign(tool.getId());

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

	public void delete(final Tool tool) {

		Assert.notNull(tool);
		Assert.isTrue(tool.getId() != 0);

		Assert.isTrue(this.toolRepository.exists(tool.getId()));
		final Collection<Event> events;
		final Collection<ProbabilityItem> propabilityItems;
		final Collection<Item> items;

		events = this.itemDesignService.findEventsByItemDesign(tool.getId());
		propabilityItems = this.itemDesignService.findProbabilityItemsByItemDesign(tool.getId());
		items = this.findItemsByTool(tool.getId());

		for (final Event event : events) {
			event.setItemDesign(null);
			this.eventService.save(event);
		}

		for (final ProbabilityItem probabilityItem : propabilityItems)
			this.probabilityItemService.delete(probabilityItem);

		for (final Item item : items)
			this.itemService.delete(item);

		this.toolRepository.delete(tool);

	}
	public Collection<Item> findItemsByTool(final int toolId) {
		Collection<Item> result;

		result = this.toolRepository.findItemsByTool(toolId);

		return result;
	}

	public Page<Tool> findFinal(final Pageable pageable) {
		Page<Tool> result;

		result = this.toolRepository.findFinal(pageable);

		return result;
	}

	public Page<Tool> findNotFinal(final Pageable pageable) {
		Page<Tool> result;

		result = this.toolRepository.findNotFinal(pageable);

		return result;
	}

	public Tool reconstruct(final Tool tool, final BindingResult binding) {
		Tool result;

		if (tool.getId() == 0)
			result = tool;
		else {
			result = this.toolRepository.findOne(tool.getId());

			result.setCapacity(tool.getCapacity());
			result.setDescription(tool.getDescription());
			result.setFinalMode(tool.getFinalMode());
			result.setImageUrl(tool.getImageUrl());
			result.setLuck(tool.getLuck());
			result.setName(tool.getName());
			result.setStrength(tool.getStrength());

		}
		this.validator.validate(result, binding);
		this.toolRepository.flush();

		return result;
	}
}
