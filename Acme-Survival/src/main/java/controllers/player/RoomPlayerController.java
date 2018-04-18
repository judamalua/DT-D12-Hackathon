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
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RoomService;
import controllers.AbstractController;
import domain.Actor;
import domain.Player;
import domain.Room;

@Controller
@RequestMapping("/room/player")
public class RoomPlayerController extends AbstractController {

	@Autowired
	private RoomService		roomService;

	@Autowired
	private ActorService	actorService;


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

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("room") Room room, final BindingResult binding) {
		ModelAndView result;
		Room sendedRefuge = null;
		try {
			sendedRefuge = room;
			room = this.roomService.reconstruct(room, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(sendedRefuge, "room.params.error");
		else
			try {

				this.roomService.save(room);
				result = new ModelAndView("redirect:/refuge/display.do");

			} catch (final Throwable oops) {
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

		result = new ModelAndView("room/edit");
		result.addObject("room", room);
		result.addObject("message", message);

		return result;

	}

}
