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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.ActorService;
import services.RefugeService;
import controllers.AbstractController;
import domain.Inventory;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/inventory/player")
public class InventoryPlayerController extends AbstractController {

	@Autowired
	private RefugeService	refugeService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public InventoryPlayerController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody
	String update() {
		String result;
		Inventory inventory;
		Refuge refuge;
		Player player;

		result = "";
		try {
			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			inventory = this.refugeService.updateInventory(refuge);

			result += inventory.getFood() + "," + inventory.getWater() + "," + inventory.getMetal() + "," + inventory.getWood();
		} catch (final Throwable oops) {
			result = "error";
		}
		return result;
	}

}
