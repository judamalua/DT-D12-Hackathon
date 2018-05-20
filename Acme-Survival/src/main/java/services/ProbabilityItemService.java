package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProbabilityItemRepository;
import domain.Event;
import domain.ProbabilityEvent;
import domain.ProbabilityItem;

@Service
@Transactional
public class ProbabilityItemService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ProbabilityItemRepository	probabilityItemRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public ProbabilityItem create() {
		ProbabilityItem result;

		result = new ProbabilityItem();

		return result;
	}

	public Collection<ProbabilityItem> findAll() {

		Collection<ProbabilityItem> result;

		Assert.notNull(this.probabilityItemRepository);
		result = this.probabilityItemRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public ProbabilityItem findOne(final int probabilityItemId) {

		ProbabilityItem result;

		result = this.probabilityItemRepository.findOne(probabilityItemId);

		return result;

	}

	public ProbabilityItem save(final ProbabilityItem probabilityItem) {

		assert probabilityItem != null;

		ProbabilityItem result;

		result = this.probabilityItemRepository.save(probabilityItem);

		return result;

	}
	
	public Collection<ProbabilityItem> saveAll(final Collection<ProbabilityItem> probabilityItems) {
		
		assert probabilityItems != null;

		Collection<ProbabilityItem> result;
		result = this.probabilityItemRepository.save(probabilityItems);

		return result;

	}

	public void delete(final ProbabilityItem probabilityItem) {

		assert probabilityItem != null;
		assert probabilityItem.getId() != 0;

		Assert.isTrue(this.probabilityItemRepository.exists(probabilityItem.getId()));

		this.probabilityItemRepository.delete(probabilityItem);

	}
	
	public void deleteAll(final Collection<ProbabilityItem> probabilityItems) {

		assert probabilityItems != null;

		this.probabilityItemRepository.delete(probabilityItems);

	}
}

