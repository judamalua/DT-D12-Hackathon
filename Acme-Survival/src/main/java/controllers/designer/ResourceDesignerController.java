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

import services.ResourceService;
import controllers.AbstractController;
import domain.Resource;

@Controller
@RequestMapping("/resource/designer")
public class ResourceDesignerController extends AbstractController {

	@Autowired
	private ResourceService	resourceService;


	// Constructors -----------------------------------------------------------

	public ResourceDesignerController() {
		super();
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("itemDesign") Resource itemDesign, final BindingResult binding) {
		ModelAndView result;
		try {
			itemDesign = this.resourceService.reconstruct(itemDesign, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(itemDesign, "refuge.params.error");
		else
			try {
				Assert.isTrue(!itemDesign.getFinalMode());

				this.resourceService.save(itemDesign);

				result = new ModelAndView("redirect:/itemDesign/designer/list.do?tool=" + false + "&");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(itemDesign, "itemDesign.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Resource itemDesign, final BindingResult binding) {
		ModelAndView result;

		try {
			itemDesign = this.resourceService.findOne(itemDesign.getId());

			Assert.isTrue(!itemDesign.getFinalMode());

			this.resourceService.delete(itemDesign);

			result = new ModelAndView("redirect:/itemDesign/designer/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(itemDesign, "refuge.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Resource itemDesign) {
		ModelAndView result;

		result = this.createEditModelAndView(itemDesign, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Resource itemDesign, final String message) {
		ModelAndView result;

		result = new ModelAndView("itemDesign/edit");
		result.addObject("itemDesign", itemDesign);
		result.addObject("tool", false);
		result.addObject("message", message);

		return result;

	}

}