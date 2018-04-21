
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
		final Collection<Item> items;

		result = this.toolRepository.save(tool);

		events = this.itemDesignService.findEventsByItemDesign(tool.getId());
		propabilityItems = this.itemDesignService.findProbabilityItemsByItemDesign(tool.getId());
		items = this.findItemsByTool(tool.getId());

		for (final Event event : events) {
			event.setItemDesign(result);
			this.eventService.save(event);
		}

		for (final ProbabilityItem probabilityItem : propabilityItems) {
			probabilityItem.setItemDesign(result);
			this.probabilityItemService.save(probabilityItem);
		}

		for (final Item item : items) {
			item.setTool(result);
			this.itemService.save(item);
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
}
