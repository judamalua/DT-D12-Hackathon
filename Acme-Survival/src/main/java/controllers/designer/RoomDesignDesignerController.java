
package controllers.designer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BarrackService;
import services.ConfigurationService;
import services.ResourceRoomService;
import services.RestorationRoomService;
import services.RoomDesignService;
import services.WarehouseService;
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
	BarrackService			barrackService;
	@Autowired
	WarehouseService		warehouseService;
	@Autowired
	RestorationRoomService	restorationRoomService;
	@Autowired
	ResourceRoomService		resourceRoomService;
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

	// Creating ---------------------------------------------------------

	/**
	 * This method creates a barrack
	 * 
	 * @param barrack
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/createBarrack", method = RequestMethod.GET)
	public ModelAndView createBarrack() {
		ModelAndView result;
		Barrack barrack;

		try {
			barrack = this.barrackService.create();
			result = this.createEditModelAndView(barrack);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	/**
	 * This method creates a warehouse
	 * 
	 * @param warehouse
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/createWarehouse", method = RequestMethod.GET)
	public ModelAndView createWarehouse() {
		ModelAndView result;
		Warehouse warehouse;

		try {
			warehouse = this.warehouseService.create();
			result = this.createEditModelAndView(warehouse);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	/**
	 * This method creates a restoration room
	 * 
	 * @param restorationRoom
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/createRestorationRoom", method = RequestMethod.GET)
	public ModelAndView createRestorationRoom() {
		ModelAndView result;
		RestorationRoom restorationRoom;

		try {
			restorationRoom = this.restorationRoomService.create();
			result = this.createEditModelAndView(restorationRoom);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	/**
	 * This method creates a resource room
	 * 
	 * @param resourceRoom
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/createResourceRoom", method = RequestMethod.GET)
	public ModelAndView createResourceRoom() {
		ModelAndView result;
		ResourceRoom resourceRoom;

		try {
			resourceRoom = this.resourceRoomService.create();
			result = this.createEditModelAndView(resourceRoom);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Setting final mode ---------------------------------------------------------

	@RequestMapping(value = "/final-mode", method = RequestMethod.GET)
	public ModelAndView setFinalMode(@RequestParam final int roomDesignId) {
		ModelAndView result;
		RoomDesign roomDesign;

		try {
			roomDesign = this.roomDesignService.findOne(roomDesignId);

			this.roomDesignService.setFinalModeRoomDesign(roomDesign);
			result = new ModelAndView("redirect:list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Saving -------------------------------------------------------------------

	/**
	 * This method saves a barrack
	 * 
	 * @param barrack
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveBarrack")
	public ModelAndView saveBarrack(@ModelAttribute("roomDesign") @Valid final Barrack barrack, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(barrack, "roomDesign.params.error");
		else
			try {
				this.roomDesignService.save(barrack);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(barrack, "roomDesign.commit.error");
			}

		return result;
	}

	/**
	 * This method saves a warehouse
	 * 
	 * @param warehouse
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveWarehouse")
	public ModelAndView saveWarehouse(@ModelAttribute("roomDesign") @Valid final Warehouse warehouse, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(warehouse, "roomDesign.params.error");
		else
			try {
				this.roomDesignService.save(warehouse);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(warehouse, "roomDesign.commit.error");
			}

		return result;
	}

	/**
	 * This method saves a restoration room
	 * 
	 * @param restorationRoom
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveRestorationRoom")
	public ModelAndView saveRestorationRoom(@ModelAttribute("roomDesign") @Valid final RestorationRoom restorationRoom, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(restorationRoom, "roomDesign.params.error");
		else
			try {
				this.roomDesignService.save(restorationRoom);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(restorationRoom, "roomDesign.commit.error");
			}

		return result;
	}

	/**
	 * This method saves a resource room
	 * 
	 * @param resourceRoom
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveResourceRoom")
	public ModelAndView saveResourceRoom(@ModelAttribute("roomDesign") @Valid final ResourceRoom resourceRoom, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(resourceRoom, "roomDesign.params.error");
		else
			try {
				this.roomDesignService.save(resourceRoom);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(resourceRoom, "roomDesign.commit.error");
			}

		return result;
	}

	// Deleting ------------------------------------------------------------------------

	/**
	 * This method deletes a barrack
	 * 
	 * @param barrack
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteBarrack")
	public ModelAndView deleteBarrack(@Valid final Barrack barrack, final BindingResult binding) {
		ModelAndView result;

		try {
			this.roomDesignService.delete(barrack);
			result = new ModelAndView("redirect:list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(barrack, "roomDesign.commit.error");
		}

		return result;
	}

	/**
	 * This method deletes a warehouse
	 * 
	 * @param warehouse
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteWarehouse")
	public ModelAndView deleteWarehouse(@Valid final Warehouse warehouse, final BindingResult binding) {
		ModelAndView result;

		try {
			this.roomDesignService.delete(warehouse);
			result = new ModelAndView("redirect:list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(warehouse, "roomDesign.commit.error");
		}

		return result;
	}

	/**
	 * This method deletes a restoration room
	 * 
	 * @param restorationRoom
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteRestorationRoom")
	public ModelAndView deleteRestorationRoom(@Valid final RestorationRoom restorationRoom, final BindingResult binding) {
		ModelAndView result;

		try {
			this.roomDesignService.delete(restorationRoom);
			result = new ModelAndView("redirect:list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(restorationRoom, "roomDesign.commit.error");
		}

		return result;
	}

	/**
	 * This method deletes a resource room
	 * 
	 * @param resourceRoom
	 * @param binding
	 * @return a model depending on the error/success on the operation
	 * 
	 * @author Juanmi
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteResourceRoom")
	public ModelAndView deleteResourceRoom(@Valid final RestorationRoom resourceRoom, final BindingResult binding) {
		ModelAndView result;

		try {
			this.roomDesignService.delete(resourceRoom);
			result = new ModelAndView("redirect:list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(resourceRoom, "roomDesign.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final RoomDesign roomDesign) {
		ModelAndView result;

		result = this.createEditModelAndView(roomDesign, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RoomDesign roomDesign, final String messageCode) {
		ModelAndView result;
		boolean isWarehouse = false, isRestorationRoom = false, isBarrack = false, isResourceRoom = false;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();

		result = new ModelAndView("roomDesign/edit");

		// Sending a boolean to true to check what attributes fill in the form
		if (roomDesign instanceof Warehouse)
			isWarehouse = true;
		else if (roomDesign instanceof RestorationRoom)
			isRestorationRoom = true;
		else if (roomDesign instanceof Barrack)
			isBarrack = true;
		else if (roomDesign instanceof ResourceRoom)
			isResourceRoom = true;
		result.addObject("roomDesign", roomDesign);

		result.addObject("isWarehouse", isWarehouse);
		result.addObject("isRestorationRoom", isRestorationRoom);
		result.addObject("isBarrack", isBarrack);
		result.addObject("isResourceRoom", isResourceRoom);
		result.addObject("languages", configuration.getLanguages());

		result.addObject("message", messageCode);

		return result;

	}

}
