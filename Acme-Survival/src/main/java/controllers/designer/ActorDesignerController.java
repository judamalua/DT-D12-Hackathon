/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.designer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.DesignerService;
import controllers.AbstractController;
import domain.Designer;
import forms.ActorForm;

@Controller
@RequestMapping("/actor/designer")
public class ActorDesignerController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private DesignerService	designerService;


	// Constructors -----------------------------------------------------------

	public ActorDesignerController() {
		super();
	}

	//Edit an Designer
	/**
	 * That method edits the profile of a designer
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editDesigner() {
		ModelAndView result;
		Designer designer;
		ActorForm actorForm;

		designer = (Designer) this.actorService.findActorByPrincipal();
		Assert.notNull(designer);
		actorForm = this.actorService.deconstruct(designer);

		result = this.createEditModelAndView(actorForm);

		return result;
	}

	//Updating profile of a designer ---------------------------------------------------------------------
	/**
	 * That method update the profile of a designer.
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateDesigner(@ModelAttribute("actor") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Designer designer = null;

		try {
			designer = this.designerService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(actor, "designer.params.error");
		else
			try {
				this.designerService.save(designer);
				result = new ModelAndView("redirect:/actor/display.do?anonymous=false");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "designer.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final ActorForm designer) {
		ModelAndView result;

		result = this.createEditModelAndView(designer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm designer, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("message", messageCode);
		result.addObject("actor", designer);

		return result;

	}

}
