
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Player;
import domain.Room;
import domain.RoomDesign;
import domain.Shelter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RoomServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private RoomService			roomService;

	@Autowired
	private RoomDesignService	roomDesignService;

	@Autowired
	private ShelterService		shelterService;

	@Autowired
	private ActorService		actorService;


	/**
	 * This test checks save a new room regarding functional requirement number 18.5: An actor who is authenticated as player must be able to
	 * manage his rooms which include create and delete them from the shelter.
	 */
	@Test
	public void testSaveRoomPositive() {
		Room room;
		Shelter shelter;
		RoomDesign roomDesign;
		Player player;
		int roomDesignId;

		super.authenticate("player1"); //The player knows the Shelter

		roomDesignId = super.getEntityId("RoomDesign1");

		room = this.roomService.create();
		roomDesign = this.roomDesignService.findOne(roomDesignId);
		player = (Player) this.actorService.findActorByPrincipal();

		shelter = this.shelterService.findShelterByPlayer(player.getId());

		room.setShelter(shelter);
		room.setRoomDesign(roomDesign);

		this.roomService.save(room);

		super.unauthenticate();
	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.5: An actor who is authenticated as player must be able to
	 * manage his rooms which include create and delete them from the shelter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveRoomNotLoggedNegative() {
		Room room;
		Shelter shelter;
		RoomDesign roomDesign;
		Player player;
		int roomDesignId;

		super.unauthenticate();

		roomDesignId = super.getEntityId("RoomDesign1");

		room = this.roomService.create();
		roomDesign = this.roomDesignService.findOne(roomDesignId);
		player = (Player) this.actorService.findActorByPrincipal();

		shelter = this.shelterService.findShelterByPlayer(player.getId());

		room.setShelter(shelter);
		room.setRoomDesign(roomDesign);

		this.roomService.save(room);

	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.5: An actor who is authenticated as player must be able to
	 * manage his rooms which include create and delete them from the shelter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveRoomNotShelterNegative() {
		Room room;
		Shelter shelter;
		RoomDesign roomDesign;
		Player player;
		int roomDesignId;

		super.authenticate("player2");

		roomDesignId = super.getEntityId("RoomDesign1");

		room = this.roomService.create();
		roomDesign = this.roomDesignService.findOne(roomDesignId);
		player = (Player) this.actorService.findActorByPrincipal();

		shelter = this.shelterService.findShelterByPlayer(player.getId());

		room.setShelter(shelter);
		room.setRoomDesign(roomDesign);

		this.roomService.save(room);

		super.unauthenticate();
	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.5: An actor who is authenticated as player must be able to
	 * manage his rooms which include create and delete them from the shelter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveRoomNotOwnShelterNegative() {
		Room room;
		Shelter shelter;
		RoomDesign roomDesign;
		Player player;
		int roomDesignId;

		super.authenticate("player1");

		roomDesignId = super.getEntityId("RoomDesign1");

		room = this.roomService.create();
		roomDesign = this.roomDesignService.findOne(roomDesignId);
		player = (Player) this.actorService.findActorByPrincipal();

		shelter = this.shelterService.findShelterByPlayer(player.getId());

		room.setShelter(shelter);
		room.setRoomDesign(roomDesign);
		super.unauthenticate();

		super.authenticate("Player3");
		this.roomService.save(room);

		super.unauthenticate();
	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.5: An actor who is authenticated as player must be able to
	 * manage his rooms which include create and delete them from the shelter.
	 */
	@Test
	public void testDeleteRoomPositive() {
		Room room;
		int roomId;

		super.authenticate("player1");

		roomId = super.getEntityId("Room1");
		room = this.roomService.findOne(roomId);

		this.roomService.delete(room);

		super.unauthenticate();
	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.5: An actor who is authenticated as player must be able to
	 * manage his rooms which include create and delete them from the shelter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteRoomNotLoggedNegative() {
		Room room;
		int roomId;

		roomId = super.getEntityId("Room1");
		room = this.roomService.findOne(roomId);

		this.roomService.delete(room);

	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.5: An actor who is authenticated as player must be able to
	 * manage his rooms which include create and delete them from the shelter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteRoomNotOwnerNegative() {
		Room room;
		int roomId;

		super.authenticate("player2");

		roomId = super.getEntityId("Room1");
		room = this.roomService.findOne(roomId);

		this.roomService.delete(room);

		super.unauthenticate();
	}

}
