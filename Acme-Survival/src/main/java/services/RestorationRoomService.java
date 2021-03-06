
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RestorationRoomRepository;
import domain.Actor;
import domain.Designer;
import domain.RestorationRoom;

@Service
@Transactional
public class RestorationRoomService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RestorationRoomRepository	restorationRoomRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private ActorService				actorService;


	// Simple CRUD methods --------------------------------------------------

	public RestorationRoom create() {
		RestorationRoom result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		// Checking that the user trying to create a product is a manager.
		Assert.isTrue(actor instanceof Designer);

		result = new RestorationRoom();

		// Setting final mode to false due to when the designer is creating the room design it cannot be in final mode
		result.setFinalMode(false);

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
