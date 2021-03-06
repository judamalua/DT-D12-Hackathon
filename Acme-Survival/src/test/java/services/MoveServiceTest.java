
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Move;
import domain.Player;
import domain.Shelter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MoveServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private MoveService		moveService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ShelterService	shelterService;


	/**
	 * This driver checks several tests regarding functional requirement number 18.2: An actor who is authenticated as a player must be able to
	 * create a shelter if he or she did not create one previously and move it.
	 * 
	 * @author Manuel
	 */
	@Test
	public void testSaveMovePositive() {
		Move move;
		final Shelter shelter;
		Player player;

		super.authenticate("player1");

		move = this.moveService.create();
		player = (Player) this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(player.getId());

		move.setLocation(this.shelterService.getRandomLocation());
		move.setShelter(shelter);

		this.moveService.save(move);

		super.unauthenticate();
	}

	/**
	 * This driver checks several tests regarding functional requirement number 18.2: An actor who is authenticated as a player must be able to
	 * create a shelter if he or she did not create one previously and move it.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveMoveNotAuthenticatedNegative() {
		domain.Move move;
		Shelter shelter;
		Player player;

		move = this.moveService.create();
		player = (Player) this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(player.getId());

		move.setLocation(this.shelterService.getRandomLocation());
		move.setShelter(shelter);

		this.moveService.save(move);

	}

	/**
	 * This driver checks several tests regarding functional requirement number 18.2: An actor who is authenticated as a player must be able to
	 * create a shelter if he or she did not create one previously and move it.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveMoveNotOwnShelterNegative() {
		domain.Move move;
		Shelter shelter;
		Player player;

		super.authenticate("Player1");
		move = this.moveService.create();
		player = (Player) this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(player.getId());
		super.unauthenticate();

		move.setLocation(this.shelterService.getRandomLocation());
		move.setShelter(shelter);

		super.authenticate("Player2");
		this.moveService.save(move);

		super.unauthenticate();

	}

}
