
package controllers.designer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.DesignerConfigurationService;
import controllers.AbstractController;
import domain.DesignerConfiguration;

@Controller
@RequestMapping("/designerConfiguration/designer")
public class DesignerConfigurationDesignerController extends AbstractController {

	// Services -------------------------------------------------------

	@Autowired
	private DesignerConfigurationService	designerConfigurationService;


	// Listing ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		DesignerConfiguration configuration;

		result = new ModelAndView("designerConfiguration/list");
		configuration = this.designerConfigurationService.findDesignerConfiguration();

		result.addObject("designerConfiguration", configuration);

		return result;
	}

	// Editing ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		DesignerConfiguration configuration;

		configuration = this.designerConfigurationService.findDesignerConfiguration();
		Assert.notNull(configuration);
		result = this.createEditModelAndView(configuration);

		return result;
	}

	// Saving -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final DesignerConfiguration configuration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(configuration, "configuration.params.error");
		} else {
			try {

				this.designerConfigurationService.save(configuration);
				result = new ModelAndView("redirect:/designerConfiguration/designer/list.do");

			} catch (final Throwable oops) {
				if (oops.getMessage().contains("CharacterCapacityError")) {
					result = this.createEditModelAndView(configuration, "configuration.capacity.error");
				} else if (oops.getMessage().contains("FoodWaterGatherError")) {
					result = this.createEditModelAndView(configuration, "configuration.foodLostGatherWaterGather.error");
				} else {
					result = this.createEditModelAndView(configuration, "configuration.commit.error");
				}
			}
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final DesignerConfiguration configuration) {
		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final DesignerConfiguration configuration, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("designerConfiguration/edit");
		result.addObject("designerConfiguration", configuration);

		result.addObject("message", messageCode);

		return result;

	}
}
