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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.InventoryService;
import services.RefugeService;
import controllers.AbstractController;
import domain.Inventory;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/inventory/player")
public class InventoryPlayerController extends AbstractController {

	@Autowired
	private RefugeService		refugeService;

	@Autowired
	private InventoryService	inventoryService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public InventoryPlayerController() {
		super();
	}

	/**
	 * That method returns a model and view with the inventory display
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/display")
	public ModelAndView display() {
		ModelAndView result;
		final Refuge ownRefuge;
		Player player;
		Inventory inventory;
		Double sumCapacity;

		try {

			player = (Player) this.actorService.findActorByPrincipal();
			ownRefuge = this.refugeService.findRefugeByPlayer(player.getId());

			Assert.notNull(ownRefuge, "Not have refuge");

			inventory = this.inventoryService.findInventoryByRefuge(ownRefuge.getId());

			sumCapacity = inventory.getFood() + inventory.getMetal() + inventory.getWater() + inventory.getWood();

			result = new ModelAndView("inventory/display");

			result.addObject("inventory", inventory);
			result.addObject("ratioCapacity", sumCapacity / inventory.getCapacity());

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have refuge"))
				result = new ModelAndView("redirect:/refuge/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

}
