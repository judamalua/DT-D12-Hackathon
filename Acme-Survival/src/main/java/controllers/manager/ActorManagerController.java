/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;
import forms.ActorForm;

@Controller
@RequestMapping("/actor/manager")
public class ActorManagerController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public ActorManagerController() {
		super();
	}

	//Edit an Manager
	/**
	 * That method edits the profile of a manager
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editManager() {
		ModelAndView result;
		Manager manager;
		ActorForm actorForm;

		manager = (Manager) this.actorService.findActorByPrincipal();
		Assert.notNull(manager);
		actorForm = this.actorService.deconstruct(manager);

		result = this.createEditModelAndView(actorForm);

		return result;
	}

	//Updating profile of a manager ---------------------------------------------------------------------
	/**
	 * That method update the profile of a manager.
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateManager(@ModelAttribute("actor") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Manager manager = null;

		try {
			manager = this.managerService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(actor, "manager.params.error");
		else
			try {
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/actor/display.do?anonymous=false");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "manager.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final ActorForm manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm manager, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("message", messageCode);
		result.addObject("actor", manager);

		return result;

	}

}
