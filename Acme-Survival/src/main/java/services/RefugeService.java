
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RefugeRepository;
import domain.Actor;
import domain.Attack;
import domain.Barrack;
import domain.Inventory;
import domain.Item;
import domain.Location;
import domain.Move;
import domain.Player;
import domain.Refuge;
import domain.Room;
import domain.Warehouse;

@Service
@Transactional
public class RefugeService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RefugeRepository	refugeRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PlayerService		playerService;

	@Autowired
	private AttackService		attackService;

	@Autowired
	private CharacterService	characterService;

	@Autowired
	private ItemService			itemService;

	@Autowired
	private MoveService			moveService;

	@Autowired
	private InventoryService	inventoryService;

	@Autowired
	private RoomService			roomService;

	@Autowired
	private BarrackService		barrackService;

	@Autowired
	private LocationService		locationService;

	@Autowired
	private Validator			validator;

	@Autowired
	private RoomDesignService	roomDesignService;


	// Simple CRUD methods --------------------------------------------------

	public Refuge create() {
		Refuge result;

		result = new Refuge();

		return result;
	}

	public Collection<Refuge> findAll() {

		Collection<Refuge> result;

		Assert.notNull(this.refugeRepository);
		result = this.refugeRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Page<Refuge> findAll(final Pageable pageable) {

		Page<Refuge> result;

		Assert.notNull(this.refugeRepository);
		result = this.refugeRepository.findAll(pageable);
		Assert.notNull(result);

		return result;

	}

	public Refuge findOne(final int refugeId) {

		Refuge result;
		Assert.isTrue(refugeId != 0);

		result = this.refugeRepository.findOne(refugeId);

		return result;

	}

	public Refuge save(final Refuge refuge) {

		Assert.isTrue(refuge != null);

		Refuge result;
		Collection<Player> playersKnowsRefuge;
		Attack attackWhereAttacked;
		Collection<Attack> attacksWhereDefend;
		Collection<Item> items;
		Collection<Move> moves;
		Collection<domain.Character> characters;
		Collection<Room> rooms;
		Actor actor;
		Inventory inventory;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor.equals(refuge.getPlayer()));

		result = this.refugeRepository.save(refuge);

		if (refuge.getId() != 0) {
			playersKnowsRefuge = this.playerService.findPlayersKnowsRefuge(refuge.getId());
			attackWhereAttacked = this.attackService.findAttacksByAttacker(refuge.getId());
			attacksWhereDefend = this.attackService.findAttacksByDefendant(refuge.getId());
			characters = this.characterService.findCharactersByRefuge(refuge.getId());
			items = this.itemService.findItemsByRefuge(refuge.getId());
			moves = this.moveService.findMovesByRefuge(refuge.getId());
			rooms = this.roomService.findRoomsByRefuge(refuge.getId());
			inventory = this.inventoryService.findInventoryByRefuge(result.getId());

			for (final Player player : playersKnowsRefuge) {
				player.getRefuges().remove(refuge);
				player.getRefuges().add(result);
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
				character.setRefuge(result);
				this.characterService.save(character);
			}
			for (final Item item : items) {
				item.setRefuge(result);
				this.itemService.save(item);
			}

			for (final Move move : moves) {
				move.setRefuge(result);
				this.moveService.save(move);
			}

			for (final Room room : rooms) {
				room.setRefuge(result);
				this.roomService.save(room);
			}

			inventory.setRefuge(result);
			this.inventoryService.save(inventory);

		} else {
			//inventory initialization

			inventory = this.inventoryService.create();

			//Poner config
			inventory.setCapacity(10.);
			inventory.setFood(3.);
			inventory.setWater(3.);
			inventory.setWood(4.);
			inventory.setMetal(3.);

			//Room initialization
			rooms = this.createInitialRooms();

			inventory.setRefuge(result);
			this.inventoryService.save(inventory);

			for (final Room room : rooms) {
				room.setRefuge(result);
				this.roomService.save(room);
			}

			characters = this.generateCharacters(result);
		}

		return result;

	}
	public void delete(final Refuge refuge) {

		Assert.isTrue(refuge != null);
		Assert.isTrue(refuge.getId() != 0);
		Assert.isTrue(this.refugeRepository.exists(refuge.getId()));

		Collection<Player> playersKnowsRefuge;
		Attack attackWhereAttacked;
		Collection<Attack> attacks;
		Collection<Item> items;
		Collection<Move> moves;
		Collection<domain.Character> characters;
		Collection<Room> rooms;
		Actor actor;
		final Inventory inventory;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor.equals(refuge.getPlayer()));

		playersKnowsRefuge = this.playerService.findPlayersKnowsRefuge(refuge.getId());
		attackWhereAttacked = this.attackService.findAttacksByAttacker(refuge.getId());
		attacks = this.attackService.findAttacksByDefendant(refuge.getId());
		attacks.add(attackWhereAttacked);
		characters = this.characterService.findCharactersByRefuge(refuge.getId());
		items = this.itemService.findItemsByRefuge(refuge.getId());
		moves = this.moveService.findMovesByRefuge(refuge.getId());
		inventory = this.inventoryService.findInventoryByRefuge(refuge.getId());
		rooms = this.roomService.findRoomsByRefuge(refuge.getId());

		for (final Player player : playersKnowsRefuge) {
			player.getRefuges().remove(refuge);
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

		this.refugeRepository.delete(refuge);

	}
	public Refuge reconstruct(final Refuge refuge, final BindingResult binding) {
		Refuge result;
		Actor actor;
		Location location;
		String gpsCoordinates;

		if (refuge.getId() == 0) {

			actor = this.actorService.findActorByPrincipal();
			location = this.getRandomLocation();
			gpsCoordinates = this.generateRandomCoordinates(location);
			result = refuge;

			result.setCode(this.generateCode());
			result.setPlayer((Player) actor);
			result.setMomentOfCreation(new Date(System.currentTimeMillis() - 1));
			result.setLocation(location);
			result.setGpsCoordinates(gpsCoordinates);

		} else {
			result = this.findOne(refuge.getId());

			result.setName(refuge.getName());

		}
		this.validator.validate(result, binding);
		this.refugeRepository.flush();

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

	private Collection<Room> createInitialRooms() {
		final Collection<Room> result;
		Room barrackRoom;
		final Barrack barrack;

		result = new HashSet<>();
		barrack = (Barrack) this.barrackService.findAll().toArray()[0];

		barrackRoom = this.roomService.create();
		barrackRoom.setResistance(10);//Poner en config
		barrackRoom.setRoomDesign(barrack);

		result.add(barrackRoom);

		return result;
	}

	public Refuge findRefugeByPlayer(final int playerId) {
		Refuge result;

		result = this.refugeRepository.findRefugeByPlayer(playerId);

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

	private Location getRandomLocation() {
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

	private Collection<domain.Character> generateCharacters(final Refuge refuge) {
		Collection<domain.Character> result;
		domain.Character character;

		result = new HashSet<>();

		for (int i = 0; i < 3; i++) {//Poner número en configuración

			character = this.characterService.save(this.characterService.generateCharacter(refuge.getId()));
			result.add(character);
		}

		return result;
	}

	/**
	 * That methods get total capacity of items of a refuge
	 * 
	 * @author Luis
	 */
	public int getCurrentCapacity(final Refuge refuge) {
		Collection<Room> rooms;
		Collection<Item> items;
		int capacity = 0;

		rooms = this.roomService.findRoomsByRefuge(refuge.getId());
		items = this.itemService.findItemsByRefuge(refuge.getId());

		for (final Room r : rooms)
			if (r.getRoomDesign() instanceof Warehouse) {
				final Warehouse warehouse = (Warehouse) r.getRoomDesign();
				capacity += warehouse.getItemCapacity();
			}
		capacity -= items.size();

		return capacity;

	}
}
