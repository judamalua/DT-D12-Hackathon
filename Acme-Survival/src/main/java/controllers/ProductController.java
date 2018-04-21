
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.ProductService;
import domain.Configuration;
import domain.Manager;
import domain.Product;

@Controller
@RequestMapping("/product")
public class ProductController {

	// Services -------------------------------------------------------

	@Autowired
	ProductService			productService;
	@Autowired
	ActorService			actorService;
	@Autowired
	ConfigurationService	configurationService;


	// Listing ----------------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Product> products;
		Pageable pageable;
		Configuration configuration;

		try {

			configuration = this.configurationService.findConfiguration();

			pageable = new PageRequest(page, configuration.getPageSize());
			result = new ModelAndView("product/list");

			products = this.productService.getFinalModeProducts(pageable);

			result.addObject("managerDraftModeView", false);
			result.addObject("products", products.getContent());
			result.addObject("requestURI", "product/list.do");
			result.addObject("page", page);
			result.addObject("pageNum", products.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Detailed ---------------------------------------------------------

	@RequestMapping(value = "/detailed")
	public ModelAndView detailed(@RequestParam final int productId) {
		ModelAndView result;
		Product product;

		try {

			result = new ModelAndView("product/detailed");

			product = this.productService.findOne(productId);

			// Checking that if the product is not in final mode, only managers can access to the detailed view
			if (!product.getFinalMode())
				Assert.isTrue(this.actorService.findActorByPrincipal() instanceof Manager);

			result.addObject("product", product);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
}
