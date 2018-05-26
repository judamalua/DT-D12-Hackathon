
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RoomRepository;
import domain.Actor;
import domain.Barrack;
import domain.Inventory;
import domain.Player;
import domain.Refuge;
import domain.ResourceRoom;
import domain.Room;
import domain.Warehouse;

@Service
@Transactional
public class RoomService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RoomRepository		roomRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private CharacterService	characterService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RefugeService		refugeService;

	@Autowired
	private InventoryService	inventoryService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods --------------------------------------------------

	public Room create() {
		Room result;

		result = new Room();

		return result;
	}

	public Collection<Room> findAll() {

		Collection<Room> result;

		Assert.notNull(this.roomRepository);
		result = this.roomRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Room findOne(final int roomId) {

		Room result;

		result = this.roomRepository.findOne(roomId);

		return result;

	}

	public Room save(final Room room) {

		Assert.notNull(room);

		Room result;
		Collection<domain.Character> characters;
		Refuge refuge;
		Actor actor;
		Inventory inventory;

		actor = this.actorService.findActorByPrincipal();

		if (actor instanceof Player) {

			refuge = this.refugeService.findRefugeByPlayer(actor.getId());
			Assert.notNull(refuge);
			Assert.isTrue(room.getRefuge().equals(refuge));

			inventory = this.inventoryService.findInventoryByRefuge(refuge.getId());
			if (room.getId() == 0) {
				Assert.isTrue(inventory.getMetal() >= room.getRoomDesign().getCostMetal() && inventory.getWood() >= room.getRoomDesign().getCostWood(), "Not enough resources");
				inventory.setMetal(inventory.getMetal() - room.getRoomDesign().getCostMetal());
				inventory.setWood(inventory.getWood() - room.getRoomDesign().getCostWood());
			}
			this.inventoryService.save(inventory);
		}
		result = this.roomRepository.save(room);

		if (room.getId() != 0) {
			characters = this.characterService.findCharactersByRoom(room.getId());

			for (final domain.Character character : characters) {
				character.setRoom(result);
				this.characterService.save(character);
			}
		}

		return result;

	}

	public Room saveRoomByAttack(final Room room) {
		Room result;

		result = this.roomRepository.save(room);

		return result;
	}
	public void delete(final Room room) {

		Assert.notNull(room);
		Assert.isTrue(room.getId() != 0);

		Assert.isTrue(this.roomRepository.exists(room.getId()));
		Collection<domain.Character> characters;
		Integer currentCapacity;
		Actor actor;
		Refuge refuge;

		characters = this.characterService.findCharactersByRoom(room.getId());
		currentCapacity = this.refugeService.getCurrentCharacterCapacity(room.getRefuge());
		actor = this.actorService.findActorByPrincipal();

		if (actor instanceof Player) {
			refuge = this.refugeService.findRefugeByPlayer(actor.getId());
			Assert.notNull(refuge);
			Assert.isTrue(room.getRefuge().equals(refuge));
		}

		if (!(room.getRoomDesign() instanceof Barrack)) {
			if (room.getRoomDesign() instanceof Warehouse) {
				Assert.isTrue(this.refugeService.getCurrentCapacity(room.getRefuge()) > 0, "You have a lot of objects");
			}
			for (final domain.Character character : characters) {
				character.setRoom(null);
				this.characterService.save(character);
			}

		} else {
			Assert.isTrue(characters.size() > (currentCapacity - ((Barrack) room.getRoomDesign()).getCharacterCapacity()), "You not have space");
		}

		this.roomRepository.delete(room);
	}

	public Collection<Room> findRoomsByRefuge(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Room> result;

		result = this.roomRepository.findRoomsByRefuge(refugeId);

		return result;
	}

	public Collection<Room> findResourceRoomsByRefuge(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Room> rooms, result;

		rooms = this.roomRepository.findRoomsByRefuge(refugeId);
		result = new HashSet<>();

		for (final Room room : rooms) {
			if (room.getRoomDesign() instanceof ResourceRoom) {
				result.add(room);
			}
		}

		return result;
	}

	public Page<Room> findRoomsByRefuge(final int refugeId, final Pageable pageable) {
		Assert.isTrue(refugeId != 0);

		Page<Room> result;

		result = this.roomRepository.findRoomsByRefuge(refugeId, pageable);

		return result;
	}

	public Room reconstruct(final Room room, final BindingResult binding) {
		Room result = null;
		Player actor;
		Refuge refuge;

		if (room.getId() == 0) {

			actor = (Player) this.actorService.findActorByPrincipal();
			result = room;
			refuge = this.refugeService.findRefugeByPlayer(actor.getId());

			result.setRefuge(refuge);
		}

		Assert.notNull(result);

		this.validator.validate(result, binding);
		this.roomRepository.flush();

		return result;
	}
}
