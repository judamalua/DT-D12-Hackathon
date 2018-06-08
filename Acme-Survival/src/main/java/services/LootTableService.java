
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LootTableRepository;
import domain.Actor;
import domain.Designer;
import domain.LootTable;
import domain.ProbabilityEvent;
import domain.ProbabilityItem;

@Service
@Transactional
public class LootTableService {

	// Managed repository --------------------------------------------------

	@Autowired
	private LootTableRepository	lootTableRepository;
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ProbabilityEventService probabilityEventService;
	@Autowired
	private ProbabilityItemService probabilityItemService;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public LootTable create() {
		LootTable result;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Designer);
		result = new LootTable();
		result.setProbabilityEvents(new HashSet<ProbabilityEvent>());
		result.setProbabilityItems(new HashSet<ProbabilityItem>());
		result.setName("LootTable Name");
		return result;
	}

	public Collection<LootTable> findAll() {

		Collection<LootTable> result;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Designer);
		Assert.notNull(this.lootTableRepository);
		result = this.lootTableRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public LootTable findOne(final int lootTableId) {

		LootTable result;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Designer);
		result = this.lootTableRepository.findOne(lootTableId);

		return result;

	}

	public LootTable save(final LootTable lootTable) {

		assert lootTable != null;

		LootTable result;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Designer);
		result = this.lootTableRepository.save(lootTable);

		return result;

	}

	public void delete(final LootTable lootTable) {

		assert lootTable != null;
		assert lootTable.getId() != 0;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Designer);

		Assert.isTrue(this.lootTableRepository.exists(lootTable.getId()));
		Collection<ProbabilityItem> delItems = new HashSet<ProbabilityItem>(lootTable.getProbabilityItems());
		Collection<ProbabilityEvent> delEvents = new HashSet<ProbabilityEvent>(lootTable.getProbabilityEvents());
		lootTable.getProbabilityEvents().clear();
		lootTable.getProbabilityItems().clear();
		probabilityItemService.deleteAll(delItems);
		probabilityEventService.deleteAll(delEvents);
		this.lootTableRepository.delete(lootTable);

	}

	public LootTable reconstruct(final LootTable lootTable, final BindingResult binding) {
		LootTable result;

		if (lootTable.getId() == 0)
			result = lootTable;
		else {
			result = this.lootTableRepository.findOne(lootTable.getId());
			result.setName(lootTable.getName());
			result.setFinalMode(lootTable.getFinalMode());
			result.setProbabilityEvents(lootTable.getProbabilityEvents());
			result.setProbabilityItems(lootTable.getProbabilityItems());
		}
		this.validator.validate(result, binding);
		return result;
	}

	public Collection<LootTable> findAllFinal() {
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Designer);
		Collection<LootTable> result;
		result = this.lootTableRepository.findAllFinal();
		return result;
	}
	
	public Page<LootTable> findAll(Pageable pageable) {
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Designer);
		Page<LootTable> result;
		result = this.lootTableRepository.findAll(pageable);
		return result;
	}
	
	public void flush() {
		this.lootTableRepository.flush();

	}

}
