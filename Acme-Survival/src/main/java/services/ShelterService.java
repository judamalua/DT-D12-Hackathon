
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ShelterRepository;
import domain.Actor;
import domain.Attack;
import domain.Barrack;
import domain.DesignerConfiguration;
import domain.Inventory;
import domain.Item;
import domain.Location;
import domain.Move;
import domain.Player;
import domain.ResourceRoom;
import domain.Room;
import domain.Shelter;
import domain.Warehouse;

@Service
@Transactional
public class ShelterService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ShelterRepository				shelterRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService					actorService;

	@Autowired
	private PlayerService					playerService;

	@Autowired
	private AttackService					attackService;

	@Autowired
	private DesignerConfigurationService	designerConfigurationService;

	@Autowired
	private CharacterService				characterService;

	@Autowired
	private ItemService						itemService;

	@Autowired
	private MoveService						moveService;

	@Autowired
	private InventoryService				inventoryService;

	@Autowired
	private RoomService						roomService;

	@Autowired
	private LocationService					locationService;

	@Autowired
	private Validator						validator;


	// Simple CRUD methods --------------------------------------------------

	public Shelter create() {
		Shelter result;

		result = new Shelter();

		return result;
	}

	public Collection<Shelter> findAll() {

		Collection<Shelter> result;

		Assert.notNull(this.shelterRepository);
		result = this.shelterRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Page<Shelter> findAll(final Pageable pageable) {

		Page<Shelter> result;

		Assert.notNull(this.shelterRepository);
		result = this.shelterRepository.findAll(pageable);
		Assert.notNull(result);

		return result;

	}

	public Shelter findOne(final int shelterId) {

		Shelter result;
		Assert.isTrue(shelterId != 0);

		result = this.shelterRepository.findOne(shelterId);

		if (result != null) {
			this.updateInventory(result);
			this.updateLocation(result);
		}

		return result;

	}

	public Shelter saveToUpdateLastTimeAttacked(final Shelter shelter) {
		Assert.notNull(shelter.getLastAttackReceived());

		Shelter result;

		result = this.shelterRepository.save(shelter);

		return result;
	}

	public Shelter save(final Shelter shelter) {

		Assert.isTrue(shelter != null);

		Shelter result;
		Collection<Player> playersKnowsShelter;
		Attack attackWhereAttacked;
		Collection<Attack> attacksWhereDefend;
		Collection<Item> items;
		Collection<domain.Character> characters;
		Collection<Room> rooms;
		Actor actor;
		Inventory inventory;
		final DesignerConfiguration designerConfiguration;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor.equals(shelter.getPlayer()));

		result = this.shelterRepository.save(shelter);
		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();
		if (shelter.getId() != 0) {
			playersKnowsShelter = this.playerService.findPlayersKnowsShelter(shelter.getId());
			attackWhereAttacked = this.attackService.findAttacksByAttacker(shelter.getId());
			attacksWhereDefend = this.attackService.findAttacksByDefendant(shelter.getId());
			characters = this.characterService.findCharactersByShelter(shelter.getId());
			items = this.itemService.findItemsByShelter(shelter.getId());
			rooms = this.roomService.findRoomsByShelter(shelter.getId());
			inventory = this.inventoryService.findInventoryByShelter(result.getId());

			for (final Player player : playersKnowsShelter) {
				player.getShelters().remove(shelter);
				player.getShelters().add(result);
				this.playerService.save(player);
			}

			/*
			 * for (final Attack attack : attacksWhereAttacked) {
			 * attack.setAttacker(result);
			 * this.attackService.save(attack);
			 * }
			 */

			if (attackWhereAttacked != null) {
				attackWhereAttacked.setAttacker(result);
				this.attackService.save(attackWhereAttacked);
			}

			for (final Attack attack : attacksWhereDefend) {
				attack.setDefendant(result);
				this.attackService.save(attack);
			}

			for (final domain.Character character : characters) {
				character.setShelter(result);
				this.characterService.save(character);
			}
			for (final Item item : items) {
				item.setShelter(result);
				this.itemService.save(item);
			}

			//			for (final Move move : moves) {
			//				move.setShelter(result);
			//				this.moveService.save(move);
			//			}

			for (final Room room : rooms) {
				room.setShelter(result);
				this.roomService.save(room);
			}

			inventory.setShelter(result);
			this.inventoryService.save(inventory);

		} else {
			//inventory initialization

			inventory = this.inventoryService.create();

			//Poner config
			inventory.setWaterCapacity(designerConfiguration.getMaxInventoryWater());
			inventory.setFoodCapacity(designerConfiguration.getMaxInventoryFood());
			inventory.setWoodCapacity(designerConfiguration.getMaxInventoryWood());
			inventory.setMetalCapacity(designerConfiguration.getMaxInventoryMetal());
			inventory.setFood(designerConfiguration.getInitialFood());
			inventory.setWater(designerConfiguration.getInitialWater());
			inventory.setWood(designerConfiguration.getInitialWood());
			inventory.setMetal(designerConfiguration.getInitialMetal());

			inventory.setShelter(result);
			this.inventoryService.save(inventory);

			characters = this.generateCharacters(result, designerConfiguration.getNumInitialCharacters());
		}

		return result;

	}
	public void delete(final Shelter shelter) {

		Assert.isTrue(shelter != null);
		Assert.isTrue(shelter.getId() != 0);
		Assert.isTrue(this.shelterRepository.exists(shelter.getId()));

		Collection<Player> playersKnowsShelter;
		Attack attackWhereAttacked;
		Collection<Attack> attacks;
		Collection<Item> items;
		Collection<Move> moves;
		Collection<domain.Character> characters;
		Collection<Room> rooms;
		Actor actor;
		final Inventory inventory;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor.equals(shelter.getPlayer()));

		playersKnowsShelter = this.playerService.findPlayersKnowsShelter(shelter.getId());
		attackWhereAttacked = this.attackService.findAttacksByAttacker(shelter.getId());
		attacks = this.attackService.findAttacksByDefendant(shelter.getId());
		attacks.add(attackWhereAttacked);
		characters = this.characterService.findCharactersByShelter(shelter.getId());
		items = this.itemService.findItemsByShelter(shelter.getId());
		moves = this.moveService.findMovesByShelter(shelter.getId());
		inventory = this.inventoryService.findInventoryByShelter(shelter.getId());
		rooms = this.roomService.findRoomsByShelter(shelter.getId());

		for (final Player player : playersKnowsShelter) {
			player.getShelters().remove(shelter);
			this.playerService.save(player);
		}

		for (final Attack attack : attacks)
			this.attackService.delete(attack);

		for (final domain.Character character : characters)
			this.characterService.delete(character);

		for (final Item item : items)
			this.itemService.delete(item);

		for (final Move move : moves)
			this.moveService.delete(move);

		for (final Room room : rooms)
			this.roomService.delete(room);

		this.inventoryService.delete(inventory);

		this.shelterRepository.delete(shelter);

	}
	public Shelter reconstruct(final Shelter shelter, final BindingResult binding) {
		Shelter result;
		Actor actor;
		Location location;
		String gpsCoordinates;

		if (shelter.getId() == 0) {

			actor = this.actorService.findActorByPrincipal();
			location = this.getRandomLocation();
			gpsCoordinates = this.generateRandomCoordinates(location);
			result = shelter;

			result.setCode(this.generateCode());
			result.setPlayer((Player) actor);
			result.setMomentOfCreation(new Date(System.currentTimeMillis() - 1));
			result.setLocation(location);
			result.setGpsCoordinates(gpsCoordinates);

		} else {
			result = this.findOne(shelter.getId());

			result.setName(shelter.getName());

		}
		this.validator.validate(result, binding);
		this.shelterRepository.flush();

		return result;
	}
	public String generateCode() {
		String result;
		final String alphabet;
		Random random;

		random = new Random();

		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";

		result = "";
		for (int i = 0; i < 10; i++)
			result += alphabet.charAt(random.nextInt(alphabet.length()));
		return result;
	}
	public Shelter findShelterByPlayer(final int playerId) {
		Shelter result;

		result = this.shelterRepository.findShelterByPlayer(playerId);
		if (result != null)
			this.updateLocation(result);

		return result;
	}

	public String generateRandomCoordinates(final Location location) {
		final Double r1 = Math.random();
		final Double r2 = Math.random();
		Double x;
		Double y;
		final Double coordinateAx;
		final Double coordinateAy;
		final Double coordinateBx;
		final Double coordinateBy;
		final Double coordinateCx;
		final Double coordinateCy;
		final Double coordinateDx;
		final Double coordinateDy;
		String result;

		coordinateAx = Double.parseDouble(location.getPoint_a().split(",")[0]);
		coordinateAy = Double.parseDouble(location.getPoint_a().split(",")[1]);
		coordinateBx = Double.parseDouble(location.getPoint_b().split(",")[0]);
		coordinateBy = Double.parseDouble(location.getPoint_b().split(",")[1]);
		coordinateCx = Double.parseDouble(location.getPoint_c().split(",")[0]);
		coordinateCy = Double.parseDouble(location.getPoint_c().split(",")[1]);
		coordinateDx = Double.parseDouble(location.getPoint_d().split(",")[0]);
		coordinateDy = Double.parseDouble(location.getPoint_d().split(",")[1]);

		if (Math.random() < 0.5) {
			x = (1 - Math.sqrt(r1)) * coordinateAx + (Math.sqrt(r1) * (1 - r2)) * coordinateBx + (Math.sqrt(r1) * r2) * coordinateCx;
			y = (1 - Math.sqrt(r1)) * coordinateAy + (Math.sqrt(r1) * (1 - r2)) * coordinateBy + (Math.sqrt(r1) * r2) * coordinateCy;
		} else {
			x = (1 - Math.sqrt(r1)) * coordinateAx + (Math.sqrt(r1) * (1 - r2)) * coordinateBx + (Math.sqrt(r1) * r2) * coordinateDx;
			y = (1 - Math.sqrt(r1)) * coordinateAy + (Math.sqrt(r1) * (1 - r2)) * coordinateBy + (Math.sqrt(r1) * r2) * coordinateDy;
		}

		result = "" + x + "," + y;
		return result;
	}

	public Location getRandomLocation() {
		Location result;
		Integer size;
		Integer randomNum;
		Random random;

		random = new Random();
		size = this.locationService.findAll().size();

		randomNum = random.nextInt(size);

		result = (Location) this.locationService.findAll().toArray()[randomNum];

		return result;
	}

	private Collection<domain.Character> generateCharacters(final Shelter shelter, final Integer numCharacters) {
		Collection<domain.Character> result;
		domain.Character character;

		result = new HashSet<>();

		for (int i = 0; i < numCharacters; i++) {//Poner número en configuración

			character = this.characterService.save(this.characterService.generateCharacter(shelter.getId()));
			result.add(character);
		}

		return result;
	}

	/**
	 * That methods get total capacity of items of a shelter
	 * 
	 * @author Luis
	 */
	public int getCurrentCapacity(final Shelter shelter) {
		Collection<Room> rooms;
		Collection<Item> items;
		int capacity = 0;

		rooms = this.roomService.findRoomsByShelter(shelter.getId());
		items = this.itemService.findItemsByShelter(shelter.getId());

		for (final Room r : rooms)
			if (r.getRoomDesign() instanceof Warehouse) {
				final Warehouse warehouse = (Warehouse) r.getRoomDesign();
				capacity += warehouse.getItemCapacity();
			}
		capacity -= items.size();

		return capacity;

	}

	public Integer getCurrentCharacterCapacity(final Shelter shelter) {
		Collection<Room> rooms;
		Collection<domain.Character> characters;
		int capacity = 0;
		DesignerConfiguration designerConfiguration;

		rooms = this.roomService.findRoomsByShelter(shelter.getId());
		characters = this.characterService.findCharactersByShelter(shelter.getId());
		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();

		for (final Room r : rooms)
			if (r.getRoomDesign() instanceof Barrack) {
				final Barrack barrack = (Barrack) r.getRoomDesign();
				capacity += barrack.getCharacterCapacity();
			}
		capacity += designerConfiguration.getShelterDefaultCapacity();
		capacity -= characters.size();

		return capacity;

	}

	private Double getInventoryCapacity(final Inventory inventory) {
		Double result;
		Double currentSize;

		currentSize = inventory.getMetal() + inventory.getWater() + inventory.getWood() + inventory.getFood();

		result = inventory.getWaterCapacity() + inventory.getFoodCapacity() + inventory.getWoodCapacity() + inventory.getMetalCapacity() - currentSize;

		return result;
	}

	public Inventory updateInventory(final Shelter shelter) {

		Inventory inventory, result;
		Collection<Room> resourceRooms;
		Integer minutes;
		long difference;
		Date currentDate;

		inventory = this.inventoryService.findInventoryByShelter(shelter.getId());

		resourceRooms = this.roomService.findResourceRoomsByShelter(shelter.getId());

		result = inventory;

		if (resourceRooms.size() > 0 && shelter.getLastView() != null) {

			currentDate = new Date();

			difference = currentDate.getTime() - shelter.getLastView().getTime();

			minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
			if (minutes > 0) {
				for (final Room room : resourceRooms) {
					if ((((ResourceRoom) room.getRoomDesign()).getFood() + inventory.getFood()) < inventory.getFoodCapacity())
						inventory.setFood(inventory.getFood() + ((ResourceRoom) room.getRoomDesign()).getFood() * minutes);
					else
						inventory.setFood(inventory.getFoodCapacity());

					if ((((ResourceRoom) room.getRoomDesign()).getWater() + inventory.getWater()) < inventory.getWaterCapacity())
						inventory.setWater(inventory.getWater() + ((ResourceRoom) room.getRoomDesign()).getWater() * minutes);
					else
						inventory.setWater(inventory.getWaterCapacity());
					if ((((ResourceRoom) room.getRoomDesign()).getWood() + inventory.getWood()) < inventory.getWoodCapacity())
						inventory.setWood(inventory.getWood() + ((ResourceRoom) room.getRoomDesign()).getWood() * minutes);
					else
						inventory.setWood(inventory.getWoodCapacity());

					if ((((ResourceRoom) room.getRoomDesign()).getMetal() + inventory.getMetal()) < inventory.getMetalCapacity())
						inventory.setMetal(inventory.getMetal() + ((ResourceRoom) room.getRoomDesign()).getMetal() * minutes);
					else
						inventory.setMetal(inventory.getMetalCapacity());
				}

				result = this.inventoryService.save(inventory);

				shelter.setLastView(new Date(System.currentTimeMillis() - 1));
			}
		} else
			shelter.setLastView(new Date(System.currentTimeMillis() - 1));

		return result;
	}
	private Shelter updateLocation(final Shelter shelter) {

		Move move;
		Shelter result;
		result = shelter;
		Date currentDate;

		move = this.moveService.findMostRecentMoveByShelter(shelter.getId());
		currentDate = new Date();

		if (move != null && move.getEndDate().before(currentDate) && !move.getLocation().equals(shelter.getLocation())) {
			shelter.setLocation(move.getLocation());
			shelter.setGpsCoordinates(this.generateRandomCoordinates(move.getLocation()));

			result = this.save(shelter);
		}

		return result;
	}

	public Collection<Shelter> findAllSheltersInLocationExceptPlayerShelter(final int locationId, final int shelterId) {
		Collection<Shelter> result;

		result = this.shelterRepository.findAllSheltersInLocationExceptPlayerShelter(locationId, shelterId);

		return result;
	}
}
