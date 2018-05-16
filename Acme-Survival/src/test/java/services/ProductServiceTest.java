
package services;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProductServiceTest extends AbstractTest {

	@Autowired
	public ActorService			actorService;
	@Autowired
	public ProductService		productService;
	@Autowired
	public ConfigurationService	configurationService;


	//******************************************Positive Methods*******************************************************************
	/**
	 * This driver checks several tests regarding functional requirement number 22.3: An actor who is authenticated as a manager must be able to manage
	 * create products in the marketplace
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverCreateServices() {
		final String pictureUrl = "https://www.estilosdeaprendizaje.org/testestilosdeaprendizaje.png";

		final Object testingData[][] = {

			{
				// This test checks that authenticated managers can create a product
				"Manager1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, false, null
			}, {
				// This test checks that authenticated managers can create a product with price 0.0
				"Manager1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 0., false, null
			}, {
				// This test checks that unauthenticated users cannot create a product
				null, "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, false, IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot create a product with a blank name in English
				"Manager1", "", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, false, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot create a product with a blank name in Spanish
				"Manager1", "Test name", "", "Test description", "Descripción prueba", pictureUrl, 12.0, false, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot create a product with a blank description in English
				"Manager1", "Test name", "Nombre prueba", "", "Descripción prueba", pictureUrl, 12.0, false, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot create a product with a blank description in Spanish
				"Manager1", "Test name", "Nombre prueba", "Test description", "", pictureUrl, 12.0, false, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot create a product with a blank picture URL
				"Manager1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", "", 12.0, false, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot create a product with a negative price
				"Manager1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, -12.0, false, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot create a discontinued product
				"Manager1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot create a product
				"Player1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, false, IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot create a product
				"Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, false, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderator cannot create a product
				"Moderator1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, false, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderator cannot create a product
				"Admin1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, false, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateProducts((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6],
				(Boolean) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	// Ancillary methods ---------------------------------------------------------------------------------------

	protected void templateCreateProducts(final String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final String pictureUrl, final Double price, final Boolean discontinued,
		final Class<?> expected) {
		Class<?> caught;
		Product result;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			result = this.productService.create();

			result.setName(nameMap);
			result.setDescription(descriptionMap);
			result.setPictureUrl(pictureUrl);
			result.setPrice(price);
			result.setDiscontinued(discontinued);

			this.productService.save(result);
			this.productService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
