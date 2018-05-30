
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
import domain.Character;
import domain.DesignerConfiguration;
import domain.Inventory;
import domain.Notification;
import domain.Player;
import domain.Shelter;

@Service
@Transactional
public class AttackService {

	// Managed repository --------------------------------------------------

	@Autowired
	private AttackRepository				attackRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private MoveService						moveService;

	@Autowired
	private ShelterService					shelterService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private Validator						validator;

	@Autowired
	private NotificationService				notificationService;

	@Autowired
	private DesignerConfigurationService	designerConfigurationService;

	@Autowired
	private InventoryService				inventoryService;

	@Autowired
	private CharacterService				characterService;


	// Simple CRUD methods --------------------------------------------------

	public Attack create(final int shelterId) {
		Attack result;
		Shelter defendant, attacker;
		Date startMoment, endMoment;
		Long time;
		Player player;

		defendant = this.shelterService.findOne(shelterId);

		Assert.isTrue(this.playerKnowsSheltere(defendant));
		//Assert.isTrue(this.shelterIsAttackable(defendant.getId()), "Shelter can't be attacked");

		player = (Player) this.actorService.findActorByPrincipal();

		//Assert.isTrue(!this.playerAlreadyAttacking(player.getId()));

		attacker = this.shelterService.findShelterByPlayer(player.getId());

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
	//Un jugador que ya está en una mision de ataque no puede crear otra mision de ataque.

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
		Assert.isTrue(this.playerKnowsSheltere(attack.getDefendant()), "Player doesn't know the Shelter");
		Assert.isTrue(this.shelterIsAttackable(attack.getDefendant().getId()), "Shelter can't be attacked");

		Attack result;
		Player player;
		Shelter shelter;

		player = (Player) this.actorService.findActorByPrincipal();
		shelter = attack.getDefendant();

		Assert.isTrue(!this.playerAlreadyAttacking(player.getId()), "Player is already attacking");
		Assert.isTrue(attack.getPlayer().equals(player));
		Assert.isTrue(!this.attackerHasNoCharactersToAttack(attack.getAttacker().getId()), "Attacker doesn't have characters to attack");

		result = this.attackRepository.save(attack);
		shelter.setLastAttackReceived(result.getEndMoment());

		this.shelterService.saveToUpdateLastTimeAttacked(shelter);

		return result;

	}
	public void delete(final Attack attack) {
		Assert.notNull(attack);
		Assert.isTrue(attack.getId() != 0);
		Assert.isTrue(this.attackRepository.exists(attack.getId()));

		Notification notification, defendantNotification;
		ArrayList<Integer> resourcesStolen;
		Integer resources;

		resources = this.getResourcesOfAttack(attack);
		resourcesStolen = this.calculateResourcesToSteal(attack, resources);
		notification = this.notificationService.findNotificationByAttack(attack.getId());

		if (notification != null) {
			notification.setAttack(null);
			this.notificationService.save(notification);
		}

		defendantNotification = this.notificationService.create();

		defendantNotification.setPlayer(attack.getDefendant().getPlayer());
		defendantNotification.setBody(this.notificationService.generetateMapBodyResultByAttack(attack));
		defendantNotification.setTitle(this.notificationService.generetateTitleMapResultByAttack(attack));

		this.stealResources(attack, resourcesStolen);

		this.notificationService.saveToDefendant(defendantNotification);

		this.attackRepository.delete(attack);

	}

	public ArrayList<Integer> calculateResourcesToSteal(final Attack attack, final Integer resources) {
		ArrayList<Integer> result;
		Double waterStolen, foodStolen, metalStolen, woodStolen;
		ArrayList<Integer> resourcesStolen;
		Inventory attackerInventory, defendantInventory;

		resourcesStolen = this.getCollectionResourcesOfAttack(resources);
		attackerInventory = this.inventoryService.findInventoryByShelter(attack.getAttacker().getId());
		defendantInventory = this.inventoryService.findInventoryByShelter(attack.getDefendant().getId());

		waterStolen = 1.0 * resourcesStolen.get(0);
		foodStolen = 1.0 * resourcesStolen.get(1);
		metalStolen = 1.0 * resourcesStolen.get(2);
		woodStolen = 1.0 * resourcesStolen.get(3);

		//WATER
		if ((attackerInventory.getWater() + waterStolen <= attackerInventory.getWaterCapacity()) && (defendantInventory.getWater() - waterStolen < 0)) {
			waterStolen = defendantInventory.getWater();
		} else if ((attackerInventory.getWater() + waterStolen > attackerInventory.getWaterCapacity()) && (defendantInventory.getWater() - waterStolen >= 0)) {
			waterStolen = attackerInventory.getWaterCapacity() - attackerInventory.getWater();
		} else if ((attackerInventory.getWater() + waterStolen > attackerInventory.getWaterCapacity()) && (defendantInventory.getWater() - waterStolen < 0)) {
			waterStolen = attackerInventory.getWaterCapacity() - attackerInventory.getWater();

			if (defendantInventory.getWater() - waterStolen < 0) {
				waterStolen = defendantInventory.getWater();
			}

		}

		//FOOD
		if ((attackerInventory.getFood() + foodStolen <= attackerInventory.getFoodCapacity()) && (defendantInventory.getFood() - foodStolen < 0)) {
			foodStolen = defendantInventory.getFood();
		} else if ((attackerInventory.getFood() + foodStolen > attackerInventory.getFoodCapacity()) && (defendantInventory.getFood() - foodStolen >= 0)) {
			foodStolen = attackerInventory.getFoodCapacity() - attackerInventory.getFood();
		} else if ((attackerInventory.getFood() + foodStolen > attackerInventory.getFoodCapacity()) && (defendantInventory.getFood() - foodStolen < 0)) {
			foodStolen = attackerInventory.getFoodCapacity() - attackerInventory.getFood();

			if (defendantInventory.getFood() - foodStolen < 0) {
				foodStolen = defendantInventory.getFood();
			}

		}

		//METAL
		if ((attackerInventory.getMetal() + metalStolen <= attackerInventory.getMetalCapacity()) && (defendantInventory.getMetal() - metalStolen < 0)) {
			metalStolen = defendantInventory.getMetal();
		} else if ((attackerInventory.getMetal() + metalStolen > attackerInventory.getMetalCapacity()) && (defendantInventory.getMetal() - metalStolen >= 0)) {
			metalStolen = attackerInventory.getMetalCapacity() - attackerInventory.getMetal();
		} else if ((attackerInventory.getMetal() + metalStolen > attackerInventory.getMetalCapacity()) && (defendantInventory.getMetal() - metalStolen < 0)) {
			metalStolen = attackerInventory.getMetalCapacity() - attackerInventory.getMetal();

			if (defendantInventory.getMetal() - metalStolen < 0) {
				metalStolen = defendantInventory.getMetal();
			}

		}

		//WOOD	
		if ((attackerInventory.getWood() + woodStolen <= attackerInventory.getWoodCapacity()) && (defendantInventory.getWood() - woodStolen < 0)) {
			woodStolen = defendantInventory.getWood();
		} else if ((attackerInventory.getWood() + woodStolen > attackerInventory.getWoodCapacity()) && (defendantInventory.getWood() - woodStolen >= 0)) {
			woodStolen = attackerInventory.getWoodCapacity() - attackerInventory.getWood();
		} else if ((attackerInventory.getWood() + woodStolen > attackerInventory.getWoodCapacity()) && (defendantInventory.getWood() - woodStolen < 0)) {
			woodStolen = attackerInventory.getWoodCapacity() - attackerInventory.getWood();

			if (defendantInventory.getWood() - woodStolen >= 0) {
				woodStolen = defendantInventory.getWood();
			}

		}

		result = new ArrayList<Integer>();

		result.add(waterStolen.intValue());
		result.add(foodStolen.intValue());
		result.add(metalStolen.intValue());
		result.add(woodStolen.intValue());

		return result;

	}

	public void stealResources(final Attack attack, final ArrayList<Integer> resourcesArray) {
		Integer waterStolen;
		Integer foodStolen;
		Integer metalStolen;
		Integer woodStolen;
		Integer attackResults;
		Inventory attackerInventory, defendantInventory;
		Collection<Character> attackerCharacters, defendantCharacters;

		attackerInventory = this.inventoryService.findInventoryByShelter(attack.getAttacker().getId());
		defendantInventory = this.inventoryService.findInventoryByShelter(attack.getDefendant().getId());

		waterStolen = resourcesArray.get(0);
		foodStolen = resourcesArray.get(1);
		metalStolen = resourcesArray.get(2);
		woodStolen = resourcesArray.get(3);

		attackerInventory.setWater(attackerInventory.getWater() + waterStolen);
		attackerInventory.setFood(attackerInventory.getFood() + foodStolen);
		attackerInventory.setMetal(attackerInventory.getMetal() + metalStolen);
		attackerInventory.setWood(attackerInventory.getWood() + woodStolen);

		defendantInventory.setWater(defendantInventory.getWater() - waterStolen);
		defendantInventory.setFood(defendantInventory.getFood() - foodStolen);
		defendantInventory.setMetal(defendantInventory.getMetal() - metalStolen);
		defendantInventory.setWood(defendantInventory.getWood() - woodStolen);

		this.inventoryService.save(attackerInventory);
		this.inventoryService.save(defendantInventory);

		attackResults = this.getResourcesOfAttack(attack);

		attackerCharacters = this.findCharactersForAttackMission(attack.getAttacker().getId());
		defendantCharacters = this.findCharactersForAttackMission(attack.getDefendant().getId());
		if (attackResults > 0) {

			for (final Character c : attackerCharacters) {
				this.updateCharacterHealth(c, attackResults, true);
			}

			for (final Character c : defendantCharacters) {
				this.updateCharacterHealth(c, attackResults, false);
			}

		} else {
			for (final Character c : attackerCharacters) {
				this.updateCharacterHealth(c, attackResults, false);
			}

			for (final Character c : defendantCharacters) {
				this.updateCharacterHealth(c, attackResults, true);
			}
		}

	}

	/**
	 * This method gets a character and updates its Health depending on the results of the attack.
	 * 
	 * @param c
	 *            , the character
	 * @param attackResults
	 * @param winner
	 *            , if the character won the attack or not
	 */
	private void updateCharacterHealth(final Character c, final Integer attackResults, final boolean winner) {
		int damage, currentHealth;

		currentHealth = c.getCurrentHealth();

		if (attackResults > 0) {
			if (!winner) {
				damage = attackResults * (3 / 2);
			} else {
				damage = (int) (attackResults * 0.5); //attacker won
			}

		} else if (winner) {
			damage = c.getLevel() * 2;
		} else {
			// attacker lost
			damage = c.getLevel() * 4;
		}

		c.setCurrentHealth(currentHealth - damage);
		this.characterService.save(c);

	}
	/**
	 * This method checks that the player who is connected (the principal)
	 * knows the shelter passed as a param
	 * 
	 * @param shelter
	 * @return true if the player knows the shelter
	 * @author antrodart
	 */
	public boolean playerKnowsSheltere(final Shelter shelter) {
		Boolean result;
		Player player;

		result = false;
		player = (Player) this.actorService.findActorByPrincipal();

		if (player.getShelters().contains(shelter)) {
			result = true;
		}

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

		if (attack != null) {
			result = true;
		}

		return result;
	}
	//Business methods --------------------
	/**
	 * This methot returns the number of resources stolen in the Attack. If the attacker loses, it returns 0 or a negative number.
	 * 
	 * @param attack
	 * @return resources stolen of the Attack. If is a lost, then it returns 0.
	 */
	public Integer getResourcesOfAttack(final Attack attack) {
		Integer strengthSumAttacker, strengthSumDefendant;
		Integer result;

		strengthSumAttacker = this.getStrengthSumByShelter(attack.getAttacker().getId());
		strengthSumDefendant = this.getStrengthSumByShelter(attack.getDefendant().getId());

		Assert.isTrue(strengthSumAttacker != null && strengthSumAttacker > 0, "Attacker doesn't have characters to attack");

		if (strengthSumDefendant == null) {
			strengthSumDefendant = 1;
		}

		result = strengthSumAttacker - strengthSumDefendant;

		if (result < 0) {
			result = 0;
		}

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
		DesignerConfiguration dc;

		dc = this.designerConfigurationService.findDesignerConfiguration();

		result = new ArrayList<Integer>();
		waterStole = (int) Math.round(resources * dc.getWaterFactorSteal());
		foodStole = (int) Math.round(resources * dc.getFoodFactorSteal());
		metalStole = (int) Math.round(resources * dc.getMetalFactorSteal());
		woodStole = (int) Math.round(resources * dc.getWoodFactorSteal());

		result.add(waterStole);
		result.add(foodStole);
		result.add(metalStole);
		result.add(woodStole);

		return result;
	}
	public Attack findAttacksByAttacker(final int shelterId) {
		Assert.isTrue(shelterId != 0);

		Attack result;

		result = this.attackRepository.findAttacksByAttacker(shelterId);

		return result;
	}

	public Attack findAttackByPlayer(final int playerId) {
		Assert.isTrue(playerId != 0);

		Attack result;

		result = this.attackRepository.findAttackByPlayer(playerId);

		return result;
	}

	public Collection<Attack> findAttacksByDefendant(final int shelterId) {
		Assert.isTrue(shelterId != 0);

		Collection<Attack> result;

		result = this.attackRepository.findAttacksByDefendant(shelterId);

		return result;
	}

	public Integer getStrengthSumByShelter(final int shelterId) {
		Integer result;

		result = this.attackRepository.getStrengthSumByShelter(shelterId);

		return result;
	}

	public Attack reconstruct(final Attack attack, final BindingResult binding) {
		Attack result = null;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Shelter attacker;

		if (attack.getId() == 0) {
			result = attack;

			player = (Player) this.actorService.findActorByPrincipal();

			attacker = this.shelterService.findShelterByPlayer(player.getId());

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
	 * public Page<Attack> findAllAttacksByPlayer(final int shelterId, final Pageable pageable) {
	 * Page<Attack> result;
	 * 
	 * Assert.notNull(pageable);
	 * 
	 * result = this.attackRepository.findAllAttacksByPlayer(shelterId, pageable);
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

		if (attack.getEndMoment().before(now)) {
			result = true;
		}

		return result;
	}

	public boolean shelterIsAttackable(final int shelterId) {
		boolean result;
		Shelter shelter;
		Double shelterRecoverTime;
		Integer integerRecoverTime;
		Date attackableTime, now;
		Long miliseconds;

		shelter = this.shelterService.findOne(shelterId);

		if (shelter.getLastAttackReceived() == null) {
			result = true;
		} else {
			result = false;
			now = new Date();
			shelterRecoverTime = this.designerConfigurationService.findDesignerConfiguration().getShelterRecoverTime();
			integerRecoverTime = shelterRecoverTime.intValue();
			miliseconds = (long) (integerRecoverTime * 60000);

			attackableTime = new Date(shelter.getLastAttackReceived().getTime() + miliseconds);

			if (attackableTime.before(now)) {
				result = true;
			}
		}

		return result;
	}

	public boolean attackerHasNoCharactersToAttack(final int shelterId) {
		Boolean result;
		Integer strengthInShelter;

		strengthInShelter = this.getStrengthSumByShelter(shelterId);

		if (strengthInShelter == null || strengthInShelter <= 0) {
			result = true;
		} else {
			result = false;
		}

		return result;

	}

	public Collection<Character> findCharactersForAttackMission(final int shelterId) {
		Collection<Character> result;

		result = this.attackRepository.findCharactersForAttackMission(shelterId);

		return result;
	}

	public Collection<Collection<String>> findNumAttacksByShelter() {
		Collection<Collection<String>> result;

		result = this.attackRepository.findNumAttacksByShelter();

		return result;
	}

	public Collection<Collection<String>> findNumDefensesByShelter() {
		Collection<Collection<String>> result;

		result = this.attackRepository.findNumDefensesByShelter();

		return result;
	}

}
