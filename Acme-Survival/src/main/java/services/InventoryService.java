
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.InventoryRepository;
import domain.Inventory;

@Service
@Transactional
public class InventoryService {

	// Managed repository --------------------------------------------------

	@Autowired
	private InventoryRepository	inventoryRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Inventory create() {
		Inventory result;

		result = new Inventory();

		return result;
	}

	public Collection<Inventory> findAll() {

		Collection<Inventory> result;

		Assert.notNull(this.inventoryRepository);
		result = this.inventoryRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Inventory findOne(final int itemId) {

		Inventory result;

		result = this.inventoryRepository.findOne(itemId);

		return result;

	}

	public Inventory save(final Inventory inventory) {

		Assert.notNull(inventory);

		Inventory result;

		result = this.inventoryRepository.save(inventory);

		return result;

	}

	public void delete(final Inventory item) {

		Assert.isTrue(item != null);
		Assert.isTrue(item.getId() != 0);

		Assert.isTrue(this.inventoryRepository.exists(item.getId()));

		this.inventoryRepository.delete(item);

	}

	public Inventory findInventoryByRefuge(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Inventory result;

		result = this.inventoryRepository.findInventoryByRefuge(refugeId);

		return result;
	}

	//Other business methods -------------------------
	public Double findTotalResourcesByInventory(final Inventory inventory) {
		Double result;

		result = inventory.getWater();
		result = result + inventory.getFood();
		result = result + inventory.getMetal();
		result = result + inventory.getWood();

		return result;
	}
}
