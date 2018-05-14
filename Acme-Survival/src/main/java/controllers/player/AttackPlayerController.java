
package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AttackService;
import controllers.AbstractController;
import domain.Attack;

@Controller
@RequestMapping("/attack/player")
public class AttackPlayerController extends AbstractController {

	@Autowired
	private AttackService	attackService;


	// Constructors -----------------------------------------------------------

	public AttackPlayerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createAttackToRefuge(final int refugeId) {
		ModelAndView result;
		Attack attack;
		try {
			attack = this.attackService.create(refugeId);
			result = new ModelAndView("attack/create");
			result.addObject("attack", attack);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAttackToRefuge(@ModelAttribute("attack") Attack attack, final BindingResult binding) {
		ModelAndView result;

		try {
			attack = this.attackService.reconstruct(attack, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = new ModelAndView("redirect:/misc/403");
		else
			try {
				this.attackService.save(attack);
				result = new ModelAndView("reditect:/map/player/display.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/misc/403");
			}
		return result;
	}

}
