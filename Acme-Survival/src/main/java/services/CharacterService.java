
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CharacterRepository;
import domain.Actor;
import domain.Character;
import domain.Player;

@Service
@Transactional
public class CharacterService {

	// Managed repository --------------------------------------------------

	@Autowired
	private CharacterRepository	characterRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	/**
	 * 
	 * @author Luis
	 */
	public Character create() {
		Character result;

		result = new Character();

		return result;
	}

	/**
	 * 
	 * @author Luis
	 */
	public Collection<Character> findAll() {

		Collection<Character> result;

		Assert.notNull(this.characterRepository);
		result = this.characterRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	/**
	 * 
	 * @author Luis
	 */
	public Character findOne(final int characterId) {

		Character result;

		result = this.characterRepository.findOne(characterId);

		return result;

	}

	/**
	 * 
	 * @author Luis
	 */
	public Character save(final Character character) {
		assert character != null;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Player);

		if (character.getId() == 0) {
			//Initial properties of a character
			Assert.isTrue(character.getCurrentFood() == 100);
			Assert.isTrue(character.getCurrentHealth() == character.getStrenght());
			Assert.isTrue(character.getCurrentWater() == 100);
			Assert.isTrue(character.getExperience() == 0);
			Assert.isTrue(character.getItem() == null);
			Assert.isTrue(character.getLevel() == 1);
			Assert.isTrue(character.getLuck() == 10);
			Assert.isTrue(character.getCapacity() == 10);
		} else {
			Assert.isTrue(character.getCurrentFood() <= 100);
			Assert.isTrue(character.getCurrentHealth() <= character.getStrenght());
			Assert.isTrue(character.getCurrentWater() <= 100);
			Assert.isTrue(character.getExperience() >= 0);
			Assert.isTrue(character.getItem() == null);
			Assert.isTrue(character.getLevel() >= 1);
			Assert.isTrue(character.getLuck() >= 10);
			Assert.isTrue(character.getCapacity() >= 10);

		}

		Character result;

		result = this.characterRepository.save(character);

		return result;

	}

	/**
	 * 
	 * @author Luis
	 */
	public void delete(final Character character) {
		assert character != null;
		assert character.getId() != 0;
		Assert.isTrue(this.characterRepository.exists(character.getId()));

		this.characterRepository.delete(character);

	}

	/**
	 * 
	 * @author Luis
	 **/
	public Character reconstruct(final Character character, final BindingResult binding) {
		Character result;

		if (character.getId() == 0) {
			result = character;
			result.setCurrentFood(100);
			result.setCurrentHealth(100);
			result.setCurrentWater(100);
			result.setExperience(0);
			result.setCapacity(10);
			result.setLuck(10);
			result.setItem(null);
			result.setLevel(1);

		} else {
			result = this.characterRepository.findOne(character.getId());
			result.setCapacity(character.getCapacity());
			result.setCurrentFood(character.getCurrentFood());
			result.setCurrentHealth(character.getCurrentHealth());
			result.setCurrentWater(character.getCurrentWater());
			result.setExperience(character.getExperience());
			result.setItem(character.getItem());
			result.setLevel(character.getLevel());
			result.setLuck(character.getLuck());
			result.setName(character.getName());
			result.setRefuge(character.getRefuge());
			result.setRoom(character.getRoom());
			result.setStrenght(character.getStrenght());
			result.setSurname(result.getSurname());
		}
		this.validator.validate(result, binding);
		return result;
	}

	public Collection<Character> findCharactersByRefuge(final int refugeId) {
		Collection<Character> result;

		result = this.characterRepository.findCharactersByRefuge(refugeId);

		return result;
	}

	public Page<Character> findCharactersByRefugePageable(final int refugeId, final Pageable pageable) {
		Page<Character> result;

		result = this.characterRepository.findCharactersByRefugePageable(refugeId, pageable);

		return result;
	}

	public Collection<Character> findCharactersByRoom(final int roomId) {

		Collection<Character> result;

		result = this.characterRepository.findCharactersByRoom(roomId);

		return result;
	}
}
