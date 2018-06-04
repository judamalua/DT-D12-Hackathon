
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Attack;
import domain.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AttackServiceTest extends AbstractTest {

	//All of the Tests in this class check functional requirement 14: Players can start missions, 
	//for each mission the system must store a start moment and an end moment, the end moment is calculated 
	//by a ratio that determines the time by a distance. There are two types of mission, attack missions and recollect missions. 
	//Attack missions take place between two shelters, the attackers and the defendants, the sum of the characters 
	//strength of a shelter determines the winner of the attack. If the attacker has more strength, 
	//the difference between both strengths is multiplied by a ratio that determines how many resources 
	//the attackers takes from the defendant.

	//Service under test ---------------------------------------
	@Autowired
	private AttackService	attackService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks that the Player can Attack a Shelter that he already knows.
	 */
	@Test
	public void testAttackPositive() {
		int shelterId;
		Attack attack, saved;

		super.authenticate("player1"); //The player knows the Shelter

		shelterId = super.getEntityId("Shelter2");
		attack = this.attackService.create(shelterId);

		saved = this.attackService.saveToAttack(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		this.attackService.delete(saved);

		super.unauthenticate();
	}

	/**
	 * This test checks that the Player can't Attack a Shelter that he doesn't know.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAttackPlayerDoesntKnowShelter() {
		int shelterId;
		Attack attack, saved;
		Player player;

		super.authenticate("player4"); //The player doesn't know the Shelter

		shelterId = super.getEntityId("Shelter2");
		attack = this.attackService.create(shelterId);

		player = (Player) this.actorService.findActorByPrincipal();

		attack.setPlayer(player);

		saved = this.attackService.saveToAttack(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/**
	 * This test checks that the Player can't Attack his own Shelter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAttackOwnPlayerShelter() {
		int shelterId;
		Attack attack, saved;
		Player player;

		super.authenticate("player3"); //The player doesn't know the Shelter

		shelterId = super.getEntityId("Shelter2");
		attack = this.attackService.create(shelterId);
		player = (Player) this.actorService.findActorByPrincipal();

		attack.setPlayer(player);
		saved = this.attackService.saveToAttack(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/**
	 * This test checks that the Player can't Attack a Shelter when he is already in an Attack Mission.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAttackPlayerIsAlreadyAttacking() {
		int shelterId;
		Attack attack, attack2, saved, saved2;

		super.authenticate("player1"); //The player knows the Shelter and attacks it.

		shelterId = super.getEntityId("Shelter2");
		attack = this.attackService.create(shelterId);
		saved = this.attackService.saveToAttack(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		attack2 = this.attackService.create(shelterId);
		saved2 = this.attackService.saveToAttack(attack2);

		Assert.notNull(saved2);

		super.unauthenticate();
	}

	/**
	 * This test checks that the Player can not Attack a Shelter that has been Attacked recently.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAttackShelterNotAttackable() {
		int shelterId;
		Attack attack, saved;

		super.authenticate("player1"); //The player knows the Shelter

		shelterId = super.getEntityId("Shelter2");
		attack = this.attackService.create(shelterId);

		saved = this.attackService.saveToAttack(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		this.attackService.delete(saved);

		attack = this.attackService.create(shelterId);

		saved = this.attackService.saveToAttack(attack);

		super.unauthenticate();
	}

}
