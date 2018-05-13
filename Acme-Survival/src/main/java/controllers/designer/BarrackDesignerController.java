
package controllers.designer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BarrackService;
import services.ConfigurationService;
import domain.Barrack;
import domain.ResourceRoom;
import domain.RestorationRoom;
import domain.RoomDesign;
import domain.Warehouse;

@Controller
@RequestMapping("/barrack/designer")
public class BarrackDesignerController {

	// Services -------------------------------------------------------

	@Autowired
	BarrackService			barrackService;
	@Autowired
	ActorService			actorService;
	@Autowired
	ConfigurationService	configurationService;


	//	// Creating ---------------------------------------------------------
	//
	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create() {
	//		ModelAndView result;
	//		Product product;
	//		Actor actor;
	//
	//		try {
	//			actor = this.actorService.findActorByPrincipal();
	//			// Checking that the user trying to create a product is a manager.
	//			Assert.isTrue(actor instanceof Manager);
	//
	//			product = this.roomDesignService.create();
	//			result = this.createEditModelAndView(product);
	//
	//		} catch (final Throwable oops) {
	//			result = new ModelAndView("redirect:/misc/403");
	//		}
	//
	//		return result;
	//	}
	//

	//	// Setting final mode ---------------------------------------------------------
	//
	//	@RequestMapping(value = "/final-mode", method = RequestMethod.GET)
	//	public ModelAndView setFinalMode(@RequestParam final int productId) {
	//		ModelAndView result;
	//		Product product;
	//
	//		try {
	//			product = this.roomDesignService.findOne(productId);
	//
	//			this.roomDesignService.setFinalModeProduct(product);
	//			result = new ModelAndView("redirect:list.do");
	//
	//		} catch (final Throwable oops) {
	//			result = new ModelAndView("redirect:/misc/403");
	//		}
	//
	//		return result;
	//	}
	//
	//	// Saving -------------------------------------------------------------------
	//
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Barrack barrack, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(barrack, "roomDesign.params.error");
		else
			try {
				this.barrackService.save(barrack);
				result = new ModelAndView("redirect:/roomDesign/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(barrack, "roomDesign.commit.error");
			}

		return result;
	}
	//
	//	// Deleting ------------------------------------------------------------------------
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(@Valid final Product product, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		try {
	//			this.roomDesignService.delete(product);
	//			result = new ModelAndView("redirect:list.do");
	//
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(product, "product.commit.error");
	//		}
	//
	//		return result;
	//	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final RoomDesign roomDesign) {
		ModelAndView result;

		result = this.createEditModelAndView(roomDesign, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RoomDesign roomDesign, final String messageCode) {
		ModelAndView result;
		boolean isWarehouse = false, isRestorationRoom = false, isBarrack = false, isResourceRoom = false;

		result = new ModelAndView("roomDesign/edit");

		// Sending a boolean to true to check what attributes fill in the form
		if (roomDesign instanceof Warehouse) {
			isWarehouse = true;
			result.addObject("actionUrl", "warehouse/designer/edit.do");
		} else if (roomDesign instanceof RestorationRoom) {
			isRestorationRoom = true;
			result.addObject("actionUrl", "restorationRoom/designer/edit.do");
		} else if (roomDesign instanceof Barrack) {
			isBarrack = true;
			result.addObject("actionUrl", "barrack/designer/edit.do");
		} else if (roomDesign instanceof ResourceRoom) {
			isResourceRoom = true;
			result.addObject("actionUrl", "resourceRoom/designer/edit.do");
		}
		result.addObject("roomDesign", roomDesign);

		result.addObject("isWarehouse", isWarehouse);
		result.addObject("isRestorationRoom", isRestorationRoom);
		result.addObject("isBarrack", isBarrack);
		result.addObject("isResourceRoom", isResourceRoom);

		result.addObject("message", messageCode);

		return result;

	}

}
