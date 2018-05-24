
package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Location;
import domain.Player;
import domain.Refuge;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RefugeServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private RefugeService	refugeService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks that the Player can Attack a Refuge that he already knows.
	 */
	@Test
	public void testSaveRefugePositive() {
		Refuge refuge;
		Player player;
		Location location;

		super.authenticate("player2"); //The player knows the Refuge

		refuge = this.refugeService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		location = this.refugeService.getRandomLocation();
		refuge.setName("Test Refuge");
		refuge.setCode(this.refugeService.generateCode());
		refuge.setLocation(location);
		refuge.setGpsCoordinates(this.refugeService.generateRandomCoordinates(location));
		refuge.setMomentOfCreation(new Date(System.currentTimeMillis() - 1));
		refuge.setPlayer(player);

		this.refugeService.save(refuge);

		super.unauthenticate();
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumNotLoggedNegative() {
		Refuge refuge;
		Player player;
		Location location;

		refuge = this.refugeService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		location = this.refugeService.getRandomLocation();
		refuge.setName("Test");
		refuge.setCode(this.refugeService.generateCode());
		refuge.setLocation(location);
		refuge.setGpsCoordinates(this.refugeService.generateRandomCoordinates(location));
		refuge.setMomentOfCreation(new Date());
		refuge.setPlayer(player);

		this.refugeService.save(refuge);

	}

	@Test(expected = ConstraintViolationException.class)
	public void testSaveForumBlankNameNegative() {
		Refuge refuge;
		Player player;
		Location location;

		super.authenticate("player1");
		refuge = this.refugeService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		location = this.refugeService.getRandomLocation();
		refuge.setName("");
		refuge.setCode(this.refugeService.generateCode());
		refuge.setLocation(location);
		refuge.setGpsCoordinates(this.refugeService.generateRandomCoordinates(location));
		refuge.setMomentOfCreation(new Date());
		refuge.setPlayer(player);

		this.refugeService.save(refuge);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEditRefugeNotOwnNegative() {
		Refuge refuge;
		int refugeId;

		super.authenticate("player2"); //The player knows the Refuge

		refuge = this.refugeService.create();
		refugeId = super.getEntityId("Refuge1");

		refuge = this.refugeService.findOne(refugeId);

		this.refugeService.save(refuge);

		super.unauthenticate();
	}

}
