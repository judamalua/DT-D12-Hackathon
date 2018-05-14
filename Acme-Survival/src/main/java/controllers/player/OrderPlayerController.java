
package controllers.player;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.ProductService;
import domain.CreditCard;
import domain.Product;

@Controller
@RequestMapping("/order/player")
public class OrderPlayerController {

	// Services -------------------------------------------------------

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ProductService		productService;


	//AJAX Credit Card---------------------------------------
	/**
	 * This method receives a cookie token and sends a string with the last four numbers of a credit card and the credit card number, if something fails returns a null string.
	 * 
	 * @param cookieToken
	 *            The token to test
	 * @author Daniel Diment
	 * @return
	 *         The string
	 */
	@RequestMapping(value = "/ajaxCard", method = RequestMethod.GET)
	public @ResponseBody
	String ajaxCard(@RequestParam final String cookieToken) {
		String result = "null";
		CreditCard creditCard;
		try {
			creditCard = this.creditCardService.findByCookieToken(cookieToken);
			result = creditCard.getNumber().substring(creditCard.getNumber().length() - 4) + creditCard.getId();
		} catch (final Throwable e) {
		}
		return result;
	}
	// Order -----------------------------------------------------------

	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public ModelAndView subscibe(@RequestParam final Integer productId) {
		ModelAndView result;
		final Product product;
		final CreditCard creditCard;

		try {
			product = this.productService.findOne(productId);
			Assert.isTrue(product.getDiscontinued() == false); //Tests that the product is not discontinued
			Assert.isTrue(product.getFinalMode()); //Tests that the product is final
			creditCard = this.creditCardService.create();
			result = this.createEditModelAndView(creditCard, product);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/buy", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid @ModelAttribute("creditCard") final CreditCard creditCard, final BindingResult binding, final int productId) {
		ModelAndView result = null;
		Product product;

		product = this.productService.findOne(productId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(creditCard, product, "request.params.error");
		else
			try {
				this.creditCardService.buy(creditCard, product);
				result = new ModelAndView("redirect:/product/detailed.do?productId=" + productId);
			} catch (final Throwable oops) {

				if (oops.getMessage().contains("CreditCard expiration Date error"))
					result = this.createEditModelAndView(creditCard, product, "request.creditcard.expiration.error");
				else
					result = this.createEditModelAndView(creditCard, product, "request.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final Product product) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, product, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final Product product, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("product", product);
		result.addObject("message", messageCode);

		return result;

	}
}
