
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CharacterRepository;

import com.github.javafaker.Faker;

import domain.Actor;
import domain.Character;
import domain.Inventory;
import domain.Player;
import domain.RestorationRoom;
import domain.Room;
import domain.Shelter;

@Service
@Transactional
public class CharacterService {

	// Managed repository --------------------------------------------------

	@Autowired
	private CharacterRepository	characterRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private InventoryService	inventoryService;

	@Autowired
	private ShelterService		shelterService;

	@Autowired
	private ItemService			itemService;


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
	 * 
	 * @author Luis
	 */
	public Character findOne(final int characterId) {

		Character result;

		result = this.characterRepository.findOne(characterId);

		if (result != null && result.getRoom() != null && result.getRoom().getRoomDesign() instanceof RestorationRoom) {
			result = this.updateStats(result);
		}

		return result;

	}

	/**
	 * Save(CRUD Methods)
	 * 
	 * @author Luis
	 */
	public Character save(final Character character) {
		assert character != null;
		Actor actor;
		Character result;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor instanceof Player);

		if (character.getId() == 0) {
			//Initial properties of a character
			Assert.isTrue(character.getCurrentFood() == 100);
			Assert.isTrue(character.getCurrentHealth() == 100);
			Assert.isTrue(character.getCurrentWater() == 100);
			Assert.isTrue(character.getStrength() + character.getLuck() + character.getCapacity() == 30);
			Assert.isTrue(character.getItem() == null);
			Assert.isTrue(character.getLevel() == 1);
			Assert.isTrue(character.getExperience() == 0);
		} else {
			this.calculateLevel(character);
			Assert.isTrue(character.getCurrentFood() <= 100);
			Assert.isTrue(character.getCurrentHealth() <= 100);
			Assert.isTrue(character.getCurrentWater() <= 100);
			Assert.isTrue(character.getLevel() >= 1);
			this.calculateLevel(character);

		}
		result = this.characterRepository.save(character);

		return result;

	}

	/**
	 * That method delete a character from database cause his or her life has arrive to 0
	 * 
	 * @author Luis
	 */
	public void characterRIP(final Character character) {
		Assert.isTrue(this.characterRepository.exists(character.getId()));
		Assert.isTrue(character.getCurrentHealth() == 0);

		if (character.getItem() != null) {
			character.getItem().setEquipped(false);
			this.itemService.delete(character.getItem());
		}

		this.characterRepository.delete(character);

	}

	/**
	 * Delete(CRUD Methods)
	 * 
	 * @author Luis
	 */
	public void delete(final Character character) {
		assert character != null;
		assert character.getId() != 0;
		Assert.isTrue(this.characterRepository.exists(character.getId()));

		this.characterRepository.delete(character);

	}

	//	/**
	//	 * Reconstruct of a Character
	//	 * 
	//	 * @author Luis
	//	 **/
	//	public Character reconstruct(final Character character, final BindingResult binding) {
	//		Character result;
	//
	//		if (character.getId() == 0) {
	//			result = character;
	//			result.setCurrentFood(100);
	//			result.setCurrentHealth(100);
	//			result.setCurrentWater(100);
	//			result.setExperience(0);
	//			result.setCapacity(10);
	//			result.setStrength(10);
	//			result.setLuck(10);
	//			result.setItem(null);
	//			result.setLevel(1);
	//
	//		} else {
	//			result = this.characterRepository.findOne(character.getId());
	//			result.setCapacity(character.getCapacity());
	//			result.setCurrentFood(character.getCurrentFood());
	//			result.setCurrentHealth(character.getCurrentHealth());
	//			result.setCurrentWater(character.getCurrentWater());
	//			result.setExperience(character.getExperience());
	//			result.setItem(character.getItem());
	//			result.setLevel(character.getLevel());
	//			result.setLuck(character.getLuck());
	//			result.setName(character.getName());
	//			result.setShelter(character.getShelter());
	//			result.setRoom(character.getRoom());
	//			result.setStrength(character.getStrength());
	//			result.setSurname(result.getSurname());
	//		}
	//		this.validator.validate(result, binding);
	//		return result;
	//	}

	/**
	 * That method generate a random character,set it to a shelter and locate it in a room
	 * 
	 * @param shelterId
	 * @return Character
	 * @author Luis
	 **/
	public Character generateCharacter(final int shelterId) {
		Character character;
		Shelter shelter;
		Faker faker;
		final Random random = new Random();
		int sexo;

		sexo = random.nextInt(2);
		faker = new Faker();
		character = this.create();
		shelter = this.shelterService.findOne(shelterId);
		final String name = faker.gameOfThrones().character();

		if (sexo == 0) {
			character.setMale(true);
		} else {
			character.setMale(false);
		}

		character.setFullName(name);
		character.setCurrentFood(100);
		character.setCurrentHealth(100);
		character.setCurrentWater(100);
		character.setExperience(0);
		character.setItem(null);
		character.setLevel(1);
		character.setShelter(shelter);
		character.setRoom(null);
		character.setCurrentlyInGatheringMission(false);
		character.setGatherNotificated(true);

		this.generateCharacterAbilities(character);

		return character;

	}

	/**
	 * That private method generate and set the properties of a character randomly
	 * 
	 * @author Luis
	 **/
	private void generateCharacterAbilities(final Character character) {
		Integer sum = 0;
		final List<Integer> properties = new ArrayList<Integer>();
		final Random r = new Random();

		for (int i = 1; i <= 3; i++) {
			if ((i == 1)) {
				final Integer property = r.nextInt(11) + 1;
				properties.add(property);
				sum += property;

			} else if ((i == 2)) {
				final Integer property = r.nextInt(21 - sum) + 1;
				properties.add(property);
				sum += property;

			} else {
				final Integer last = (30 - sum);
				properties.add(last);
				sum += last;
			}
		}
		Assert.isTrue(sum == 30);

		character.setCapacity(properties.get(0));
		character.setLuck(properties.get(1));
		character.setStrength(properties.get(2));

	}

	public Character generateCharacter() {
		Character character;
		Faker faker;

		faker = new Faker();
		character = this.create();
		final String name = faker.gameOfThrones().character();

		character.setFullName(name);
		character.setCurrentFood(100);
		character.setCurrentHealth(100);
		character.setCurrentWater(100);
		character.setExperience(0);
		character.setItem(null);
		character.setLevel(1);
		character.setRoom(null);

		this.generateCharacterAbilities(character);

		return character;

	}

	/**
	 * That private method calculate and set the level of a character
	 * 
	 * @author Luis
	 **/
	public void calculateLevel(final Character character) {
		final int currentLevel = character.getLevel();
		int finalLevel = currentLevel;
		final int experience = character.getExperience();

		//La experiencia para cada nivel se calcula (nivel^2*100) 
		//ejemplo para alcanzar nivel 2(2*2*100 = 400 experiencia)
		if (experience < (currentLevel + 1) * (currentLevel + 1) * 100) {
			finalLevel = currentLevel;
		} else {
			for (int i = currentLevel + 1; i <= 100; i++) {
				finalLevel = i;
				this.LevelUP(character);
				if (i == 100 || experience < (i + 1) * (i + 1) * 100) {
					break;
				}
			}
		}
		character.setLevel(finalLevel);

		Assert.isTrue(character.getLevel() == 100 || ((character.getLevel() + 1) * (character.getLevel() + 1) * 100) > character.getExperience());

	}

	/**
	 * That private method calculate the updated properties off a character when it level up
	 * 
	 * @author Luis
	 **/
	private void LevelUP(final Character character) {
		Integer sum = 0;
		final List<Integer> properties = new ArrayList<Integer>();
		final Random r = new Random();

		for (int i = 1; i <= 3; i++) {
			if ((i == 1)) {
				final Integer property = r.nextInt(2) + 1;
				properties.add(property);
				sum += property;

			} else if ((i == 2)) {
				final Integer property = r.nextInt(2) + 1;
				properties.add(property);
				sum += property;

			} else {
				final Integer last = (5 - sum);
				properties.add(last);
				sum += last;
			}
		}
		Assert.isTrue(sum == 5);

		character.setCapacity(character.getCapacity() + properties.get(0));
		character.setLuck(character.getLuck() + properties.get(1));
		character.setStrength(character.getStrength() + properties.get(2));

	}

	public Collection<Character> findCharactersByShelter(final int shelterId) {
		Collection<Character> result;

		result = this.characterRepository.findCharactersByShelter(shelterId);

		return result;
	}

	public Page<Character> findCharactersByShelterPageable(final int shelterId, final Pageable pageable) {
		Page<Character> result;

		result = this.characterRepository.findCharactersByShelterPageable(shelterId, pageable);

		return result;
	}

	public Collection<Character> findCharactersByRoom(final int roomId) {

		Collection<Character> result;

		result = this.characterRepository.findCharactersByRoom(roomId);

		return result;
	}

	//	public void generateCharacterName(final Character character) throws FileNotFoundException {
	//		final File male = new File("maleNames.txt");
	//		final File female = new File("femaleNames.txt");
	//		Scanner s = null;
	//		final Random random = new Random();
	//
	//		int i = random.nextInt(1);
	//
	//		//Nombre Masculino 
	//		if (i == 0) {
	//			s = new Scanner(male);
	//			final int stop = random.nextInt(100);
	//			for (final int puntero = 0; i < 101; i++) {
	//				s.nextLine();
	//				if (puntero == stop)
	//					break;
	//			}
	//			final String maleName = s.nextLine();
	//			character.setFullName(maleName);
	//			character.setMale(true);
	//
	//		} else {
	//			s = new Scanner(female);
	//			final int stop = random.nextInt(100);
	//			for (final int puntero = 0; i < 101; i++) {
	//				s.nextLine();
	//				if (puntero == stop)
	//					break;
	//			}
	//			final String femaleName = s.nextLine();
	//			character.setFullName(femaleName);
	//			character.setMale(false);
	//
	//		}
	//
	//	}

	public void flush() {
		this.characterRepository.flush();
	}

	/**
	 * This query returns the character of a shelter that are currently doing a gathering mission
	 * 
	 * @param shelterId
	 * @return
	 * @author Juanmi
	 */
	public Collection<Character> findCharactersCurrentlyInMission(final int shelterId) {
		Collection<Character> result;

		result = this.characterRepository.findCharactersCurrentlyInMission(shelterId);

		return result;
	}

	public Character updateStats(final Character character) {

		Assert.notNull(character);

		Room room;
		RestorationRoom restorationRoom;
		Character result;
		Inventory inventory;
		Double food, water, health;
		Date entrance, currentDate;
		long minutes;
		Collection<Character> characters;

		room = character.getRoom();

		restorationRoom = (RestorationRoom) room.getRoomDesign();

		inventory = this.inventoryService.findInventoryByShelter(character.getShelter().getId());

		entrance = character.getRoomEntrance();
		currentDate = new Date();

		minutes = TimeUnit.MILLISECONDS.toMinutes(currentDate.getTime() - entrance.getTime());
		characters = this.characterRepository.findCharactersCurrentlyInMission(character.getShelter().getId());

		if (character.getCurrentlyInGatheringMission() || characters.contains(character)) {
			minutes = 0;
		}

		if (minutes > 0) {
			food = restorationRoom.getFood() * minutes;
			health = restorationRoom.getHealth() * minutes;
			water = restorationRoom.getWater() * minutes;

			if (character.getCurrentFood() + food < 100) {
				character.setCurrentFood((int) (character.getCurrentFood() + food));
				inventory.setFood(inventory.getFood() - food);
			} else {
				character.setCurrentFood(100);
			}

			if (character.getCurrentHealth() + health < 100) {
				character.setCurrentHealth((int) (character.getCurrentHealth() + health));
			} else {
				character.setCurrentHealth(100);
			}

			if (character.getCurrentWater() + water < 100) {
				character.setCurrentWater((int) (character.getCurrentWater() + water));
				inventory.setWater(inventory.getWater() - water);
			} else {
				character.setCurrentWater(100);
			}

			if (minutes > 0) {
				character.setRoomEntrance(currentDate);
			}

			this.inventoryService.save(inventory);
		}
		result = this.save(character);

		return result;
	}

	public Collection<Character> findCharactersNotNotificatedOfGather(final int shelterId) {
		Collection<Character> result;

		result = this.characterRepository.findCharactersNotNotificatedOfGather(shelterId);

		return result;
	}

	public String findNumCharacters() {
		String result;

		result = this.characterRepository.findNumCharacters();

		return result;
	}
}
