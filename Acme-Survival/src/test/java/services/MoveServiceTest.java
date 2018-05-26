
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
import domain.Refuge;

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
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private RefugeService	refugeService;


	@Test
	public void testSaveMessagePositive() {
		Move move;
		final Refuge refuge;
		Player player;

		super.authenticate("player1");

		move = this.moveService.create();
		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());

		move.setLocation(this.refugeService.getRandomLocation());
		move.setRefuge(refuge);

		this.moveService.save(move);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMoveNotAuthenticatedNegative() {
		domain.Move move;
		Refuge refuge;
		Player player;

		move = this.moveService.create();
		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());

		move.setLocation(this.refugeService.getRandomLocation());
		move.setRefuge(refuge);

		this.moveService.save(move);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveMoveNotOwnRefugeNegative() {
		domain.Move move;
		Refuge refuge;
		Player player;

		super.authenticate("Player1");
		move = this.moveService.create();
		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());
		super.unauthenticate();

		move.setLocation(this.refugeService.getRandomLocation());
		move.setRefuge(refuge);

		super.authenticate("Player2");
		this.moveService.save(move);

		super.unauthenticate();

	}

}
