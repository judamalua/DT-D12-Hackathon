
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	// The SUT ---------------------------------------------------------------
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ProductService		productService;


	// Tests ------------------------------------------------------------------
	/**
	 * This driver checks several tests regarding functional requirement number 18.7: An actor who is authenticated as a player must be able to
	 * buy products in the marketplace.
	 * 
	 * @author Daniel
	 */
	@Test
	public void driver() {

		final Object testingData[][] = {//Username, HolderName, BrandName, Number, CVV, ExpirationMonth, ExpirationYear, ExpectedException
			{
				//Positive test, an user can save a Valid CreditCard.
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 20, null
			}, {
				//Checks that the CVV can be the lowest int in the range (100).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 100, 12, 20, null
			}, {
				//Checks that the CVV can be the lowest int in the range + 1 (100).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 101, 12, 20, null
			}, {
				//Checks that the CVV can be the middle int in the range  (100 - 999).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 549, 12, 20, null
			}, {
				//Checks that the CVV can be the middle int in the range  (100 - 999).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 550, 12, 20, null
			}, {
				//Checks that the CVV can be the greatest int in the range - 1 (999).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 998, 12, 20, null
			}, {
				//Checks that the CVV can be the greatest int in the range (999).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 999, 12, 20, null
			}, {
				//Checks that you must be logged as an User to save a CreditCard.
				null, "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 20, IllegalArgumentException.class
			}, {
				//Checks that the Expiration Month can be the lowest value in range (1).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 1, 20, null
			}, {
				//Checks that the Expiration Month can be the lowest value in range +1 (1).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 2, 20, null
			}, {
				//Checks that the Expiration Month can be the middle value in range  (6).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 6, 20, null
			}, {
				//Checks that the Expiration Month can be the greatest value in range -1  (12).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 11, 20, null
			}, {
				//Checks that the Expiration Month can be the greatest value in range   (12).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 20, null
			}, {
				//Checks that the Expiration Year can be the lowest value, acording to the actual year (18).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 18, null
			}, {
				//Checks that the Expiration Year can be the lowest value +1(00).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 19, null
			}, {
				//Checks that the Expiration Year can be the middle value in range (50).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 50, null
			}, {
				//Checks that the Expiration Year can be the greatest value -1 (99).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 98, null
			}, {
				//Checks that the Expiration Year can be the greatest value  (99).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 99, null
			}, {
				//Checks that you must be logged as an User to save a CreditCard.
				"manager1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 20, java.lang.ClassCastException.class
			}, {
				//Checks that you must be logged as an User to save a CreditCard.
				"admin", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 20, java.lang.ClassCastException.class
			}, {
				//Checks that the Holder name must not be blank.
				"player1", "Product2", "", "Valid Brand Name", "4485677312398507", 123, 12, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the Brand name must not be blank.
				"player1", "Product2", "Valid Holder Name", "", "4485677312398507", 123, 12, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the Number must not be blank.
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "", 123, 12, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the Number must be a valid Credit Card number.
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "1234567891234567", 123, 12, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the CVV is not outside the minimum range (100).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 99, 12, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the CVV is not outside the maximum range (999).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 1000, 12, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the Expiration Month is not outside the minimum range (1).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 0, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the Expiration Month is not outside the maximum range (12).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 13, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the Expiration Year is not outside the minimum range (0).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, -1, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the Expiration Year is not outside the maximum range (99).
				"player1", "Product2", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 123, 12, 100, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the product musn't be discontinued
				"player1", "Product1", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 100, 12, 20, javax.validation.ConstraintViolationException.class
			}, {
				//Checks that the product must be in final mode
				"player1", "Product3", "Valid Holder Name", "Valid Brand Name", "4485677312398507", 100, 12, 20, javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6], (Integer) testingData[i][7],
				(Class<?>) testingData[i][8]);
		}

	}

	protected void template(final String username, final String newspaperPopulate, final String holderName, final String brandName, final String number, final int cvv, final int expirationMonth, final int expirationYear, final Class<?> expected) {
		CreditCard creditCard;
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);

			creditCard = this.creditCardService.create();

			final int productId = super.getEntityId(newspaperPopulate);

			final Product product = this.productService.findOne(productId);

			creditCard.setHolderName(holderName);
			creditCard.setBrandName(brandName);
			creditCard.setNumber(number);
			creditCard.setCvv(cvv);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);

			this.creditCardService.buy(creditCard, product);

			this.creditCardService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
