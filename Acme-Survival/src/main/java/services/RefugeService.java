
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	private WarehouseService	warehouseService;

	@Autowired
	private Validator			validator;


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
		Collection<Attack> attacksWhereAttacked, attacksWhereDefend;
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
			attacksWhereAttacked = this.attackService.findAttacksByAttacker(refuge.getId());
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

			for (final Attack attack : attacksWhereAttacked) {
				attack.setAttacker(result);
				this.attackService.save(attack);
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
		} else {
			//inventory initialization

			inventory = this.inventoryService.create();

			//Poner config
			inventory.setCapacity(10.);
			inventory.setFood(3.);
			inventory.setWater(3.);
			inventory.setWood(3.);
			inventory.setMetal(3.);

			//Room initialization
			rooms = this.createInitialRooms();

			for (final Room room : rooms) {
				room.setRefuge(result);
				this.roomService.save(room);
			}
		}

		inventory.setRefuge(result);
		this.inventoryService.save(inventory);

		return result;

	}
	public void delete(final Refuge refuge) {

		Assert.isTrue(refuge != null);
		Assert.isTrue(refuge.getId() != 0);
		Assert.isTrue(this.refugeRepository.exists(refuge.getId()));

		Collection<Player> playersKnowsRefuge;
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
		attacks = this.attackService.findAttacksByAttacker(refuge.getId());
		attacks.addAll(this.attackService.findAttacksByDefendant(refuge.getId()));
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

		if (refuge.getId() == 0) {

			actor = this.actorService.findActorByPrincipal();
			result = refuge;

			result.setCode(this.generateCode());
			result.setPlayer((Player) actor);
			result.setMomentOfCreation(new Date(System.currentTimeMillis() - 1));

		} else {
			result = this.findOne(refuge.getId());

			result.setName(refuge.getName());

		}
		this.validator.validate(result, binding);

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
		Room warehouseRoom;
		final Barrack barrack;
		final Warehouse warehouse;

		result = new HashSet<>();
		barrack = this.barrackService.create();
		warehouse = this.warehouseService.create();

		barrackRoom = this.roomService.create();
		warehouseRoom = this.roomService.create();
		barrackRoom.setResistance(5);//Poner en config
		barrackRoom.setRoomDesign(barrack);
		warehouseRoom.setResistance(5);
		warehouseRoom.setRoomDesign(warehouse);

		result.add(barrackRoom);
		result.add(warehouseRoom);

		return result;
	}
}
