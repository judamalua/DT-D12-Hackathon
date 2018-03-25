package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EventRepository;
import domain.Event;

@Service
@Transactional
public class EventService {

	// Managed repository --------------------------------------------------

	@Autowired
	private EventRepository	eventRepository;


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
}

