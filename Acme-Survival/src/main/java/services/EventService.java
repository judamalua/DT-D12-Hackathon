package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import domain.Event;

@Service
@Transactional
public class EventService {

	// Managed repository --------------------------------------------------

	@Autowired
	private EventRepository	eventRepository;
	@Autowired
	private Validator				validator;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Event create() {
		Event result;

		result = new Event();

		return result;
	}

	public Collection<Event> findAll() {

		Collection<Event> result;

		Assert.notNull(this.eventRepository);
		result = this.eventRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Event findOne(final int eventId) {

		Event result;

		result = this.eventRepository.findOne(eventId);

		return result;

	}

	public Event save(final Event event) {

		assert event != null;

		Event result;

		result = this.eventRepository.save(event);

		return result;

	}

	public void delete(final Event event) {

		assert event != null;
		assert event.getId() != 0;

		Assert.isTrue(this.eventRepository.exists(event.getId()));

		this.eventRepository.delete(event);

	}
	

	/**
	 * Reconstruct the Event passed as parameter
	 * 
	 * @param event
	 * @param binding
	 * 
	 * @return The reconstructed Event
	 * @author Alejandro
	 */
	public Event reconstruct(final Event event, final BindingResult binding) {
		Event result;

		if (event.getId() == 0) {
			result = event;
		} else {
			result = this.eventRepository.findOne(event.getId());
			result.setDescription(event.getDescription());
			result.setName(event.getName());
			result.setFinalMode(event.getFinalMode());
			result.setFood(event.getFood());
			result.setHealth(event.getHealth());
			result.setWater(event.getWater());
			result.setFindCharacter(event.getFindCharacter());
			result.setItemDesign(event.getItemDesign());
		}

		this.validator.validate(result, binding);
		return result;
	}
}

