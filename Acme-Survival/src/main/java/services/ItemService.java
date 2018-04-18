
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;

@Service
@Transactional
public class ItemService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ItemRepository	itemRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Item create() {
		Item result;

		result = new Item();

		return result;
	}

	public Collection<Item> findAll() {

		Collection<Item> result;

		Assert.notNull(this.itemRepository);
		result = this.itemRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Item findOne(final int itemId) {

		Item result;

		result = this.itemRepository.findOne(itemId);

		return result;

	}

	public Item save(final Item item) {

		assert item != null;

		Item result;

		result = this.itemRepository.save(item);

		return result;

	}

	public void delete(final Item item) {

		assert item != null;
		assert item.getId() != 0;

		Assert.isTrue(this.itemRepository.exists(item.getId()));

		this.itemRepository.delete(item);

	}

	public Collection<Item> findItemsByRefuge(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Item> result;

		result = this.itemRepository.findItemsByRefuge(refugeId);

		return result;
	}
}
