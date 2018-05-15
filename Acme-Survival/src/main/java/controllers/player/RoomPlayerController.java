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
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.InventoryService;
import services.RefugeService;
import services.RoomDesignService;
import services.RoomService;
import controllers.AbstractController;
import domain.Actor;
import domain.Inventory;
import domain.Player;
import domain.Refuge;
import domain.Room;
import domain.RoomDesign;

@Controller
@RequestMapping("/room/player")
public class RoomPlayerController extends AbstractController {

	@Autowired
	private RoomService			roomService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RoomDesignService	roomDesignService;

	@Autowired
	private RefugeService		refugeService;

	@Autowired
	private InventoryService	inventoryService;


	// Constructors -----------------------------------------------------------

	public RoomPlayerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Room room;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Player);

			room = this.roomService.create();

			result = this.createEditModelAndView(room);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer roomId) {
		ModelAndView result;
		final Room room;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Player);

			room = this.roomService.findOne(roomId);

			this.roomService.delete(room);

			result = new ModelAndView("redirect:/refuge/player/display.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	@ResponseBody
	public void getResources(@RequestParam final String roomDesignId, final ModelMap modelMap) {
		RoomDesign roomDesign;
		final Refuge refuge;
		final Inventory inventory;
		final Actor actor;

		actor = this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(actor.getId());
		inventory = this.inventoryService.findInventoryByRefuge(refuge.getId());

		if (roomDesignId != null && roomDesignId != "" && !roomDesignId.equals("0")) {

			roomDesign = this.roomDesignService.findOne(Integer.parseInt(roomDesignId));
			if (roomDesign.getCostMetal() <= inventory.getMetal() && roomDesign.getCostWood() <= inventory.getWood()) {
				modelMap.addAttribute("wood", roomDesign.getCostWood());
				modelMap.addAttribute("metal", roomDesign.getCostMetal());
			} else
				modelMap.addAttribute("error", "room.resources.error");
		}

	}
	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("room") Room room, final BindingResult binding) {
		ModelAndView result;
		Room sendedRoom = null;
		try {
			sendedRoom = room;
			room = this.roomService.reconstruct(room, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(sendedRoom, "room.params.error");
		else
			try {
				this.roomService.save(room);
				result = new ModelAndView("redirect:/refuge/player/display.do");

			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Not enough resources"))
					result = this.createEditModelAndView(room, "room.resources.error");
				else
					result = this.createEditModelAndView(room, "room.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Room room) {
		ModelAndView result;

		result = this.createEditModelAndView(room, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Room room, final String message) {
		ModelAndView result;
		final Collection<RoomDesign> roomDesigns;

		result = new ModelAndView("room/edit");
		roomDesigns = this.roomDesignService.findFinalRoomDesign();

		result.addObject("room", room);
		result.addObject("roomDesigns", roomDesigns);
		result.addObject("message", message);

		return result;

	}
}
