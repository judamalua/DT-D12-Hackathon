
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
import domain.Shelter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ShelterServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private ShelterService	shelterService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks save a new room regarding functional requirement number 18.2: An actor who is authenticated as player must be able to
	 * create a shelter if he or she did not create one previously and move it.
	 */
	@Test
	public void testSaveShelterPositive() {
		Shelter shelter;
		Player player;
		Location location;

		super.authenticate("player2"); //The player knows the Shelter

		shelter = this.shelterService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		location = this.shelterService.getRandomLocation();
		shelter.setName("Test Shelter");
		shelter.setCode(this.shelterService.generateCode());
		shelter.setLocation(location);
		shelter.setGpsCoordinates(this.shelterService.generateRandomCoordinates(location));
		shelter.setMomentOfCreation(new Date(System.currentTimeMillis() - 1));
		shelter.setPlayer(player);

		this.shelterService.save(shelter);

		super.unauthenticate();
	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.2: An actor who is authenticated as player must be able to
	 * create a shelter if he or she did not create one previously and move it.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumNotLoggedNegative() {
		Shelter shelter;
		Player player;
		Location location;

		super.unauthenticate();
		shelter = this.shelterService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		location = this.shelterService.getRandomLocation();
		shelter.setName("Test name");
		shelter.setCode(this.shelterService.generateCode());
		shelter.setLocation(location);
		shelter.setGpsCoordinates(this.shelterService.generateRandomCoordinates(location));
		shelter.setMomentOfCreation(new Date());
		shelter.setPlayer(player);

		this.shelterService.save(shelter);

	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.2: An actor who is authenticated as player must be able to
	 * create a shelter if he or she did not create one previously and move it.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSaveForumBlankNameNegative() {
		Shelter shelter;
		Player player;
		Location location;

		super.authenticate("player1");
		shelter = this.shelterService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		location = this.shelterService.getRandomLocation();
		shelter.setName("");
		shelter.setCode(this.shelterService.generateCode());
		shelter.setLocation(location);
		shelter.setGpsCoordinates(this.shelterService.generateRandomCoordinates(location));
		shelter.setMomentOfCreation(new Date());
		shelter.setPlayer(player);

		this.shelterService.save(shelter);

		super.unauthenticate();
	}

	/**
	 * This test checks save a new room regarding functional requirement number 18.2: An actor who is authenticated as player must be able to
	 * create a shelter if he or she did not create one previously and move it.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditShelterNotOwnNegative() {
		Shelter shelter;
		int shelterId;

		super.authenticate("player2"); //The player knows the Shelter

		shelter = this.shelterService.create();
		shelterId = super.getEntityId("Shelter1");

		shelter = this.shelterService.findOne(shelterId);

		this.shelterService.save(shelter);

		super.unauthenticate();
	}

}
