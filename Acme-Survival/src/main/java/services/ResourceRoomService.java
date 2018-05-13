
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ResourceRoomRepository;
import domain.Actor;
import domain.Designer;
import domain.ResourceRoom;

@Service
@Transactional
public class ResourceRoomService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ResourceRoomRepository	resourceRoomRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods --------------------------------------------------

	public ResourceRoom create() {
		ResourceRoom result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		// Checking that the user trying to create a product is a manager.
		Assert.isTrue(actor instanceof Designer);

		result = new ResourceRoom();

		// Setting final mode to false due to when the designer is creating the room design it cannot be in final mode
		result.setFinalMode(false);

		return result;
	}

	public Collection<ResourceRoom> findAll() {

		Collection<ResourceRoom> result;

		Assert.notNull(this.resourceRoomRepository);
		result = this.resourceRoomRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public ResourceRoom findOne(final int resourceRoomId) {

		ResourceRoom result;

		result = this.resourceRoomRepository.findOne(resourceRoomId);

		return result;

	}

	public ResourceRoom save(final ResourceRoom resourceRoom) {

		assert resourceRoom != null;

		ResourceRoom result;

		result = this.resourceRoomRepository.save(resourceRoom);

		return result;

	}

	public void delete(final ResourceRoom resourceRoom) {

		assert resourceRoom != null;
		assert resourceRoom.getId() != 0;

		Assert.isTrue(this.resourceRoomRepository.exists(resourceRoom.getId()));

		this.resourceRoomRepository.delete(resourceRoom);

	}
}
