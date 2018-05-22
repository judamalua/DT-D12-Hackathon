
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
	//Attack missions take place between two refuges, the attackers and the defendants, the sum of the characters 
	//strength of a refuge determines the winner of the attack. If the attacker has more strength, 
	//the difference between both strengths is multiplied by a ratio that determines how many resources 
	//the attackers takes from the defendant.

	//Service under test ---------------------------------------
	@Autowired
	private AttackService	attackService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks that the Player can Attack a Refuge that he already knows.
	 */
	@Test
	public void testAttackPositive() {
		int refugeId;
		Attack attack, saved;
		final Player player;

		super.authenticate("player1"); //The player knows the Refuge

		refugeId = super.getEntityId("Refuge2");
		attack = this.attackService.create(refugeId);
		//player = (Player) this.actorService.findActorByPrincipal();

		//attack.setPlayer(player);

		saved = this.attackService.saveToAttack(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/**
	 * This test checks that the Player can't Attack a Refuge that he doesn't know.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAttackPlayerDoesntKnowRefuge() {
		int refugeId;
		Attack attack, saved;
		Player player;

		super.authenticate("player4"); //The player doesn't know the Refuge

		refugeId = super.getEntityId("Refuge2");
		attack = this.attackService.create(refugeId);

		player = (Player) this.actorService.findActorByPrincipal();

		attack.setPlayer(player);

		saved = this.attackService.save(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/**
	 * This test checks that the Player can't Attack his own Refuge.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAttackOwnPlayerRefuge() {
		int refugeId;
		Attack attack, saved;
		Player player;

		super.authenticate("player3"); //The player doesn't know the Refuge

		refugeId = super.getEntityId("Refuge2");
		attack = this.attackService.create(refugeId);
		player = (Player) this.actorService.findActorByPrincipal();

		attack.setPlayer(player);
		saved = this.attackService.save(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/**
	 * This test checks that the Player can't Attack a Refuge when he is already in an Attack Mission.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAttackPlayerIsAlreadyAttacking() {
		int refugeId;
		Attack attack, attack2, saved, saved2;
		final Player player;

		super.authenticate("player1"); //The player knows the Refuge and attacks it.

		refugeId = super.getEntityId("Refuge2");
		attack = this.attackService.create(refugeId);
		//player = (Player) this.actorService.findActorByPrincipal();

		//attack.setPlayer(player);
		saved = this.attackService.save(attack);

		this.attackService.flush();

		Assert.notNull(saved);

		attack2 = this.attackService.create(refugeId);
		saved2 = this.attackService.save(attack2);

		Assert.notNull(saved2);

		super.unauthenticate();
	}

}
