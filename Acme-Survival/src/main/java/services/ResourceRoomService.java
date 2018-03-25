package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ResourceRoomRepository;
import domain.ResourceRoom;

@Service
@Transactional
public class ResourceRoomService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ResourceRoomRepository	resourceRoomRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public ResourceRoom create() {
		ResourceRoom result;

		result = new ResourceRoom();

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

