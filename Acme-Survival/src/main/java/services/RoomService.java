
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

import repositories.RoomRepository;
import domain.Player;
import domain.Refuge;
import domain.Room;

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
	public void delete(final Room room) {

		Assert.notNull(room);
		Assert.isTrue(room.getId() != 0);

		Assert.isTrue(this.roomRepository.exists(room.getId()));

		this.roomRepository.delete(room);

	}

	public Collection<Room> findRoomsByRefuge(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Room> result;

		result = this.roomRepository.findRoomsByRefuge(refugeId);

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

			result.setResistance(result.getRoomDesign().getMaxResistance());
			result.setRefuge(refuge);
		}

		Assert.notNull(result);

		this.validator.validate(result, binding);

		return result;
	}
}
