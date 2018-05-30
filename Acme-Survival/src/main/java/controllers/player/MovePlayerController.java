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

import java.util.Collection;

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
import services.AttackService;
import services.CharacterService;
import services.DesignerConfigurationService;
import services.InventoryService;
import services.LocationService;
import services.MoveService;
import services.ShelterService;
import controllers.AbstractController;
import domain.DesignerConfiguration;
import domain.Inventory;
import domain.Location;
import domain.Move;
import domain.Player;
import domain.Shelter;

@Controller
@RequestMapping("/move/player")
public class MovePlayerController extends AbstractController {

	@Autowired
	private ShelterService					shelterService;

	@Autowired
	private MoveService						moveService;

	@Autowired
	private AttackService					attackService;

	@Autowired
	private CharacterService				characterService;

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
		Shelter ownShelter;
		Location location;
		Move move = null, currentMove;
		DesignerConfiguration designerConfiguration;
		Inventory inventory;
		boolean isAttacking;
		boolean isInMission = false;
		Collection<domain.Character> characters;

		try {
			player = (Player) this.actorService.findActorByPrincipal();
			ownShelter = this.shelterService.findShelterByPlayer(player.getId());

			Assert.notNull(ownShelter, "Not have shelter");

			location = this.locationService.findOne(locationId);
			designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();

			inventory = this.inventoryService.findInventoryByShelter(ownShelter.getId());

			move = this.moveService.create();
			move.setLocation(location);
			currentMove = this.moveService.findCurrentMoveByShelter(ownShelter.getId());
			isAttacking = this.attackService.playerAlreadyAttacking(player.getId());

			characters = this.characterService.findCharactersByShelter(ownShelter.getId());

			for (final domain.Character character : characters) {
				isInMission = character.getCurrentlyInGatheringMission();
				if (isInMission)
					break;
			}

			if (inventory.getFood() >= designerConfiguration.getMovingFood() && inventory.getMetal() >= designerConfiguration.getMovingMetal() && inventory.getWater() >= designerConfiguration.getMovingWater()
				&& inventory.getWood() >= designerConfiguration.getMovingWood() && currentMove == null && !isAttacking && !isInMission)
				result = this.createEditModelAndView(move, "move.confirm");
			else {
				if (currentMove != null)
					result = this.createEditModelAndView(move, "move.moving.error");
				else if (isAttacking)
					result = this.createEditModelAndView(move, "move.attacking.error");
				else if (isInMission)
					result = this.createEditModelAndView(move, "move.mission.error");
				else
					result = this.createEditModelAndView(move, "move.resources.error");

				result.addObject("error", true);
			}

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have shelter"))
				result = new ModelAndView("redirect:/shelter/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	@RequestMapping(value = "/confirm", method = RequestMethod.POST, params = "confirm")
	public ModelAndView confirm(@ModelAttribute("move") Move move, final BindingResult binding) {
		ModelAndView result;
		Player player;
		Shelter ownShelter;
		DesignerConfiguration designerConfiguration;
		Inventory inventory;

		try {
			move = this.moveService.reconstruct(move, binding);
		} catch (final Throwable oops) {
		}

		try {
			player = (Player) this.actorService.findActorByPrincipal();
			ownShelter = this.shelterService.findShelterByPlayer(player.getId());

			designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();

			inventory = this.inventoryService.findInventoryByShelter(ownShelter.getId());

			Assert.notNull(ownShelter, "Not have shelter");
			Assert.isTrue(inventory.getFood() >= designerConfiguration.getMovingFood() && inventory.getMetal() >= designerConfiguration.getMovingMetal() && inventory.getWater() >= designerConfiguration.getMovingWater()
				&& inventory.getWood() >= designerConfiguration.getMovingWood());
			Assert.isTrue(!this.attackService.playerAlreadyAttacking(player.getId()));

			this.moveService.save(move);

			result = new ModelAndView("redirect:/shelter/player/display.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have shelter"))
				result = new ModelAndView("redirect:/shelter/player/create.do");
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
