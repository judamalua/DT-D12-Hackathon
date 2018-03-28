
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.ProductService;
import domain.Configuration;
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

			result.addObject("principalIsManager", false);
			result.addObject("managerDraftModeView", false);
			result.addObject("products", products.getContent());
			result.addObject("requestURI", "product/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Detailed ---------------------------------------------------------

}
