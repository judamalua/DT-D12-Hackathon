
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
import services.ShelterService;
import controllers.AbstractController;
import domain.Attack;
import domain.Move;
import domain.Player;
import domain.Shelter;

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
	private ShelterService	shelterService;


	// Constructors -----------------------------------------------------------

	public AttackPlayerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createAttackToShelter(final int shelterId) {
		ModelAndView result;
		Attack attack;
		Move move;
		Player player;
		Shelter shelter;

		try {

			player = (Player) this.actorService.findActorByPrincipal();
			shelter = this.shelterService.findShelterByPlayer(player.getId());
			move = this.moveService.findCurrentMoveByShelter(shelter.getId());

			attack = this.attackService.create(shelterId);
			result = new ModelAndView("attack/create");
			result.addObject("attack", attack);
			result.addObject("isAttacking", this.attackService.playerAlreadyAttacking(player.getId()));
			result.addObject("isAttackable", this.attackService.shelterIsAttackable(shelterId));
			result.addObject("attackerHasNoCharacters", this.attackService.attackerHasNoCharactersToAttack(attack.getAttacker().getId()));

			if (move == null)
				result.addObject("isMoving", true);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAttackToShelter(@ModelAttribute("attack") Attack attack, final BindingResult binding) {
		ModelAndView result;
		Move move;
		Player player;
		Shelter shelter;

		try {
			attack = this.attackService.reconstruct(attack, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = new ModelAndView("redirect:/misc/403");
		else
			try {

				player = (Player) this.actorService.findActorByPrincipal();
				shelter = this.shelterService.findShelterByPlayer(player.getId());
				move = this.moveService.findCurrentMoveByShelter(shelter.getId());
				Assert.isTrue(move == null);

				this.attackService.saveToAttack(attack);

				result = new ModelAndView("redirect:/map/player/display.do");
			} catch (final Throwable oops) {
				if (oops.getMessage() == "Shelter can't be attacked")
					result = this.createAttackToShelter(attack.getDefendant().getId());
				else if (oops.getMessage() == "Attacker doesn't have characters to attack")
					result = this.createAttackToShelter(attack.getDefendant().getId());
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
	 * Shelter shelter;
	 * Player player;
	 * 
	 * try {
	 * configuration = this.configurationService.findConfiguration();
	 * pageable = new PageRequest(page, configuration.getPageSize());
	 * 
	 * result = new ModelAndView("attack/list");
	 * 
	 * player = (Player) this.actorService.findActorByPrincipal();
	 * shelter = this.shelterService.findShelterByPlayer(player.getId());
	 * 
	 * attacks = this.attackService.findAllAttacksByPlayer(shelter.getId(), pageable);
	 * 
	 * result.addObject("attacks", attacks.getContent());
	 * result.addObject("page", page);
	 * result.addObject("pageNum", attacks.getTotalPages());
	 * result.addObject("requestUri", "attack/player/list.do?");
	 * result.addObject("myShelterId", shelter.getId());
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
	 * attackerStrength = this.attackService.getStrengthSumByShelter(attack.getAttacker().getId());
	 * defendantStrength = this.attackService.getStrengthSumByShelter(attack.getDefendant().getId());
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
