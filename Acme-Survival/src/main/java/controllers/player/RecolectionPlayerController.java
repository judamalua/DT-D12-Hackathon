
package controllers.player;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RecolectionService;
import controllers.AbstractController;
import domain.Character;
import domain.Recolection;

@Controller
@RequestMapping("/recolection/player")
public class RecolectionPlayerController extends AbstractController {

	@Autowired
	private RecolectionService	recolectionService;


	// Constructors -----------------------------------------------------------

	public RecolectionPlayerController() {
		super();
	}

	// Create -----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int locationId) {
		ModelAndView result;
		Recolection recolection;
		try {
			recolection = this.recolectionService.create(locationId);
			result = this.createEditModelAndView(recolection);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("recolection") Recolection recolection, final BindingResult binding) {
		ModelAndView result;
		try {
			recolection = this.recolectionService.reconstruct(recolection, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(recolection, "recolection.params.error");
		else
			try {
				recolection = this.recolectionService.save(recolection);
				result = new ModelAndView("redirect:/map/player/display.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/misc/403");
			}
		return result;
	}

	// Ancilliary methods  -----------------------------------------------------------------
	private ModelAndView createEditModelAndView(final Recolection recolection) {
		ModelAndView result;

		result = this.createEditModelAndView(recolection, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Recolection recolection, final String message) {
		ModelAndView result;
		Collection<Character> elegibleCharacters;

		elegibleCharacters = this.recolectionService.findCharactersElegible();
		result = new ModelAndView("recolection/edit");

		result.addObject("recolection", recolection);
		result.addObject("message", message);
		result.addObject("characters", elegibleCharacters);

		return result;
	}

}
