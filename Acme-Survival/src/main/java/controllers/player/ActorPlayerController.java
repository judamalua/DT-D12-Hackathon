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
import services.PlayerService;
import controllers.AbstractController;
import domain.Player;
import forms.ActorForm;

@Controller
@RequestMapping("/actor/player")
public class ActorPlayerController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private PlayerService	playerService;


	// Constructors -----------------------------------------------------------

	public ActorPlayerController() {
		super();
	}

	//Edit an Player
	/**
	 * That method edits the profile of a player
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPlayer() {
		ModelAndView result;
		Player player;
		ActorForm actorForm;

		player = (Player) this.actorService.findActorByPrincipal();
		Assert.notNull(player);
		actorForm = this.actorService.deconstruct(player);

		result = this.createEditModelAndView(actorForm);

		return result;
	}

	//Updating profile of a player ---------------------------------------------------------------------
	/**
	 * That method update the profile of a player.
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updatePlayer(@ModelAttribute("actor") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Player player = null;

		try {
			player = this.playerService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(actor, "player.params.error");
		else
			try {
				this.playerService.save(player);
				result = new ModelAndView("redirect:/actor/display.do?anonymous=false");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "player.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final ActorForm player) {
		ModelAndView result;

		result = this.createEditModelAndView(player, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm player, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("message", messageCode);
		result.addObject("actor", player);

		return result;

	}

}
