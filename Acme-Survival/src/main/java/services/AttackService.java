
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AttackRepository;
import domain.Attack;
import domain.DesignerConfiguration;
import domain.Notification;
import domain.Player;
import domain.Refuge;

@Service
@Transactional
public class AttackService {

	// Managed repository --------------------------------------------------

	@Autowired
	private AttackRepository		attackRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private MoveService				moveService;

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private NotificationService		notificationService;

	@Autowired
	private DesignerConfiguration	designerConfiguration;


	// Simple CRUD methods --------------------------------------------------

	public Attack create(final int refugeId) {
		Attack result;
		Refuge defendant, attacker;
		Date startMoment, endMoment;
		Long time;
		Player player;

		defendant = this.refugeService.findOne(refugeId);

		Assert.isTrue(this.playerKnowsRefugee(defendant));

		player = (Player) this.actorService.findActorByPrincipal();

		Assert.isTrue(!this.playerAlreadyAttacking(player.getId()));

		attacker = this.refugeService.findRefugeByPlayer(player.getId());

		startMoment = new Date(System.currentTimeMillis() - 10);
		time = this.moveService.timeBetweenLocations(attacker.getLocation(), defendant.getLocation());
		endMoment = new Date(System.currentTimeMillis() + time);

		result = new Attack();

		result.setAttacker(attacker);
		result.setDefendant(defendant);
		result.setStartDate(startMoment);
		result.setEndMoment(endMoment);
		result.setPlayer(player);

		return result;

	}
	//Controlador: entra la id del refugio al que atacar; se comprueba que la persona tenga en su lista de refugios conocidos tal refugio, crear la mision de ataque, se pone el timer y empieza
	//Cuando termine el ataque, mostrar pantalla de resultados para el que ataca y el que ha sido atacado, poniendo lo que ha ganado y perdido cada uno
	//Un jugador que ya est� en una mision de ataque no puede crear otra mision de ataque.

	public Collection<Attack> findAll() {

		Collection<Attack> result;

		Assert.notNull(this.attackRepository);
		result = this.attackRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Attack findOne(final int attackId) {

		Attack result;

		result = this.attackRepository.findOne(attackId);

		return result;

	}

	public Attack save(final Attack attack) {

		Assert.notNull(attack);

		Attack result;

		result = this.attackRepository.save(attack);

		return result;

	}

	public Attack saveToAttack(final Attack attack) {

		Assert.notNull(attack);
		Assert.isTrue(this.playerKnowsRefugee(attack.getDefendant()), "Player doesn't know the Refuge");

		Attack result;
		Player player;

		player = (Player) this.actorService.findActorByPrincipal();

		Assert.isTrue(!this.playerAlreadyAttacking(player.getId()), "Player is already attacking");
		Assert.isTrue(attack.getPlayer().equals(player));

		result = this.attackRepository.save(attack);

		return result;

	}
	public void delete(final Attack attack) {
		Assert.notNull(attack);
		Assert.isTrue(attack.getId() != 0);
		Assert.isTrue(this.attackRepository.exists(attack.getId()));

		Notification notification;

		notification = this.notificationService.findNotificationByMission(attack.getId());

		if (notification != null) {
			notification.setMission(null);
			this.notificationService.save(notification);
		}

		this.attackRepository.delete(attack);

	}

	/**
	 * This method checks that the player who is connected (the principal)
	 * knows the refuge passed as a param
	 * 
	 * @param refuge
	 * @return true if the player knows the refuge
	 * @author antrodart
	 */
	public boolean playerKnowsRefugee(final Refuge refuge) {
		Boolean result;
		Player player;

		result = false;
		player = (Player) this.actorService.findActorByPrincipal();

		if (player.getRefuges().contains(refuge))
			result = true;

		return result;
	}

	/**
	 * Returns true if the Player logged (the principal) is already involved in an Attack Mission.
	 * 
	 * @return true if the player is already involved in an attack mission
	 */
	public boolean playerAlreadyAttacking(final int playerId) {
		Boolean result;
		Attack attack;

		result = false;

		attack = this.attackRepository.findAttackByPlayer(playerId);

		if (attack != null)
			result = true;

		return result;
	}
	//Business methods --------------------
	/**
	 * This methot returns the number of resources stolen in the Attack. If the attacker loses, it returns 0 or a negative number.
	 * 
	 * @param attack
	 * @return resources stolen of the Attack. If is a lost, then it returns 0 or a negative number.
	 */
	public Integer getResourcesOfAttack(final Attack attack) {
		Integer strengthSumAttacker, strengthSumDefendant;
		Integer result;

		strengthSumAttacker = this.getStrengthSumByRefuge(attack.getAttacker().getId());
		strengthSumDefendant = this.getStrengthSumByRefuge(attack.getDefendant().getId());

		result = strengthSumAttacker - strengthSumDefendant;

		return result;

	}

	/**
	 * This method receives the number of resources that the attacker can steal in an attack;
	 * and returns an Array<Integer> where the first Integer is the water the attacker stole,
	 * the second the food, the third the metal and the fourth the wood.
	 * 
	 * @param attack
	 * @param resources
	 * @return
	 */
	public ArrayList<Integer> getCollectionResourcesOfAttack(final Integer resources) {
		ArrayList<Integer> result;
		Integer waterStole, foodStole, metalStole, woodStole;

		result = new ArrayList<Integer>();
		waterStole = (int) Math.round(resources * this.designerConfiguration.getWaterFactorSteal());
		foodStole = (int) Math.round(resources * this.designerConfiguration.getFoodFactorSteal());
		metalStole = (int) Math.round(resources * this.designerConfiguration.getMetalFactorSteal());
		woodStole = (int) Math.round(resources * this.designerConfiguration.getWoodFactorSteal());

		result.add(waterStole);
		result.add(foodStole);
		result.add(metalStole);
		result.add(woodStole);

		return result;
	}
	public Attack findAttacksByAttacker(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Attack result;

		result = this.attackRepository.findAttacksByAttacker(refugeId);

		return result;
	}

	public Attack findAttackByPlayer(final int playerId) {
		Assert.isTrue(playerId != 0);

		Attack result;

		result = this.attackRepository.findAttackByPlayer(playerId);

		return result;
	}

	public Collection<Attack> findAttacksByDefendant(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Attack> result;

		result = this.attackRepository.findAttacksByDefendant(refugeId);

		return result;
	}

	public Integer getStrengthSumByRefuge(final int refugeId) {
		Integer result;

		result = this.attackRepository.getStrengthSumByRefuge(refugeId);

		return result;
	}

	public Attack reconstruct(final Attack attack, final BindingResult binding) {
		Attack result = null;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Refuge attacker;

		if (attack.getId() == 0) {
			result = attack;

			player = (Player) this.actorService.findActorByPrincipal();

			attacker = this.refugeService.findRefugeByPlayer(player.getId());

			startMoment = new Date(System.currentTimeMillis() - 10);
			time = this.moveService.timeBetweenLocations(attacker.getLocation(), result.getDefendant().getLocation());
			endMoment = new Date(System.currentTimeMillis() + time);

			attack.setAttacker(attacker);
			attack.setStartDate(startMoment);
			attack.setEndMoment(endMoment);
			result.setPlayer(player);
		}
		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.attackRepository.flush();
	}

	/*
	 * public Page<Attack> findAllAttacksByPlayer(final int refugeId, final Pageable pageable) {
	 * Page<Attack> result;
	 * 
	 * Assert.notNull(pageable);
	 * 
	 * result = this.attackRepository.findAllAttacksByPlayer(refugeId, pageable);
	 * 
	 * return result;
	 * }
	 */

	/**
	 * This method checks if the Attack is finished or not.
	 * 
	 * @param attack
	 * @return true if the Attack is finished
	 */
	public boolean hasFinished(final Attack attack) {
		Boolean result;
		Date now;

		result = false;
		now = new Date();

		if (attack.getEndMoment().before(now))
			result = true;

		return result;
	}
}
