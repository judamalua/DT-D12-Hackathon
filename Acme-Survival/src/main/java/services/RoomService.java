
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RoomRepository;
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

		characters = this.characterService.findCharactersByRoom(room.getId());

		result = this.roomRepository.save(room);

		for (final domain.Character character : characters) {
			character.setRoom(result);
			this.characterService.save(character);
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
}
