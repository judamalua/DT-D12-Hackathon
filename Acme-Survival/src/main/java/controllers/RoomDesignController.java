
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.RoomDesignService;
import domain.Designer;
import domain.RoomDesign;

@Controller
@RequestMapping("/roomDesign")
public class RoomDesignController {

	// Services -------------------------------------------------------

	@Autowired
	RoomDesignService		roomDesignService;
	@Autowired
	ActorService			actorService;
	@Autowired
	ConfigurationService	configurationService;


	// Listing ----------------------------------------------------

	//	@RequestMapping(value = "/list")
	//	public ModelAndView list(@RequestParam(defaultValue = "0") final int page) {
	//		ModelAndView result;
	//		Page<Product> products;
	//		Pageable pageable;
	//		Configuration configuration;
	//
	//		try {
	//
	//			configuration = this.configurationService.findConfiguration();
	//
	//			pageable = new PageRequest(page, configuration.getPageSize());
	//			result = new ModelAndView("product/list");
	//
	//			products = this.roomDesignService.getFinalModeProducts(pageable);
	//
	//			result.addObject("designerDraftModeView", false);
	//			result.addObject("products", products.getContent());
	//			result.addObject("requestURI", "product/list.do");
	//			result.addObject("page", page);
	//			result.addObject("pageNum", products.getTotalPages());
	//
	//		} catch (final Throwable oops) {
	//			result = new ModelAndView("redirect:/misc/403");
	//		}
	//
	//		return result;
	//	}

	// Detailed ---------------------------------------------------------

	/**
	 * This method returns the model of the detailed view of a room design
	 * 
	 * @param roomDesignId
	 * @return the model of the detailed view of a room design
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/detailed")
	public ModelAndView detailed(@RequestParam final int roomDesignId) {
		ModelAndView result;
		RoomDesign roomDesign;

		try {

			result = new ModelAndView("roomDesign/detailed");

			roomDesign = this.roomDesignService.findOne(roomDesignId);

			// Checking that if the product is not in final mode, only designers can access to the detailed view
			if (!roomDesign.getFinalMode())
				Assert.isTrue(this.actorService.findActorByPrincipal() instanceof Designer);

			result.addObject("roomDesign", roomDesign);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
}
