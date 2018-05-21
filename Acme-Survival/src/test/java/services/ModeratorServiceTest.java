
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
import domain.Moderator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ModeratorServiceTest extends AbstractTest {

	@Autowired
	public ActorService		actorService;
	@Autowired
	public ModeratorService	ModeratorService;


	//TODO
	//******************************************Positive Methods*******************************************************************
	/**
	 * An actor who is not authenticated must be able to: Register to the system as a Moderator.
	 * 
	 * 
	 * This test checks that a not registered Moderator can register himself in the system,without errors
	 * 
	 * @author Luis
	 */
	@Test
	public void testRegisterANewModerator() {
		Moderator newModerator;
		final Date birthDate = new Date();

		newModerator = this.ModeratorService.create();

		newModerator.setName("Fernando");
		newModerator.setSurname("Gutiérrez López");
		newModerator.setBirthDate(birthDate);
		newModerator.setEmail("ferguti90@gmail.com");
		newModerator.setPhoneNumber("606587789");
		newModerator.setAvatar("https://www.realbetisbalompie.es");

		this.ModeratorService.save(newModerator);

	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Moderator.
	 * 
	 * 
	 * This test checks that a not registered Moderator can register himself in the system,without errors
	 * and without optional attributes(address,phone number)
	 * 
	 * @author Luis
	 */
	@Test
	public void testRegisterANewModeratorWithoutOptionalAtributes() {
		Moderator newModerator;
		final Date birthDate = new Date();

		newModerator = this.ModeratorService.create();

		newModerator.setName("Fernando");
		newModerator.setSurname("Gutiérrez López");
		newModerator.setBirthDate(birthDate);
		newModerator.setEmail("ferguti90@gmail.com");

		this.ModeratorService.save(newModerator);

		super.unauthenticate();
	}

	/**
	 * 
	 * 
	 * This test checks that a Moderator can edit his profile correctly
	 * 
	 * @author Luis
	 */
	@Test
	public void testEditProfile() {
		super.authenticate("Moderator1");

		Moderator Moderator;

		Moderator = (Moderator) this.actorService.findActorByPrincipal();
		Moderator.setPhoneNumber("658877877");
		Moderator.setEmail("Moderator1newEmail@gmail.com");
		Moderator.setAvatar("https://www.realbetisbalompie.es");

		this.ModeratorService.save(Moderator);

	}

	//******************************************Negative Methods*******************************************************************
	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Moderator.
	 * 
	 * 
	 * This test checks that a not registered Moderator cannot register himself in the system,without a valid name
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewModeratorWithInvalidName() {
		Moderator newModerator;

		final Date birthDate = new Date();

		newModerator = this.ModeratorService.create();

		newModerator.setName("");//Name not valid(is blank)
		newModerator.setSurname("Gutiérrez López");
		newModerator.setBirthDate(birthDate);
		newModerator.setEmail("ferguti90@gmail.com");
		newModerator.setPhoneNumber("606587789");
		newModerator.setAvatar("https://www.realbetisbalompie.es");

		this.ModeratorService.save(newModerator);
		this.ModeratorService.flush();

	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Moderator.
	 * 
	 * This test checks that a not registered Moderator cannot register himself in the system,without a valid surname
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewModeratorWithInvalidSurname() {
		super.authenticate("Admin1");
		Moderator newModerator;

		final Date birthDate = new Date();

		newModerator = this.ModeratorService.create();

		newModerator.setName("Fernando");
		newModerator.setSurname("");//Surname not valid(is blank)
		newModerator.setBirthDate(birthDate);
		newModerator.setEmail("ferguti90@gmail.com");
		newModerator.setPhoneNumber("606587789");
		newModerator.setAvatar("https://www.realbetisbalompie.es");

		this.ModeratorService.save(newModerator);
		this.ModeratorService.flush();

	}
	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Moderator.
	 * 
	 * This test checks that a not registered Moderator cannot register himself in the system,without a valid email
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewModeratorWithInvalidEmail() {
		Moderator newModerator;

		final Date birthDate = new Date();

		newModerator = this.ModeratorService.create();

		newModerator.setName("Fernando");
		newModerator.setSurname("Gutiérrez López");
		newModerator.setBirthDate(birthDate);
		newModerator.setEmail("ferguti90");//Email not valid(don´t follow the pattern of a email )
		newModerator.setPhoneNumber("606587789");
		newModerator.setAvatar("https://www.realbetisbalompie.es");

		this.ModeratorService.save(newModerator);
		this.ModeratorService.flush();
	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Moderator.
	 * 
	 * 
	 * This test checks that a not registered Moderator cannot register himself in the system,without a valid birth date
	 * 
	 * @author Luis
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewModeratorWithInvalidBirthDate() throws ParseException, java.text.ParseException {
		Moderator newModerator;
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		final Date birthDate = format.parse("21/12/2030");

		newModerator = this.ModeratorService.create();

		newModerator.setName("Fernando");
		newModerator.setSurname("Gutiérrez López");
		newModerator.setBirthDate(birthDate);//Birth date not valid(future date)
		newModerator.setEmail("ferguti90@gmail.com");
		newModerator.setPhoneNumber("606587789");
		newModerator.setAvatar("https://www.realbetisbalompie.es");

		this.ModeratorService.save(newModerator);
		this.ModeratorService.flush();

	}

	/**
	 * 
	 * 
	 * This test checks that a Moderator cannot edit the profile of other Moderator
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditProfileOfOtherModerator() {
		super.authenticate("Moderator1");

		Moderator Moderator;
		Integer ModeratorId;

		ModeratorId = super.getEntityId("Moderator2");
		Moderator = this.ModeratorService.findOne(ModeratorId);

		Moderator.setPhoneNumber("658877784");
		Moderator.setEmail("Moderator2newEmail@gmail.com");
		Moderator.setAvatar("https://www.realbetisbalompie.es");

		this.ModeratorService.save(Moderator);

		super.unauthenticate();

	}

	/**
	 * 
	 * 
	 * This test checks that unauthenticated Moderators cannot edit the profile of other Moderator
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNoLoggedModeratorEditProfileOfOtherModerator() {
		super.authenticate(null);

		Moderator Moderator;
		Integer ModeratorId;

		ModeratorId = super.getEntityId("Moderator2");
		Moderator = this.ModeratorService.findOne(ModeratorId);

		Moderator.setPhoneNumber("658877784");
		Moderator.setEmail("Moderator2newEmail@gmail.com");
		Moderator.setAvatar("https://www.realbetisbalompie.es");

		this.ModeratorService.save(Moderator);

		super.unauthenticate();

	}

}
