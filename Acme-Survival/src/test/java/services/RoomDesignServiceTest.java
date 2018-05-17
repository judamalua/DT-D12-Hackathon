
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RoomDesignServiceTest extends AbstractTest {

	@Autowired
	public ActorService			actorService;
	@Autowired
	public ProductService		productService;
	@Autowired
	public ConfigurationService	configurationService;


	//******************************************Positive Methods*******************************************************************
	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * create products in the marketplace, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverCreateProducts() {
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

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: A user who is authenticated as any role must
	 * be able to list the products of the marketplace
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverListFinalModeProducts() {
		final Object testingData[][] = {
			{
				// This test checks that unauthenticated users can list products
				null, null
			}, {
				// This test checks that authenticated players can list products
				"Player1", null
			}, {
				// This test checks that authenticated managers can list products
				"Manager1", null
			}, {
				// This test checks that authenticated moderators can list products
				"Moderator1", null
			}, {
				// This test checks that authenticated designers can list products
				"Designer1", null
			}, {
				// This test checks that authenticated admins can list products
				"Admin1", null
			}, {
				// This test checks that a user that does not exist cannot list products
				"Player500", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListFinalModeProducts((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An authenticated manager must be able to list draft mode products
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverListDraftModeProducts() {
		final Object testingData[][] = {
			{
				// This test checks that authenticated managers can list draft mode products
				"Manager1", null
			}, {
				// This test checks that unauthenticated users cannot list draft mode products
				null, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot list draft mode products
				"Player1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot list draft mode products
				"Designer1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated users moderators list draft mode products
				"Moderator1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot list draft mode products
				"Admin1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListDraftModeProducts((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * edit draft mode products, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverEditProducts() {
		final String pictureUrl = "https://www.estilosdeaprendizaje.org/testestilosdeaprendizaje.png";

		final Object testingData[][] = {

			{
				// This test checks that authenticated managers can edit a draft mode product
				"Manager1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, null
			}, {
				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank English name
				"Manager1", "Product3", "", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank Spanish name
				"Manager1", "Product3", "Test name", "", "Test description", "Descripción prueba", pictureUrl, 12.0, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank English description
				"Manager1", "Product3", "Test name", "Nombre prueba", "", "Descripción prueba", pictureUrl, 12.0, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank Spanish description
				"Manager1", "Product3", "Test name", "Nombre prueba", "Test description", "", pictureUrl, 12.0, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank picture URL
				"Manager1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", "", 12.0, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated managers cannot edit a draft mode product inserting a negative price
				"Manager1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, -12.0, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that unauthenticated users cannot edit a draft mode product
				null, "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot edit a draft mode product
				"Player1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot edit a draft mode product
				"Designer1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot edit a draft mode product
				"Moderator1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot edit a draft mode product
				"Admin1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditProducts((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Double) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * delete draft mode products, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverDeleteProduct() {
		final Object testingData[][] = {
			{
				// This test checks that managers can delete a draft mode product
				"Manager1", "Product3", null
			}, {
				// This test checks that managers cannot delete a final mode product
				"Manager1", "Product1", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot delete a draft mode product
				null, "Product3", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot delete a draft mode product
				"Player1", "Product3", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot delete a draft mode product
				"Designer1", "Product3", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot delete a draft mode product
				"Moderator1", "Product3", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot delete a draft mode product
				"Admin1", "Product3", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot delete a final mode product
				null, "Product1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot delete a final mode product
				"Player1", "Product1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot delete a final mode product
				"Designer1", "Product1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot delete a final mode product
				"Moderator1", "Product1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot delete a final mode product
				"Admin1", "Product1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteProducts((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * set final mode products as discontinued, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverDiscontinueProduct() {
		final Object testingData[][] = {
			{
				// This test checks that managers can set final mode products as discontinued
				"Manager1", "Product1", null
			}, {
				// This test checks that managers cannot set draft mode products as discontinued
				"Manager1", "Product3", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot set final mode products as discontinued
				null, "Product1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot set final mode products as discontinued
				"Player1", "Product1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot set final mode products as discontinued
				"Moderator1", "Product1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot set final mode products as discontinued
				"Designer1", "Product1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot set final mode products as discontinued
				"Admin1", "Product1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDiscontinueProducts((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * set draft mode products as final mode, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverSetFinalModeProduct() {
		final Object testingData[][] = {
			{
				// This test checks that managers can set draft mode products as final mode
				"Manager1", "Product3", null
			}, {
				// This test checks that managers cannot set final mode products as final mode
				"Manager1", "Product1", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot set draft mode products as final mode
				null, "Product3", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot set draft mode products as final mode
				"Player1", "Product3", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot set draft mode products as final mode
				"Moderator1", "Product3", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot set draft mode products as final mode
				"Designer1", "Product3", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot set draft mode products as final mode
				"Admin1", "Product3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSetFinalModeProducts((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
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

	protected void templateListFinalModeProducts(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<Product> products;
		Page<Product> pageProducts;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageProducts = this.productService.getFinalModeProducts(pageable);
			products = pageProducts.getContent();

			Assert.notNull(products);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateListDraftModeProducts(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<Product> products;
		Page<Product> pageProducts;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageProducts = this.productService.getDraftModeProducts(pageable);
			products = pageProducts.getContent();

			Assert.notNull(products);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateEditProducts(final String username, final String productPopulateName, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final String pictureUrl, final Double price,
		final Class<?> expected) {
		Class<?> caught;
		int productId;
		Product product;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			productId = super.getEntityId(productPopulateName);

			product = this.productService.findOne(productId);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			product.setName(nameMap);
			product.setDescription(descriptionMap);
			product.setPictureUrl(pictureUrl);
			product.setPrice(price);

			this.productService.save(product);
			this.productService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateDeleteProducts(final String username, final String productPopulateName, final Class<?> expected) {
		Class<?> caught;
		int productId;
		Product product;

		caught = null;

		try {
			super.authenticate(username);

			productId = super.getEntityId(productPopulateName);

			product = this.productService.findOne(productId);

			this.productService.delete(product);
			this.productService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateDiscontinueProducts(final String username, final String productPopulateName, final Class<?> expected) {
		Class<?> caught;
		int productId;
		Product product;

		caught = null;

		try {
			super.authenticate(username);

			productId = super.getEntityId(productPopulateName);

			product = this.productService.findOne(productId);

			this.productService.discontinueProduct(product);
			this.productService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateSetFinalModeProducts(final String username, final String productPopulateName, final Class<?> expected) {
		Class<?> caught;
		int productId;
		Product product;

		caught = null;

		try {
			super.authenticate(username);

			productId = super.getEntityId(productPopulateName);

			product = this.productService.findOne(productId);

			this.productService.setFinalModeProduct(product);
			this.productService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
