
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RoomDesignRepository;
import domain.Actor;
import domain.Designer;
import domain.RoomDesign;

@Service
@Transactional
public class RoomDesignService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RoomDesignRepository	roomDesignRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private ActorService			actorService;


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

	/**
	 * Saves the room design given by parameters
	 * 
	 * @param room
	 *            design
	 *            to be saved
	 * @return the room design saved
	 */
	public RoomDesign save(final RoomDesign roomDesign) {

		assert roomDesign != null;
		Actor actor;
		RoomDesign result;

		actor = this.actorService.findActorByPrincipal();

		// To edit or create a room design the principal must be a designer
		Assert.isTrue(actor instanceof Designer);

		result = this.roomDesignRepository.save(roomDesign);

		return result;

	}

	/**
	 * Deletes the room design in parameters
	 * 
	 * @param roomDesign
	 *            to be deleted
	 * @author Juanmi
	 */
	public void delete(final RoomDesign roomDesign) {

		assert roomDesign != null;
		assert roomDesign.getId() != 0;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to delete a room design is a designer.
		Assert.isTrue(actor instanceof Designer);
		Assert.isTrue(this.roomDesignRepository.exists(roomDesign.getId()));
		// Checking that the room design trying to be deleted is not in final mode.
		Assert.isTrue(!roomDesign.getFinalMode());

		Assert.isTrue(this.roomDesignRepository.exists(roomDesign.getId()));

		this.roomDesignRepository.delete(roomDesign);

	}

	// Other business methods ------------------------------------------------------------------------------------

	/**
	 * This method marks a room design as final mode
	 * 
	 * @param roomDesign
	 * @author Juanmi
	 */
	public void setFinalModeRoomDesign(final RoomDesign roomDesign) {
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		// Checking that the user trying to mark a room design as final mode is a designer.
		Assert.isTrue(actor instanceof Designer);

		// Checking that the room design that is going to be marked as final mode is not a final mode room design.
		Assert.isTrue(!roomDesign.getFinalMode());

		roomDesign.setFinalMode(true);

		this.save(roomDesign);
	}

	public Collection<RoomDesign> findFinalRoomDesign() {
		Collection<RoomDesign> result;

		result = this.roomDesignRepository.findFinalRoomDesign();

		return result;

	}

	/**
	 * This method returns a page of draft mode room designs
	 * 
	 * @param pageable
	 * @return a page of room design
	 * 
	 * @author Juanmi
	 */
	public Page<RoomDesign> findDraftRoomDesigns(final Pageable pageable) {
		Page<RoomDesign> result;
		Assert.notNull(pageable);

		result = this.roomDesignRepository.findDraftRoomDesigns(pageable);

		return result;
	}

	/**
	 * This method returns a page of draft mode room designs
	 * 
	 * @param pageable
	 * @return a page of room design
	 * 
	 * @author Juanmi
	 */
	public Page<RoomDesign> findFinalRoomDesigns(final Pageable pageable) {
		Page<RoomDesign> result;
		Assert.notNull(pageable);

		result = this.roomDesignRepository.findFinalRoomDesigns(pageable);

		return result;
	}
}
