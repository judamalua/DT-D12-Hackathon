/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.DesignerConfigurationService;
import services.InventoryService;
import services.LocationService;
import services.MoveService;
import services.RefugeService;
import controllers.AbstractController;
import domain.DesignerConfiguration;
import domain.Inventory;
import domain.Location;
import domain.Move;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/move/player")
public class MovePlayerController extends AbstractController {

	@Autowired
	private RefugeService					refugeService;

	@Autowired
	private MoveService						moveService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private LocationService					locationService;

	@Autowired
	private InventoryService				inventoryService;

	@Autowired
	private DesignerConfigurationService	designerConfigurationService;


	// Constructors -----------------------------------------------------------

	public MovePlayerController() {
		super();
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer locationId) {
		ModelAndView result;
		Player player;
		Refuge ownRefuge;
		Location location;
		Move move = null;
		DesignerConfiguration designerConfiguration;
		Inventory inventory;

		try {
			player = (Player) this.actorService.findActorByPrincipal();
			ownRefuge = this.refugeService.findRefugeByPlayer(player.getId());

			Assert.notNull(ownRefuge, "Not have refuge");

			location = this.locationService.findOne(locationId);
			designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();

			inventory = this.inventoryService.findInventoryByRefuge(ownRefuge.getId());

			move = this.moveService.create();
			move.setLocation(location);

			if (inventory.getFood() >= designerConfiguration.getMovingFood() && inventory.getMetal() >= designerConfiguration.getMovingMetal() && inventory.getWater() >= designerConfiguration.getMovingWater()
				&& inventory.getWood() >= designerConfiguration.getMovingWood())
				result = this.createEditModelAndView(move, "move.confirm");
			else
				result = this.createEditModelAndView(move, "move.resources.error");

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have refuge"))
				result = new ModelAndView("redirect:/refuge/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST, params = "confirm")
	public ModelAndView confirm(@ModelAttribute("move") Move move, final BindingResult binding) {
		ModelAndView result;
		Player player;
		Refuge ownRefuge;
		DesignerConfiguration designerConfiguration;
		Inventory inventory;

		try {
			move = this.moveService.reconstruct(move, binding);
		} catch (final Throwable oops) {
		}

		try {

			player = (Player) this.actorService.findActorByPrincipal();
			ownRefuge = this.refugeService.findRefugeByPlayer(player.getId());

			designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();

			inventory = this.inventoryService.findInventoryByRefuge(ownRefuge.getId());

			Assert.notNull(ownRefuge, "Not have refuge");
			Assert.isTrue(inventory.getFood() >= designerConfiguration.getMovingFood() && inventory.getMetal() >= designerConfiguration.getMovingMetal() && inventory.getWater() >= designerConfiguration.getMovingWater()
				&& inventory.getWood() >= designerConfiguration.getMovingWood());

			this.moveService.save(move);

			result = new ModelAndView("redirect:/refuge/player/display.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have refuge"))
				result = new ModelAndView("redirect:/refuge/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Move move) {
		ModelAndView result;

		result = this.createEditModelAndView(move, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Move move, final String message) {
		ModelAndView result;

		result = new ModelAndView("move/confirm");
		result.addObject("message", message);
		result.addObject("move", move);

		return result;

	}

}
