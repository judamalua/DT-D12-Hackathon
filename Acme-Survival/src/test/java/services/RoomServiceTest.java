
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Player;
import domain.Refuge;
import domain.Room;
import domain.RoomDesign;

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
	private RefugeService		refugeService;

	@Autowired
	private ActorService		actorService;


	/**
	 * This test checks that the Player can Attack a Refuge that he already knows.
	 */
	@Test
	public void testSaveRoomPositive() {
		Room room;
		Refuge refuge;
		RoomDesign roomDesign;
		Player player;
		int roomDesignId;

		super.authenticate("player1"); //The player knows the Refuge

		roomDesignId = super.getEntityId("RoomDesign1");

		room = this.roomService.create();
		roomDesign = this.roomDesignService.findOne(roomDesignId);
		player = (Player) this.actorService.findActorByPrincipal();

		refuge = this.refugeService.findRefugeByPlayer(player.getId());

		room.setRefuge(refuge);
		room.setRoomDesign(roomDesign);

		this.roomService.save(room);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveRoomNotLoggedNegative() {
		Room room;
		Refuge refuge;
		RoomDesign roomDesign;
		Player player;
		int roomDesignId;

		super.unauthenticate();

		roomDesignId = super.getEntityId("RoomDesign1");

		room = this.roomService.create();
		roomDesign = this.roomDesignService.findOne(roomDesignId);
		player = (Player) this.actorService.findActorByPrincipal();

		refuge = this.refugeService.findRefugeByPlayer(player.getId());

		room.setRefuge(refuge);
		room.setRoomDesign(roomDesign);

		this.roomService.save(room);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveRoomNotRefugeNegative() {
		Room room;
		Refuge refuge;
		RoomDesign roomDesign;
		Player player;
		int roomDesignId;

		super.authenticate("player2");

		roomDesignId = super.getEntityId("RoomDesign1");

		room = this.roomService.create();
		roomDesign = this.roomDesignService.findOne(roomDesignId);
		player = (Player) this.actorService.findActorByPrincipal();

		refuge = this.refugeService.findRefugeByPlayer(player.getId());

		room.setRefuge(refuge);
		room.setRoomDesign(roomDesign);

		this.roomService.save(room);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveRoomNotOwnRefugeNegative() {
		Room room;
		Refuge refuge;
		RoomDesign roomDesign;
		Player player;
		int roomDesignId;

		super.authenticate("player1");

		roomDesignId = super.getEntityId("RoomDesign1");

		room = this.roomService.create();
		roomDesign = this.roomDesignService.findOne(roomDesignId);
		player = (Player) this.actorService.findActorByPrincipal();

		refuge = this.refugeService.findRefugeByPlayer(player.getId());

		room.setRefuge(refuge);
		room.setRoomDesign(roomDesign);
		super.unauthenticate();

		super.authenticate("Player3");
		this.roomService.save(room);

		super.unauthenticate();
	}

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

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteRoomNotLoggedNegative() {
		Room room;
		int roomId;

		roomId = super.getEntityId("Room1");
		room = this.roomService.findOne(roomId);

		this.roomService.delete(room);

	}

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
