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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RoomDesignService;
import services.RoomService;
import controllers.AbstractController;
import domain.Actor;
import domain.Player;
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

	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public @ResponseBody
	String getResources(@RequestParam final String roomDesignId) {
		String result;
		RoomDesign roomDesign;

		roomDesign = this.roomDesignService.findOne(Integer.parseInt(roomDesignId));
		result = "<li> metal: " + roomDesign.getCostMetal() + "</li><br/>" + "<li> wood: " + roomDesign.getCostWood() + "</li><br/>";
		return result;
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

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Room room, final BindingResult binding) {
		ModelAndView result;

		try {
			room = this.roomService.reconstruct(room, binding);
		} catch (final Throwable oops) {
		}
		try {
			this.roomService.delete(room);

			result = new ModelAndView("redirect:/refuge/display.do");

		} catch (final Throwable oops) {
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
