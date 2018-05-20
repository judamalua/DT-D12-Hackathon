
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
import domain.Designer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DesignerServiceTest extends AbstractTest {

	@Autowired
	public ActorService		actorService;
	@Autowired
	public DesignerService	DesignerService;


	//TODO
	//******************************************Positive Methods*******************************************************************
	/**
	 * An actor who is not authenticated must be able to: Register to the system as a Designer.
	 * 
	 * 
	 * This test checks that a not registered Designer can register himself in the system,without errors
	 * 
	 * @author Luis
	 */
	@Test
	public void testRegisterANewDesigner() {
		Designer newDesigner;
		final Date birthDate = new Date();

		newDesigner = this.DesignerService.create();

		newDesigner.setName("Fernando");
		newDesigner.setSurname("Gutiérrez López");
		newDesigner.setBirthDate(birthDate);
		newDesigner.setEmail("ferguti90@gmail.com");
		newDesigner.setPhoneNumber("606587789");
		newDesigner.setAvatar("https://www.realbetisbalompie.es");

		this.DesignerService.save(newDesigner);

	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Designer.
	 * 
	 * 
	 * This test checks that a not registered Designer can register himself in the system,without errors
	 * and without optional attributes(address,phone number)
	 * 
	 * @author Luis
	 */
	@Test
	public void testRegisterANewDesignerWithoutOptionalAtributes() {
		Designer newDesigner;
		final Date birthDate = new Date();

		newDesigner = this.DesignerService.create();

		newDesigner.setName("Fernando");
		newDesigner.setSurname("Gutiérrez López");
		newDesigner.setBirthDate(birthDate);
		newDesigner.setEmail("ferguti90@gmail.com");

		this.DesignerService.save(newDesigner);

		super.unauthenticate();
	}

	/**
	 * 
	 * 
	 * This test checks that a Designer can edit his profile correctly
	 * 
	 * @author Luis
	 */
	@Test
	public void testEditProfile() {
		super.authenticate("Designer1");

		Designer Designer;

		Designer = (Designer) this.actorService.findActorByPrincipal();
		Designer.setPhoneNumber("658877877");
		Designer.setEmail("Designer1newEmail@gmail.com");
		Designer.setAvatar("https://www.realbetisbalompie.es");

		this.DesignerService.save(Designer);

	}

	//******************************************Negative Methods*******************************************************************
	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Designer.
	 * 
	 * 
	 * This test checks that a not registered Designer cannot register himself in the system,without a valid name
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewDesignerWithInvalidName() {
		Designer newDesigner;

		final Date birthDate = new Date();

		newDesigner = this.DesignerService.create();

		newDesigner.setName("");//Name not valid(is blank)
		newDesigner.setSurname("Gutiérrez López");
		newDesigner.setBirthDate(birthDate);
		newDesigner.setEmail("ferguti90@gmail.com");
		newDesigner.setPhoneNumber("606587789");
		newDesigner.setAvatar("https://www.realbetisbalompie.es");

		this.DesignerService.save(newDesigner);
		this.DesignerService.flush();

	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Designer.
	 * 
	 * This test checks that a not registered Designer cannot register himself in the system,without a valid surname
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewDesignerWithInvalidSurname() {
		super.authenticate("Admin1");
		Designer newDesigner;

		final Date birthDate = new Date();

		newDesigner = this.DesignerService.create();

		newDesigner.setName("Fernando");
		newDesigner.setSurname("");//Surname not valid(is blank)
		newDesigner.setBirthDate(birthDate);
		newDesigner.setEmail("ferguti90@gmail.com");
		newDesigner.setPhoneNumber("606587789");
		newDesigner.setAvatar("https://www.realbetisbalompie.es");

		this.DesignerService.save(newDesigner);
		this.DesignerService.flush();

	}
	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Designer.
	 * 
	 * This test checks that a not registered Designer cannot register himself in the system,without a valid email
	 * 
	 * @author Luis
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewDesignerWithInvalidEmail() {
		Designer newDesigner;

		final Date birthDate = new Date();

		newDesigner = this.DesignerService.create();

		newDesigner.setName("Fernando");
		newDesigner.setSurname("Gutiérrez López");
		newDesigner.setBirthDate(birthDate);
		newDesigner.setEmail("ferguti90");//Email not valid(don´t follow the pattern of a email )
		newDesigner.setPhoneNumber("606587789");
		newDesigner.setAvatar("https://www.realbetisbalompie.es");

		this.DesignerService.save(newDesigner);
		this.DesignerService.flush();
	}

	/**
	 * 4.1 An actor who is not authenticated must be able to: Register to the system as a Designer.
	 * 
	 * 
	 * This test checks that a not registered Designer cannot register himself in the system,without a valid birth date
	 * 
	 * @author Luis
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterANewDesignerWithInvalidBirthDate() throws ParseException, java.text.ParseException {
		Designer newDesigner;
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		final Date birthDate = format.parse("21/12/2030");

		newDesigner = this.DesignerService.create();

		newDesigner.setName("Fernando");
		newDesigner.setSurname("Gutiérrez López");
		newDesigner.setBirthDate(birthDate);//Birth date not valid(future date)
		newDesigner.setEmail("ferguti90@gmail.com");
		newDesigner.setPhoneNumber("606587789");
		newDesigner.setAvatar("https://www.realbetisbalompie.es");

		this.DesignerService.save(newDesigner);
		this.DesignerService.flush();

	}

	/**
	 * 
	 * 
	 * This test checks that a Designer cannot edit the profile of other Designer
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditProfileOfOtherDesigner() {
		super.authenticate("Designer1");

		Designer Designer;
		Integer DesignerId;

		DesignerId = super.getEntityId("Designer2");
		Designer = this.DesignerService.findOne(DesignerId);

		Designer.setPhoneNumber("658877784");
		Designer.setEmail("Designer2newEmail@gmail.com");
		Designer.setAvatar("https://www.realbetisbalompie.es");

		this.DesignerService.save(Designer);

		super.unauthenticate();

	}

	/**
	 * 
	 * 
	 * This test checks that unauthenticated Designers cannot edit the profile of other Designer
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNoLoggedDesignerEditProfileOfOtherDesigner() {
		super.authenticate(null);

		Designer Designer;
		Integer DesignerId;

		DesignerId = super.getEntityId("Designer2");
		Designer = this.DesignerService.findOne(DesignerId);

		Designer.setPhoneNumber("658877784");
		Designer.setEmail("Designer2newEmail@gmail.com");
		Designer.setAvatar("https://www.realbetisbalompie.es");

		this.DesignerService.save(Designer);

		super.unauthenticate();

	}

}
