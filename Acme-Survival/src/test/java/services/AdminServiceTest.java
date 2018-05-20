
package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Admin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	// The SUT ---------------------------------------------------------------
	@Autowired
	private AdminService	adminService;


	// Tests ------------------------------------------------------------------

	/**
	 * This test checks that authenticated admins can create other admins
	 * 
	 * @author Alejandro
	 */
	@Test
	public void driverCreateAdmin() {

		final Object testingData[][] = {
			{
				"Admin1", "AdminUser", "TestName", "TestSurname Test", new Date(System.currentTimeMillis() - 1), "testEmail@gmail.com", "655555555", "https://realbetisbalompie.es", null
			}, {
				"Admin1", "AdminUser", "", "TestSurname Test", new Date(System.currentTimeMillis() - 1), "testEmail@gmail.com", "655555555", "https://realbetisbalompie.es", DataIntegrityViolationException.class
			}, {
				null, "AdminUser", "TestName", "TestSurname Test", new Date(System.currentTimeMillis() - 1), "testEmail@gmail.com", "655555555", "https://realbetisbalompie.es", DataIntegrityViolationException.class
			}, {
				"User1", "AdminUser", "TestName", "TestSurname Test", new Date(System.currentTimeMillis() - 1), "testEmail@gmail.com", "655555555", "https://realbetisbalompie.es", DataIntegrityViolationException.class
			}, {
				"Admin1", "", "TestName", "TestSurname Test", new Date(System.currentTimeMillis() - 1), "testEmail@gmail.com", "655555555", "https://realbetisbalompie.es", DataIntegrityViolationException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAdmin((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void templateCreateAdmin(final String login, final String userAdmin, final String name, final String surname, final Date birthDate, final String email, final String phoneNumber, final String avatar, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			Admin newAdmin;

			super.authenticate(login);

			newAdmin = this.adminService.create();
			//EN PROGRESO, Falta crear la useraccount
			final UserAccount usAcc = new UserAccount();
			usAcc.setUsername(userAdmin);
			usAcc.setPassword("admin");
			final Authority auth = new Authority();
			auth.setAuthority("ADMIN");
			usAcc.addAuthority(auth);

			newAdmin.setUserAccount(usAcc);

			newAdmin.setName(name);
			newAdmin.setSurname(surname);
			newAdmin.setBirthDate(birthDate);
			newAdmin.setEmail(email);
			newAdmin.setPhoneNumber(phoneNumber);
			newAdmin.setAvatar(avatar);

			this.adminService.save(newAdmin);
			this.adminService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
