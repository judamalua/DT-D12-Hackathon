/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.PlayerService;
import domain.Actor;
import domain.Player;
import forms.ActorForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services -------------------------------------------------------
	@Autowired
	ActorService	actorService;
	@Autowired
	PlayerService	playerService;


	//	@Autowired
	//	UserService		userService;

	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// Displaying  ---------------------------------------------------------------		

	/**
	 * That method returns the profile of the actor logged
	 * That method returns a model and view with personal info of an Actor
	 * 
	 * @param page
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/display")
	public ModelAndView display() {
		ModelAndView result;
		Actor actor;

		try {
			result = new ModelAndView("actor/display");
			actor = this.actorService.findActorByPrincipal();

			result.addObject("actor", actor);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Registering player ------------------------------------------------------------
	/**
	 * That method registers an user in the system and saves it.
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/registerAsPlayer", method = RequestMethod.GET)
	public ModelAndView registerExplorer() {
		ModelAndView result;
		ActorForm player;

		player = new ActorForm();

		result = this.createEditModelAndViewRegister(player);

		result.addObject("actionURL", "actor/registerAsUser.do");

		return result;
	}

	//Saving player ---------------------------------------------------------------------
	/**
	 * That method saves a player in the system
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/registerAsUser", method = RequestMethod.POST, params = "save")
	public ModelAndView registerUser(@ModelAttribute("actor") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Authority auth;
		Player player = null;

		try {
			player = this.playerService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		if (binding.hasErrors())
			result = this.createEditModelAndViewRegister(actor, "player.params.error");
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.PLAYER);
				Assert.isTrue(player.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(actor.getConfirmPassword().equals(player.getUserAccount().getPassword()), "Passwords do not match");
				this.actorService.registerActor(player);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndViewRegister(actor, "player.username.error");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndViewRegister(actor, "player.password.error");
				else
					result = this.createEditModelAndViewRegister(actor, "player.commit.error");
			}

		return result;
	}

	//Ancillary methods ----------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);

		result.addObject("message", messageCode);

		return result;
	}
	protected ModelAndView createEditModelAndViewRegister(final ActorForm actor) {
		ModelAndView result;

		result = this.createEditModelAndViewRegister(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewRegister(final ActorForm actor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("user/register");
		result.addObject("message", messageCode);
		result.addObject("actor", actor);

		return result;

	}
}
