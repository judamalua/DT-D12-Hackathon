/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ModeratorService;
import controllers.AbstractController;
import domain.Moderator;
import forms.ActorForm;

@Controller
@RequestMapping("/actor/moderator")
public class ActorModeratorController extends AbstractController {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private ModeratorService	moderatorService;


	// Constructors -----------------------------------------------------------

	public ActorModeratorController() {
		super();
	}

	//Edit an Moderator
	/**
	 * That method edits the profile of a moderator
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editModerator() {
		ModelAndView result;
		Moderator moderator;
		ActorForm actorForm;

		moderator = (Moderator) this.actorService.findActorByPrincipal();
		Assert.notNull(moderator);
		actorForm = this.actorService.deconstruct(moderator);

		result = this.createEditModelAndView(actorForm);

		return result;
	}

	//Updating profile of a moderator ---------------------------------------------------------------------
	/**
	 * That method update the profile of a moderator.
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateModerator(@ModelAttribute("actor") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Moderator moderator = null;

		try {
			moderator = this.moderatorService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(actor, "moderator.params.error");
		else
			try {
				this.moderatorService.save(moderator);
				result = new ModelAndView("redirect:/actor/display.do?anonymous=false");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "moderator.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final ActorForm moderator) {
		ModelAndView result;

		result = this.createEditModelAndView(moderator, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm moderator, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("message", messageCode);
		result.addObject("actor", moderator);

		return result;

	}

}
