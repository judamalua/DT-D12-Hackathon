
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;
import domain.Order;
import domain.Player;
import domain.Product;

@Service
@Transactional
public class CreditCardService {

	// Managed repository --------------------------------------------------

	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private OrderService			orderService;


	// Simple CRUD methods --------------------------------------------------

	public CreditCard create() {
		CreditCard result;

		result = new CreditCard();
		result.setCookieToken(this.generateCookieToken());

		result.setPlayer((Player) this.actorService.findActorByPrincipal());
		return result;
	}

	public Collection<CreditCard> findAll() {

		Collection<CreditCard> result;

		Assert.notNull(this.creditCardRepository);
		result = this.creditCardRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public CreditCard findOne(final int creditCardId) {

		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);

		return result;

	}

	public CreditCard save(final CreditCard creditCard) {

		assert creditCard != null;

		CreditCard result;
		this.checkCreditCardExpired(creditCard);

		creditCard.setPlayer((Player) this.actorService.findActorByPrincipal());
		result = this.creditCardRepository.save(creditCard);

		return result;

	}

	public void delete(final CreditCard creditCard) {

		assert creditCard != null;
		assert creditCard.getId() != 0;

		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));

		this.creditCardRepository.delete(creditCard);

	}

	/**
	 * Generates an unique and random cookie token for every credit card
	 * 
	 * @author Daniel Diment
	 * @return
	 *         The random token
	 */
	private String generateCookieToken() {
		String alphabet, result;
		Random random;
		StringBuilder stringBuilder;
		Collection<String> allCookieTokens;

		random = new Random();
		stringBuilder = new StringBuilder();
		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgzijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < 14; i++)
			stringBuilder.append(alphabet.charAt(random.nextInt(alphabet.length())));

		result = stringBuilder.toString();
		allCookieTokens = this.creditCardRepository.getAllCookieTokens();

		if (allCookieTokens.contains(result))
			result = this.generateCookieToken();

		return result;
	}
	/**
	 * Finds a credit card by its token. If the customer is not the owner of the credit card the search will fail.
	 * 
	 * @param cookieToken
	 *            The token to find by
	 * @author Daniel Diment
	 * @return
	 *         The credit card
	 */
	public CreditCard findByCookieToken(final String cookieToken) {
		CreditCard result;

		result = this.creditCardRepository.findByCookieToken(cookieToken);

		//Checks that the CreditCard hasn't expired
		this.checkCreditCardExpired(result);

		Assert.isTrue(result.getPlayer().getId() == this.actorService.findActorByPrincipal().getId());

		return result;
	}

	/**
	 * This method checks that the Credit Card of the Request hasn't expired, checking its expiration
	 * year and expiration month.
	 * 
	 * @param creditCard
	 * @author Antonio
	 */
	public void checkCreditCardExpired(final CreditCard creditCard) {
		Integer actualMonth, actualYear, ccMonth, ccYear;
		DateFormat dfYear, dfMonth;
		String formattedYear, formattedMonth;

		ccMonth = creditCard.getExpirationMonth();
		ccYear = creditCard.getExpirationYear();

		dfYear = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		formattedYear = dfYear.format(Calendar.getInstance().getTime());
		actualYear = Integer.valueOf(formattedYear);

		dfMonth = new SimpleDateFormat("MM"); //Just the month
		formattedMonth = dfMonth.format(Calendar.getInstance().getTime());
		actualMonth = Integer.valueOf(formattedMonth);

		//Asserts that the CreditCard expiration Year is greater than the actual year
		Assert.isTrue(ccYear >= actualYear, "CreditCard expiration Date error");

		//If the CreditCard expiration Year is the same that the actual Year, 
		//Asserts that the CreditCard expiration Month is greater than the actual Month.
		if (ccYear == actualYear)
			Assert.isTrue(ccMonth > actualMonth, "CreditCard expiration Date error");

	}

	public void buy(final CreditCard creditCard, final Product product) {
		Assert.isTrue(product.getDiscontinued() == false); //Tests that the product is not discontinued
		Assert.isTrue(product.getFinalMode()); //Tests that the product is final
		Assert.isTrue(creditCard.getPlayer().getId() == this.actorService.findActorByPrincipal().getId());//Tests that the credit card is from the user
		CreditCard savedCard;
		Order order;

		if (creditCard.getId() == 0)
			savedCard = this.save(creditCard);
		else
			savedCard = creditCard;
		order = this.orderService.create();
		order.setMoment(new Date());
		order.setCreditCard(savedCard);
		order.setPlayer((Player) this.actorService.findActorByPrincipal());
		order.setProduct(product);
		this.orderService.save(order);
	}
}
