
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProbabilityEventRepository;
import domain.LootTable;
import domain.ProbabilityEvent;

@Service
@Transactional
public class ProbabilityEventService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ProbabilityEventRepository	probabilityEventRepository;

	@Autowired
	private LootTableService			lootTableService;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public ProbabilityEvent create() {
		ProbabilityEvent result;

		result = new ProbabilityEvent();

		return result;
	}

	public Collection<ProbabilityEvent> findAll() {

		Collection<ProbabilityEvent> result;

		Assert.notNull(this.probabilityEventRepository);
		result = this.probabilityEventRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public ProbabilityEvent findOne(final int probabilityEventId) {

		ProbabilityEvent result;

		result = this.probabilityEventRepository.findOne(probabilityEventId);

		return result;

	}

	public ProbabilityEvent save(final ProbabilityEvent probabilityEvent) {

		assert probabilityEvent != null;

		ProbabilityEvent result;

		result = this.probabilityEventRepository.save(probabilityEvent);

		return result;

	}

	public Collection<ProbabilityEvent> saveAll(final Collection<ProbabilityEvent> probabilityEvents) {

		assert probabilityEvents != null;

		Collection<ProbabilityEvent> result;
		result = this.probabilityEventRepository.save(probabilityEvents);

		return result;

	}

	public void delete(final ProbabilityEvent probabilityEvent) {

		Assert.isTrue(probabilityEvent != null);
		Assert.isTrue(probabilityEvent.getId() != 0);

		Assert.isTrue(this.probabilityEventRepository.exists(probabilityEvent.getId()));
		LootTable lootTable;

		lootTable = this.lootTableService.findLootTableByProbabilityEvent(probabilityEvent.getId());

		if (lootTable != null) {
			lootTable.getProbabilityEvents().remove(probabilityEvent);
			this.lootTableService.save(lootTable);
		}

		this.probabilityEventRepository.delete(probabilityEvent);

	}

	public void deleteAll(final Collection<ProbabilityEvent> probabilityEvents) {

		assert probabilityEvents != null;

		this.probabilityEventRepository.delete(probabilityEvents);

	}

	public Collection<ProbabilityEvent> findProbabilityEventByEvent(final int eventId) {
		Collection<ProbabilityEvent> result;

		result = this.probabilityEventRepository.findProbabilityEventByEvent(eventId);

		return result;
	}

	public void flush() {
		this.probabilityEventRepository.flush();

	}
}
