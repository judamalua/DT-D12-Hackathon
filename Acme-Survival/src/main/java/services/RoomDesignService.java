
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RoomDesignRepository;
import domain.RoomDesign;

@Service
@Transactional
public class RoomDesignService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RoomDesignRepository	roomDesignRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Collection<RoomDesign> findAll() {

		Collection<RoomDesign> result;

		Assert.notNull(this.roomDesignRepository);
		result = this.roomDesignRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public RoomDesign findOne(final int roomDesignId) {

		RoomDesign result;

		result = this.roomDesignRepository.findOne(roomDesignId);

		return result;

	}

	public RoomDesign save(final RoomDesign roomDesign) {

		assert roomDesign != null;

		RoomDesign result;

		result = this.roomDesignRepository.save(roomDesign);

		return result;

	}

	public void delete(final RoomDesign roomDesign) {

		assert roomDesign != null;
		assert roomDesign.getId() != 0;

		Assert.isTrue(this.roomDesignRepository.exists(roomDesign.getId()));

		this.roomDesignRepository.delete(roomDesign);

	}
}

