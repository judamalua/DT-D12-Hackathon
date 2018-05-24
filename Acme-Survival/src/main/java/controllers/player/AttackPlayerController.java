
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
import services.AttackService;
import services.MoveService;
import services.RefugeService;
import controllers.AbstractController;
import domain.Attack;
import domain.Move;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/attack/player")
public class AttackPlayerController extends AbstractController {

	@Autowired
	private AttackService	attackService;

	//	@Autowired
	//	private ConfigurationService	configurationService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MoveService		moveService;

	@Autowired
	private RefugeService	refugeService;


	// Constructors -----------------------------------------------------------

	public AttackPlayerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createAttackToRefuge(final int refugeId) {
		ModelAndView result;
		Attack attack;
		Move move;
		Player player;
		Refuge refuge;

		try {

			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			move = this.moveService.findCurrentMoveByRefuge(refuge.getId());

			attack = this.attackService.create(refugeId);
			result = new ModelAndView("attack/create");
			result.addObject("attack", attack);
			result.addObject("isAttacking", this.attackService.playerAlreadyAttacking(player.getId()));
			result.addObject("isAttackable", this.attackService.refugeIsAttackable(refugeId));
			result.addObject("attackerHasNoCharacters", this.attackService.attackerHasNoCharactersToAttack(attack.getAttacker().getId()));

			if (move == null)
				result.addObject("isMoving", true);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAttackToRefuge(@ModelAttribute("attack") Attack attack, final BindingResult binding) {
		ModelAndView result;
		Move move;
		Player player;
		Refuge refuge;

		try {
			attack = this.attackService.reconstruct(attack, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = new ModelAndView("redirect:/misc/403");
		else
			try {

				player = (Player) this.actorService.findActorByPrincipal();
				refuge = this.refugeService.findRefugeByPlayer(player.getId());
				move = this.moveService.findCurrentMoveByRefuge(refuge.getId());
				Assert.isTrue(move == null);

				this.attackService.saveToAttack(attack);

				result = new ModelAndView("redirect:/map/player/display.do");
			} catch (final Throwable oops) {
				if (oops.getMessage() == "Refuge can't be attacked")
					result = this.createAttackToRefuge(attack.getDefendant().getId());
				else if (oops.getMessage() == "Attacker doesn't have characters to attack")
					result = this.createAttackToRefuge(attack.getDefendant().getId());
				else
					result = new ModelAndView("redirect:/misc/403");

			}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int attackId) {
		ModelAndView result;
		Attack attack;

		try {
			attack = this.attackService.findOne(attackId);
			this.attackService.delete(attack);

			result = new ModelAndView("redirect:/map/player/display.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	/*
	 * @RequestMapping(value = "/list", method = RequestMethod.GET)
	 * public ModelAndView list(@RequestParam(defaultValue = "0") final int page) {
	 * ModelAndView result;
	 * Page<Attack> attacks;
	 * Configuration configuration;
	 * Pageable pageable;
	 * Refuge refuge;
	 * Player player;
	 * 
	 * try {
	 * configuration = this.configurationService.findConfiguration();
	 * pageable = new PageRequest(page, configuration.getPageSize());
	 * 
	 * result = new ModelAndView("attack/list");
	 * 
	 * player = (Player) this.actorService.findActorByPrincipal();
	 * refuge = this.refugeService.findRefugeByPlayer(player.getId());
	 * 
	 * attacks = this.attackService.findAllAttacksByPlayer(refuge.getId(), pageable);
	 * 
	 * result.addObject("attacks", attacks.getContent());
	 * result.addObject("page", page);
	 * result.addObject("pageNum", attacks.getTotalPages());
	 * result.addObject("requestUri", "attack/player/list.do?");
	 * result.addObject("myRefugeId", refuge.getId());
	 * } catch (final Throwable oops) {
	 * result = new ModelAndView("redirect:/misc/403");
	 * }
	 * 
	 * return result;
	 * }
	 */

	/*
	 * @RequestMapping(value = "/results", method = RequestMethod.GET)
	 * public ModelAndView results(final int attackId) {
	 * ModelAndView result;
	 * Attack attack;
	 * Boolean attackerIsWinner;
	 * Integer attackerStrength, defendantStrength;
	 * 
	 * try {
	 * attack = this.attackService.findOne(attackId);
	 * 
	 * Assert.isTrue(this.attackService.hasFinished(attack));
	 * 
	 * attackerStrength = this.attackService.getStrengthSumByRefuge(attack.getAttacker().getId());
	 * defendantStrength = this.attackService.getStrengthSumByRefuge(attack.getDefendant().getId());
	 * 
	 * if (attackerStrength > defendantStrength)
	 * attackerIsWinner = true;
	 * else
	 * attackerIsWinner = false;
	 * 
	 * result = new ModelAndView("attack/results");
	 * 
	 * result.addObject("attack", attack);
	 * result.addObject("attackerIsWinner", attackerIsWinner);
	 * result.addObject("attackerStrength", attackerStrength);
	 * result.addObject("defendantStrength", defendantStrength);
	 * 
	 * } catch (final Throwable oops) {
	 * result = new ModelAndView("redirect:/misc/403");
	 * }
	 * 
	 * return result;
	 * }
	 */
}
