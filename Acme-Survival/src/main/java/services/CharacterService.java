
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
import domain.Refuge;
import domain.RestorationRoom;
import domain.Room;

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
	private RefugeService		refugeService;

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

		if (result != null && result.getRoom() != null && result.getRoom().getRoomDesign() instanceof RestorationRoom)
			result = this.updateStats(result);

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

		if (character.getCurrentHealth() < 1) {
			this.characterRIP(character);

			result = null;

		} else
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

		if (character.getItem() != null)
			this.itemService.delete(character.getItem());

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
	//			result.setRefuge(character.getRefuge());
	//			result.setRoom(character.getRoom());
	//			result.setStrength(character.getStrength());
	//			result.setSurname(result.getSurname());
	//		}
	//		this.validator.validate(result, binding);
	//		return result;
	//	}

	/**
	 * That method generate a random character,set it to a refuge and locate it in a room
	 * 
	 * @param refugeId
	 * @return Character
	 * @author Luis
	 **/
	public Character generateCharacter(final int refugeId) {
		Character character;
		Refuge refuge;
		Faker faker;
		final Random random = new Random();
		int sexo;

		sexo = random.nextInt(2);
		faker = new Faker();
		character = this.create();
		refuge = this.refugeService.findOne(refugeId);
		final String name = faker.gameOfThrones().character();

		if (sexo == 0)
			character.setMale(true);
		else
			character.setMale(false);

		character.setFullName(name);
		character.setCurrentFood(100);
		character.setCurrentHealth(100);
		character.setCurrentWater(100);
		character.setExperience(0);
		character.setItem(null);
		character.setLevel(1);
		character.setRefuge(refuge);
		character.setRoom(null);
		character.setCurrentlyInGatheringMission(false);

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

		for (int i = 1; i <= 3; i++)
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
		if (experience < (currentLevel + 1) * (currentLevel + 1) * 100)
			finalLevel = currentLevel;
		else
			for (int i = currentLevel + 1; i <= 100; i++) {
				finalLevel = i;
				this.LevelUP(character);
				if (i == 100 || experience < (i + 1) * (i + 1) * 100)
					break;
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

		for (int i = 1; i <= 3; i++)
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
		Assert.isTrue(sum == 5);

		character.setCapacity(character.getCapacity() + properties.get(0));
		character.setLuck(character.getLuck() + properties.get(1));
		character.setStrength(character.getStrength() + properties.get(2));

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
	 * This query returns the character of a refuge that are currently doing a gathering mission
	 * 
	 * @param refugeId
	 * @return
	 * @author Juanmi
	 */
	public Collection<Character> findCharactersCurrentlyInMission(final int refugeId) {
		Collection<Character> result;

		result = this.characterRepository.findCharactersCurrentlyInMission(refugeId);

		return result;
	}

	private Character updateStats(final Character character) {

		Assert.notNull(character);

		Room room;
		RestorationRoom restorationRoom;
		Character result;
		Inventory inventory;
		Double food, water, health;
		Date entrance, currentDate;
		long hours;

		room = character.getRoom();

		restorationRoom = (RestorationRoom) room.getRoomDesign();

		inventory = this.inventoryService.findInventoryByRefuge(character.getRefuge().getId());

		entrance = character.getRoomEntrance();
		currentDate = new Date();

		hours = TimeUnit.MILLISECONDS.toHours(currentDate.getTime() - entrance.getTime());

		food = restorationRoom.getFood() * hours;
		health = restorationRoom.getHealth() * hours;
		water = restorationRoom.getWater() * hours;

		if (character.getCurrentFood() + food < 100) {
			character.setCurrentFood((int) (character.getCurrentFood() + food));
			inventory.setFood(inventory.getFood() - food);
		} else
			character.setCurrentFood(100);

		if (character.getCurrentHealth() + health < 100)
			character.setCurrentHealth((int) (character.getCurrentHealth() + health));
		else
			character.setCurrentHealth(100);

		if (character.getCurrentWater() + water < 100) {
			character.setCurrentWater((int) (character.getCurrentWater() + water));
			inventory.setWater(inventory.getWater() - water);
		} else
			character.setCurrentWater(100);

		if (hours > 0)
			character.setRoomEntrance(currentDate);

		this.inventoryService.save(inventory);

		result = this.save(character);

		return result;
	}
}
