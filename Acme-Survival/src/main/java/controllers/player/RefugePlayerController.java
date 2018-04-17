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
import services.ConfigurationService;
import services.PlayerService;
import services.RefugeService;
import services.RoomService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Player;
import domain.Refuge;
import domain.Room;

@Controller
@RequestMapping("/refuge/player")
public class RefugePlayerController extends AbstractController {

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private RoomService				roomService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public RefugePlayerController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the system refuge list
	 * 
	 * @param page
	 * @param anonymous
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Refuge> refuges;
		Pageable pageable;
		Configuration configuration;
		Player actor;

		try {
			result = new ModelAndView("refuge/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			actor = (Player) this.actorService.findActorByPrincipal();

			refuges = this.playerService.findKnowRefugesByPlayer(actor.getId(), pageable);

			result.addObject("refuges", refuges.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", refuges.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	/**
	 * That method returns a model and view with the refuge display
	 * 
	 * @param page
	 * @param anonymous
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam(required = false) final Integer refugeId, @RequestParam(required = false, defaultValue = "0") final int pageRoom) {
		ModelAndView result;
		final Refuge refuge, ownRefuge;
		Player player;
		Configuration configuration;
		Pageable pageable;
		Page<Room> rooms;

		try {

			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(pageRoom, configuration.getPageSize());

			result = new ModelAndView("refuge/display");
			player = (Player) this.actorService.findActorByPrincipal();
			ownRefuge = this.refugeService.findRefugeByPlayer(player.getId());

			if (refugeId == null)
				refuge = ownRefuge;
			else
				refuge = this.refugeService.findOne(refugeId);

			rooms = this.roomService.findRoomsByRefuge(refugeId, pageable);

			Assert.notNull(refuge);
			Assert.isTrue(refuge.equals(ownRefuge) || player.getRefuges().contains(refuge));

			result.addObject("refuge", refuge);
			result.addObject("rooms", rooms.getContent());
			result.addObject("pageRoom", pageRoom);
			result.addObject("pageNumRoom", rooms.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Refuge ownRefuge;
		Player actor;

		try {
			actor = (Player) this.actorService.findActorByPrincipal();
			ownRefuge = this.refugeService.findRefugeByPlayer(actor.getId());

			result = this.createEditModelAndView(ownRefuge);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Refuge refuge;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Player);

			refuge = this.refugeService.create();

			result = this.createEditModelAndView(refuge);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:misc/403");
		}
		return result;
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("refuge") Refuge refuge, final BindingResult binding) {
		ModelAndView result;
		Refuge sendedRefuge = null;
		try {
			sendedRefuge = refuge;
			refuge = this.refugeService.reconstruct(refuge, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(sendedRefuge, "refuge.params.error");
		else
			try {

				this.refugeService.save(refuge);
				result = new ModelAndView("redirect:/refuge/display.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(refuge, "refuge.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Refuge refuge, final BindingResult binding) {
		ModelAndView result;

		try {
			refuge = this.refugeService.reconstruct(refuge, binding);
		} catch (final Throwable oops) {
		}
		try {
			this.refugeService.delete(refuge);

			result = new ModelAndView("redirect:/refuge/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(refuge, "refuge.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Refuge refuge) {
		ModelAndView result;

		result = this.createEditModelAndView(refuge, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Refuge refuge, final String message) {
		ModelAndView result;

		result = new ModelAndView("refuge/edit");
		result.addObject("refuge", refuge);
		result.addObject("message", message);

		return result;

	}

}
