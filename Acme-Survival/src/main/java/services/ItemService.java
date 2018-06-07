
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
import domain.Shelter;

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
	private ShelterService		shelterService;
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

	public Collection<Item> saveAll(final Collection<Item> item) {

		assert item != null;

		Collection<Item> result;
		result = this.itemRepository.save(item);

		return result;

	}

	public Item findOne(final int itemId) {

		Item result;

		result = this.itemRepository.findOne(itemId);

		return result;

	}

	/**
	 * Save a item in a shelter
	 * 
	 * @Luis
	 */
	public Item keepInShelter(final Item item, final int shelterId) {
		Assert.isTrue(item != null);
		Item result;
		Shelter shelter;
		Actor principal;
		shelter = this.shelterService.findOne(shelterId);
		principal = this.actorService.findActorByPrincipal();

		//Guardamos Items que van destinados a un refugio
		Assert.isTrue(this.actorService.findActorByPrincipal() instanceof Player);
		Assert.isTrue(this.shelterService.findShelterByPlayer(principal.getId()) == shelter);
		Assert.isTrue(this.shelterService.getCurrentCapacity(item.getShelter()) > 0);

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
		Item result, oldItem;
		domain.Character character;

		character = this.characterService.findOne(characterId);

		Assert.isTrue(!item.getEquipped());

		//Actualización de objeto equipado y el que desequipa
		if (character.getItem() != null) {
			oldItem = character.getItem();
			oldItem.setEquipped(false);
			//update propertis
			character.setCapacity(character.getCapacity() - oldItem.getTool().getCapacity());
			character.setStrength(character.getStrength() - oldItem.getTool().getStrength());
			character.setLuck(character.getLuck() - oldItem.getTool().getLuck());
			this.itemRepository.save(oldItem);

			character.setItem(item);
			character.setCapacity(character.getCapacity() + item.getTool().getCapacity());
			character.setStrength(character.getStrength() + item.getTool().getStrength());
			character.setLuck(character.getLuck() + item.getTool().getLuck());
			item.setEquipped(true);

			this.itemRepository.save(item);
			//			this.characterService.save(character);

		} else {
			character.setItem(item);
			character.setCapacity(character.getCapacity() + item.getTool().getCapacity());
			character.setStrength(character.getStrength() + item.getTool().getStrength());
			character.setLuck(character.getLuck() + item.getTool().getLuck());
			item.setEquipped(true);

			this.itemRepository.save(item);
			//			this.characterService.save(character);

		}

		this.characterService.save(character);
		result = this.itemRepository.save(item);

		return result;

	}

	/**
	 * Update a discard of a item
	 * 
	 * @Luis
	 */
	public Item UpdateDiscard(final domain.Character character) {
		Item result;

		Assert.isTrue(character.getItem() != null && character.getItem().getEquipped());

		final Item oldItem = character.getItem();
		oldItem.setEquipped(false);
		character.setCapacity(character.getCapacity() - oldItem.getTool().getCapacity());
		character.setStrength(character.getStrength() - oldItem.getTool().getStrength());
		character.setLuck(character.getLuck() - oldItem.getTool().getLuck());
		result = this.itemRepository.save(oldItem);
		character.setItem(null);
		this.characterService.save(character);

		return result;

	}

	public void delete(final Item item) {
		assert item != null;
		assert item.getId() != 0;

		Assert.isTrue(!item.getEquipped());
		Assert.isTrue(this.itemRepository.exists(item.getId()));

		this.itemRepository.delete(item);

	}

	public Collection<Item> findItemsByShelter(final int shelterId) {
		Assert.isTrue(shelterId != 0);

		Collection<Item> result;

		result = this.itemRepository.findItemsByShelter(shelterId);

		return result;
	}
	public Page<Item> findItemsByShelter(final int shelterId, final Pageable pageable) {
		Assert.isTrue(shelterId != 0);

		Page<Item> result;

		result = this.itemRepository.findItemsByShelter(shelterId, pageable);

		return result;
	}

	public void flush() {
		this.itemRepository.flush();

	}

}
