
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Actor;
import domain.Item;
import domain.Player;
import domain.Refuge;

@Service
@Transactional
public class ItemService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ItemRepository		itemRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService		actorService;
	@Autowired
	private RefugeService		refugeService;
	@Autowired
	private CharacterService	characterService;


	// Simple CRUD methods --------------------------------------------------

	public Item create() {
		Item result;

		result = new Item();
		result.setEquipped(false);

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

	/**
	 * Save a item in a refuge
	 * 
	 * @Luis
	 */
	public Item keepInRefuge(final Item item, final int refugeId) {
		Assert.isTrue(item != null);
		Item result;
		Refuge refuge;
		Actor principal;
		refuge = this.refugeService.findOne(refugeId);
		principal = this.actorService.findActorByPrincipal();

		//Guardamos Items que van destinados a un refugio
		Assert.isTrue(this.actorService.findActorByPrincipal() instanceof Player);
		Assert.isTrue(this.refugeService.findRefugeByPlayer(principal.getId()) == refuge);
		Assert.isTrue(this.refugeService.getCurrentCapacity(item.getRefuge()) > 0);

		result = this.itemRepository.save(item);

		return result;

	}

	public Item save(final Item item) {
		Assert.isTrue(item != null);
		Item result;

		result = this.itemRepository.save(item);

		return result;

	}

	/**
	 * Update a equip of a item
	 * 
	 * @Luis
	 */
	public Item UpdateEquipped(final Item item, final int characterId) {
		Item result;
		domain.Character character;

		character = this.characterService.findOne(characterId);

		Assert.isTrue(!item.getEquipped());

		//Actualización de objeto equipado y el que desequipa
		if (character.getItem() != null) {
			final Item oldItem = character.getItem();
			oldItem.setEquipped(false);
			//update propertis
			character.setCapacity(character.getCapacity() - oldItem.getTool().getCapacity());
			character.setCapacity(character.getStrength() - oldItem.getTool().getStrength());
			character.setCapacity(character.getLuck() - oldItem.getTool().getLuck());
			this.itemRepository.save(oldItem);

			character.setItem(item);
			character.setCapacity(character.getCapacity() + item.getTool().getCapacity());
			character.setCapacity(character.getStrength() + item.getTool().getStrength());
			character.setCapacity(character.getLuck() + item.getTool().getLuck());
			item.setEquipped(true);

			this.itemRepository.save(item);
			this.characterService.save(character);

		} else {
			character.setItem(item);
			character.setCapacity(character.getCapacity() + item.getTool().getCapacity());
			character.setCapacity(character.getStrength() + item.getTool().getStrength());
			character.setCapacity(character.getLuck() + item.getTool().getLuck());
			item.setEquipped(true);

			this.itemRepository.save(item);
			this.characterService.save(character);

		}

		this.characterService.save(character);
		result = this.itemRepository.save(item);

		return result;

	}

	public void delete(final Item item) {
		assert item != null;
		assert item.getId() != 0;

		Assert.isTrue(this.itemRepository.exists(item.getId()));

		this.itemRepository.delete(item);
		this.refugeService.save(item.getRefuge());

	}

	public Collection<Item> findItemsByRefuge(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Item> result;

		result = this.itemRepository.findItemsByRefuge(refugeId);

		return result;
	}
	public Page<Item> findItemsByRefuge(final int refugeId, final Pageable pageable) {
		Assert.isTrue(refugeId != 0);

		Page<Item> result;

		result = this.itemRepository.findItemsByRefuge(refugeId, pageable);

		return result;
	}

}
