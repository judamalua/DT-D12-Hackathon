
package services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PlayerServiceTest extends AbstractTest {

	@Autowired
	public ActorService		actorService;
	@Autowired
	public PlayerService	PlayerService;


	//TODO
	//******************************************Positive Methods*******************************************************************
	/**
	 * An actor who is not authenticated must be able to: Register to the system as a Player.
	 * 
	 * 
	 * This test checks that a not registered Player can register himself in the system,without errors
	 * 
	 * @author Luis
	 */
	@Test
	public void testRegisterANewPlayer() {
		Player newPlayer;
		final Date birthDate = new Date();

		newPlayer = this.PlayerService.create();

		newPlayer.setName("Fernando");
		newPlayer.setSurname("Gutiérrez López");
		newPlayer.setBirthDate(birthDate);
		newPlayer.setEmail("ferguti90@gmail.com");
		newPlayer.setPhoneNumber("606587789");
		newPlayer.setAvatar("https://www.realbetisbalompie.es");

		this.PlayerService.save(newPlayer);

	}

	/**
	 * 4.2 An actor who is not authenticated must be able to:List the Players of the system and navigate to their profiles, which include personal data and the list of rendezvouses that they've attended or are going to attend.
	 * 
	 * 
	 * This test checks that a not registered actor can list the Players in the system and navigate to their profiles
	 * 
	 * @author Luis
	 */
	@Test
	public void testActorNotRegisterdedCanListPlayers() {
		super.authenticate(null);

		this.PlayerService.findAll();//List Players in the system

		this.PlayerService.findOne(this.getEntityId("Player2"));//Navigate to their profiles

		super.unauthenticate();

	}

	/**
	 * 5.1 An actor who is authenticated as a Player must be able to:Do the same as an actor who is not authenticated, but register to the system.
	 * 
	 * This test checks that a registered actor can list the Players in the system and navigate to their profiles
	 * 
	 * @author Luis
	 */
	@Test
	public void testActorRegisterdedCanListPlayers() {
		super.authenticate("Player1");

		this.PlayerService.findAll();//List Players in the system

		this.PlayerService.findOne(this.getEntityId("Player2"));//Navigate to their profiles

		super.unauthenticate();

	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Player.
	 * 
	 * 
	 * This test checks that a not registered Player can register himself in the system,without errors
	 * and without optional attributes(address,phone number)
	 * 
	 * @author Luis
	 */
	@Test
	public void testRegisterANewPlayerWithoutOptionalAtributes() {
		Player newPlayer;
		final Date birthDate = new Date();

		newPlayer = this.PlayerService.create();

		newPlayer.setName("Fernando");
		newPlayer.setSurname("Gutiérrez López");
		newPlayer.setBirthDate(birthDate);
		newPlayer.setEmail("ferguti90@gmail.com");

		this.PlayerService.save(newPlayer);

		super.unauthenticate();
	}

	/**
	 * 
	 * 
	 * This test checks that a Player can edit his profile correctly
	 * 
	 * @author Luis
	 */
	@Test
	public void testEditProfile() {
		super.authenticate("Player1");

		Player Player;

		Player = (Player) this.actorService.findActorByPrincipal();
		Player.setPhoneNumber("658877877");
		Player.setEmail("Player1newEmail@gmail.com");
		Player.setAvatar("https://www.realbetisbalompie.es");

		this.PlayerService.save(Player);

	}

	//******************************************Negative Methods*******************************************************************
	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Player.
	 * 
	 * 
	 * This test checks that a not registered Player cannot register himself in the system,without a valid name
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewPlayerWithInvalidName() {
		Player newPlayer;

		final Date birthDate = new Date();

		newPlayer = this.PlayerService.create();

		newPlayer.setName("");//Name not valid(is blank)
		newPlayer.setSurname("Gutiérrez López");
		newPlayer.setBirthDate(birthDate);
		newPlayer.setEmail("ferguti90@gmail.com");
		newPlayer.setPhoneNumber("606587789");
		newPlayer.setAvatar("https://www.realbetisbalompie.es");

		this.PlayerService.save(newPlayer);
		this.PlayerService.flush();

	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Player.
	 * 
	 * This test checks that a not registered Player cannot register himself in the system,without a valid surname
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewPlayerWithInvalidSurname() {
		Player newPlayer;

		final Date birthDate = new Date();

		newPlayer = this.PlayerService.create();

		newPlayer.setName("Fernando");
		newPlayer.setSurname("");//Surname not valid(is blank)
		newPlayer.setBirthDate(birthDate);
		newPlayer.setEmail("ferguti90@gmail.com");
		newPlayer.setPhoneNumber("606587789");
		newPlayer.setAvatar("https://www.realbetisbalompie.es");

		this.PlayerService.save(newPlayer);
		this.PlayerService.flush();

	}
	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Player.
	 * 
	 * This test checks that a not registered Player cannot register himself in the system,without a valid email
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewPlayerWithInvalidEmail() {
		Player newPlayer;

		final Date birthDate = new Date();

		newPlayer = this.PlayerService.create();

		newPlayer.setName("Fernando");
		newPlayer.setSurname("Gutiérrez López");
		newPlayer.setBirthDate(birthDate);
		newPlayer.setEmail("ferguti90");//Email not valid(don´t follow the pattern of a email )
		newPlayer.setPhoneNumber("606587789");
		newPlayer.setAvatar("https://www.realbetisbalompie.es");

		this.PlayerService.save(newPlayer);
		this.PlayerService.flush();
	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Player.
	 * 
	 * 
	 * This test checks that a not registered Player cannot register himself in the system,without a valid birth date
	 * 
	 * @author Luis
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewPlayerWithInvalidBirthDate() throws ParseException, java.text.ParseException {
		Player newPlayer;
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		final Date birthDate = format.parse("21/12/2030");

		newPlayer = this.PlayerService.create();

		newPlayer.setName("Fernando");
		newPlayer.setSurname("Gutiérrez López");
		newPlayer.setBirthDate(birthDate);//Birth date not valid(future date)
		newPlayer.setEmail("ferguti90@gmail.com");
		newPlayer.setPhoneNumber("606587789");
		newPlayer.setAvatar("https://www.realbetisbalompie.es");

		this.PlayerService.save(newPlayer);
		this.PlayerService.flush();

	}

	/**
	 * 
	 * 
	 * This test checks that a Player cannot edit the profile of other Player
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditProfileOfOtherPlayer() {
		super.authenticate("Player1");

		Player Player;
		Integer PlayerId;

		PlayerId = super.getEntityId("Player2");
		Player = this.PlayerService.findOne(PlayerId);

		Player.setPhoneNumber("658877784");
		Player.setEmail("Player2newEmail@gmail.com");
		Player.setAvatar("https://www.realbetisbalompie.es");

		this.PlayerService.save(Player);

		super.unauthenticate();

	}

	/**
	 * 
	 * 
	 * This test checks that unauthenticated Players cannot edit the profile of other Player
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNoLoggedPlayerEditProfileOfOtherPlayer() {
		super.authenticate(null);

		Player Player;
		Integer PlayerId;

		PlayerId = super.getEntityId("Player2");
		Player = this.PlayerService.findOne(PlayerId);

		Player.setPhoneNumber("658877784");
		Player.setEmail("Player2newEmail@gmail.com");
		Player.setAvatar("https://www.realbetisbalompie.es");

		this.PlayerService.save(Player);

		super.unauthenticate();

	}

}
