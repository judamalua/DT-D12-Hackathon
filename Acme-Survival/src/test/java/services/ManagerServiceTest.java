
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
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	@Autowired
	public ActorService		actorService;
	@Autowired
	public ManagerService	ManagerService;


	//******************************************Positive Methods*******************************************************************
	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * This test checks that a not registered Manager can register himself in the system,without errors
	 * 
	 * @author Luis
	 */
	@Test
	public void testRegisterANewManager() {
		Manager newManager;
		final Date birthDate = new Date();

		newManager = this.ManagerService.create();

		newManager.setName("Fernando");
		newManager.setSurname("Gutiérrez López");
		newManager.setBirthDate(birthDate);
		newManager.setEmail("ferguti90@gmail.com");
		newManager.setPhoneNumber("606587789");
		newManager.setAvatar("https://www.realbetisbalompie.es");

		this.ManagerService.save(newManager);

	}

	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * This test checks that a not registered Manager can register himself in the system,without errors
	 * and without optional attributes(address,phone number)
	 * 
	 * @author Luis
	 */
	@Test
	public void testRegisterANewManagerWithoutOptionalAtributes() {
		Manager newManager;
		final Date birthDate = new Date();

		newManager = this.ManagerService.create();

		newManager.setName("Fernando");
		newManager.setSurname("Gutiérrez López");
		newManager.setBirthDate(birthDate);
		newManager.setEmail("ferguti90@gmail.com");

		this.ManagerService.save(newManager);

		super.unauthenticate();
	}

	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * This test checks that a Manager can edit his profile correctly
	 * 
	 * @author Luis
	 */
	@Test
	public void testEditProfile() {
		super.authenticate("Manager1");

		Manager Manager;

		Manager = (Manager) this.actorService.findActorByPrincipal();
		Manager.setPhoneNumber("658877877");
		Manager.setEmail("Manager1newEmail@gmail.com");
		Manager.setAvatar("https://www.realbetisbalompie.es");

		this.ManagerService.save(Manager);

	}

	//******************************************Negative Methods*******************************************************************
	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * 
	 * This test checks that a not registered Manager cannot register himself in the system,without a valid name
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewManagerWithInvalidName() {
		Manager newManager;

		final Date birthDate = new Date();

		newManager = this.ManagerService.create();

		newManager.setName("");//Name not valid(is blank)
		newManager.setSurname("Gutiérrez López");
		newManager.setBirthDate(birthDate);
		newManager.setEmail("ferguti90@gmail.com");
		newManager.setPhoneNumber("606587789");
		newManager.setAvatar("https://www.realbetisbalompie.es");

		this.ManagerService.save(newManager);
		this.ManagerService.flush();

	}

	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * This test checks that a not registered Manager cannot register himself in the system,without a valid surname
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewManagerWithInvalidSurname() {
		super.authenticate("Admin1");
		Manager newManager;

		final Date birthDate = new Date();

		newManager = this.ManagerService.create();

		newManager.setName("Fernando");
		newManager.setSurname("");//Surname not valid(is blank)
		newManager.setBirthDate(birthDate);
		newManager.setEmail("ferguti90@gmail.com");
		newManager.setPhoneNumber("606587789");
		newManager.setAvatar("https://www.realbetisbalompie.es");

		this.ManagerService.save(newManager);
		this.ManagerService.flush();

	}
	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * This test checks that a not registered Manager cannot register himself in the system,without a valid email
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewManagerWithInvalidEmail() {
		Manager newManager;

		final Date birthDate = new Date();

		newManager = this.ManagerService.create();

		newManager.setName("Fernando");
		newManager.setSurname("Gutiérrez López");
		newManager.setBirthDate(birthDate);
		newManager.setEmail("ferguti90");//Email not valid(don´t follow the pattern of a email )
		newManager.setPhoneNumber("606587789");
		newManager.setAvatar("https://www.realbetisbalompie.es");

		this.ManagerService.save(newManager);
		this.ManagerService.flush();
	}

	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Manager.
	 * 
	 * 
	 * This test checks that a not registered Manager cannot register himself in the system,without a valid birth date
	 * 
	 * @author Luis
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewManagerWithInvalidBirthDate() throws ParseException, java.text.ParseException {
		Manager newManager;
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		final Date birthDate = format.parse("21/12/2030");

		newManager = this.ManagerService.create();

		newManager.setName("Fernando");
		newManager.setSurname("Gutiérrez López");
		newManager.setBirthDate(birthDate);//Birth date not valid(future date)
		newManager.setEmail("ferguti90@gmail.com");
		newManager.setPhoneNumber("606587789");
		newManager.setAvatar("https://www.realbetisbalompie.es");

		this.ManagerService.save(newManager);
		this.ManagerService.flush();

	}

	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * This test checks that a Manager cannot edit the profile of other Manager
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditProfileOfOtherManager() {
		super.authenticate("Manager1");

		Manager Manager;
		Integer ManagerId;

		ManagerId = super.getEntityId("Manager2");
		Manager = this.ManagerService.findOne(ManagerId);

		Manager.setPhoneNumber("658877784");
		Manager.setEmail("Manager2newEmail@gmail.com");
		Manager.setAvatar("https://www.realbetisbalompie.es");

		this.ManagerService.save(Manager);

		super.unauthenticate();

	}

	/**
	 * This test checks register a new manager regarding functional requirement number 21.1: An actor who is authenticated as a admin must be able to
	 * register new managers, admins, designers and moderators.
	 * 
	 * This test checks that unauthenticated Managers cannot edit the profile of other Manager
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNoLoggedManagerEditProfileOfOtherManager() {
		super.authenticate(null);

		Manager Manager;
		Integer ManagerId;

		ManagerId = super.getEntityId("Manager2");
		Manager = this.ManagerService.findOne(ManagerId);

		Manager.setPhoneNumber("658877784");
		Manager.setEmail("Manager2newEmail@gmail.com");
		Manager.setAvatar("https://www.realbetisbalompie.es");

		this.ManagerService.save(Manager);

		super.unauthenticate();

	}

}
