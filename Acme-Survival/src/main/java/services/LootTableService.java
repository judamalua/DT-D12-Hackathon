package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LootTableRepository;
import domain.LootTable;

@Service
@Transactional
public class LootTableService {

	// Managed repository --------------------------------------------------

	@Autowired
	private LootTableRepository	lootTableRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public LootTable create() {
		LootTable result;

		result = new LootTable();

		return result;
	}

	public Collection<LootTable> findAll() {

		Collection<LootTable> result;

		Assert.notNull(this.lootTableRepository);
		result = this.lootTableRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public LootTable findOne(final int lootTableId) {

		LootTable result;

		result = this.lootTableRepository.findOne(lootTableId);

		return result;

	}

	public LootTable save(final LootTable lootTable) {

		assert lootTable != null;

		LootTable result;

		result = this.lootTableRepository.save(lootTable);

		return result;

	}

	public void delete(final LootTable lootTable) {

		assert lootTable != null;
		assert lootTable.getId() != 0;

		Assert.isTrue(this.lootTableRepository.exists(lootTable.getId()));

		this.lootTableRepository.delete(lootTable);

	}
}

