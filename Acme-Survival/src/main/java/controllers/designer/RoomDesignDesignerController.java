
package controllers.designer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.RoomDesignService;
import domain.Actor;
import domain.Barrack;
import domain.Configuration;
import domain.Designer;
import domain.ResourceRoom;
import domain.RestorationRoom;
import domain.RoomDesign;
import domain.Warehouse;

@Controller
@RequestMapping("/roomDesign/designer")
public class RoomDesignDesignerController {

	// Services -------------------------------------------------------

	@Autowired
	RoomDesignService		roomDesignService;
	@Autowired
	ActorService			actorService;
	@Autowired
	ConfigurationService	configurationService;


	// Listing ----------------------------------------------------

	/**
	 * This method returns a model with the list of draft mode room designs
	 * 
	 * @param page
	 * @return a model with the draft mode room designs
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/list")
	public ModelAndView listDraftMode(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<RoomDesign> draftModeRoomDesigns;
		Actor actor;
		Pageable pageable;
		Configuration configuration;

		try {

			configuration = this.configurationService.findConfiguration();

			pageable = new PageRequest(page, configuration.getPageSize());
			result = new ModelAndView("roomDesign/list");
			actor = this.actorService.findActorByPrincipal();
			// Checking that the user trying to list draft mode products is a manager.
			Assert.isTrue(actor instanceof Designer);

			draftModeRoomDesigns = this.roomDesignService.findDraftRoomDesigns(pageable);

			result.addObject("designerDraftModeView", true);
			result.addObject("roomDesigns", draftModeRoomDesigns.getContent());
			result.addObject("page", page);
			result.addObject("requestURI", "roomDesign/designer/list.do");
			result.addObject("pageNum", draftModeRoomDesigns.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Editing ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int roomDesignId) {
		ModelAndView result;
		RoomDesign roomDesign;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			// Checking that the user trying to edit the room design is a designer.
			Assert.isTrue(actor instanceof Designer);

			roomDesign = this.roomDesignService.findOne(roomDesignId);
			Assert.notNull(roomDesign);

			// Checking that the room design trying to be edited is not a final mode room design.
			Assert.isTrue(!roomDesign.getFinalMode());

			result = this.createEditModelAndView(roomDesign);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	//
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
	//	// Discontinuing ---------------------------------------------------------
	//
	//	@RequestMapping(value = "/discontinue", method = RequestMethod.GET)
	//	public ModelAndView discontinue(@RequestParam final int productId) {
	//		ModelAndView result;
	//		Product product;
	//
	//		try {
	//			product = this.roomDesignService.findOne(productId);
	//
	//			this.roomDesignService.discontinueProduct(product);
	//			result = new ModelAndView("redirect:/product/list.do");
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
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveBarrack")
	public ModelAndView save(@Valid final Barrack barrack, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(barrack, "barrack.params.error");
		else
			try {
				this.roomDesignService.save(barrack);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(barrack, "barrack.commit.error");
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
