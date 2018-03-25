package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RestorationRoomRepository;
import domain.RestorationRoom;

@Service
@Transactional
public class RestorationRoomService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RestorationRoomRepository	restorationRoomRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public RestorationRoom create() {
		RestorationRoom result;

		result = new RestorationRoom();

		return result;
	}

	public Collection<RestorationRoom> findAll() {

		Collection<RestorationRoom> result;

		Assert.notNull(this.restorationRoomRepository);
		result = this.restorationRoomRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public RestorationRoom findOne(final int restorationRoomId) {

		RestorationRoom result;

		result = this.restorationRoomRepository.findOne(restorationRoomId);

		return result;

	}

	public RestorationRoom save(final RestorationRoom restorationRoom) {

		assert restorationRoom != null;

		RestorationRoom result;

		result = this.restorationRoomRepository.save(restorationRoom);

		return result;

	}

	public void delete(final RestorationRoom restorationRoom) {

		assert restorationRoom != null;
		assert restorationRoom.getId() != 0;

		Assert.isTrue(this.restorationRoomRepository.exists(restorationRoom.getId()));

		this.restorationRoomRepository.delete(restorationRoom);

	}
}

