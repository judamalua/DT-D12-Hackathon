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

import services.ConfigurationService;
import services.ToolService;
import controllers.AbstractController;
import domain.Configuration;
import domain.ItemDesign;
import domain.Tool;

@Controller
@RequestMapping("/tool/designer")
public class ToolDesignerController extends AbstractController {

	@Autowired
	private ToolService				toolService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ToolDesignerController() {
		super();
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("itemDesign") Tool itemDesign, final BindingResult binding) {
		ModelAndView result;

		try {
			itemDesign = this.toolService.reconstruct(itemDesign, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(itemDesign, "refuge.params.error");
		else
			try {

				this.configurationService.checkSystemLanguages(itemDesign.getName());
				this.toolService.save(itemDesign);

				result = new ModelAndView("redirect:/itemDesign/designer/list.do?tool=" + true + "&finalMode=" + itemDesign.getFinalMode());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(itemDesign, "itemDesign.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Tool itemDesign, final BindingResult binding) {
		ModelAndView result;

		try {
			itemDesign = this.toolService.findOne(itemDesign.getId());

			Assert.isTrue(!itemDesign.getFinalMode());

			this.toolService.delete(itemDesign);

			result = new ModelAndView("redirect:/itemDesign/designer/list.do?tool=" + false + "&finalMode=" + itemDesign.getFinalMode());

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(itemDesign, "itemDesign.commit.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final ItemDesign itemDesign) {
		ModelAndView result;

		result = this.createEditModelAndView(itemDesign, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ItemDesign itemDesign, final String message) {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();

		result = new ModelAndView("itemDesign/edit");
		result.addObject("itemDesign", itemDesign);
		result.addObject("tool", true);
		result.addObject("message", message);
		result.addObject("languages", configuration.getLanguages());

		return result;

	}

}
