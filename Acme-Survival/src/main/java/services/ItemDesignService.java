
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemDesignRepository;
import domain.Event;
import domain.ItemDesign;
import domain.ProbabilityItem;

@Service
@Transactional
public class ItemDesignService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ItemDesignRepository	itemDesignRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Collection<ItemDesign> findAll() {

		Collection<ItemDesign> result;

		Assert.notNull(this.itemDesignRepository);
		result = this.itemDesignRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public ItemDesign findOne(final int itemDesignId) {

		ItemDesign result;

		result = this.itemDesignRepository.findOne(itemDesignId);

		return result;

	}

	public ItemDesign save(final ItemDesign itemDesign) {

		Assert.notNull(itemDesign);

		ItemDesign result;

		result = this.itemDesignRepository.save(itemDesign);

		return result;

	}

	public void delete(final ItemDesign itemDesign) {

		assert itemDesign != null;
		assert itemDesign.getId() != 0;

		Assert.isTrue(this.itemDesignRepository.exists(itemDesign.getId()));

		this.itemDesignRepository.delete(itemDesign);

	}

	public Collection<Event> findEventsByItemDesign(final int itemDesignId) {
		Collection<Event> result;

		result = this.itemDesignRepository.findEventsByItemDesign(itemDesignId);

		return result;
	}

	public Collection<ProbabilityItem> findProbabilityItemsByItemDesign(final int itemDesignId) {
		Collection<ProbabilityItem> result;

		result = this.itemDesignRepository.findProbabilityItemsByItemDesign(itemDesignId);

		return result;
	}
}
