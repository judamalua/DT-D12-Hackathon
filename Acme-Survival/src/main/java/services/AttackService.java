
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AttackRepository;
import domain.Attack;
import domain.Player;
import domain.Refuge;

@Service
@Transactional
public class AttackService {

	// Managed repository --------------------------------------------------

	@Autowired
	private AttackRepository	attackRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private MoveService			moveService;

	@Autowired
	private RefugeService		refugeService;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	public Attack create() {
		Attack result;

		result = new Attack();

		return result;
	}

	public Attack create(final int refugeId) {
		Attack result;
		Refuge defendant, attacker;
		Date startMoment, endMoment;
		Long time;
		Player player;

		defendant = this.refugeService.findOne(refugeId);

		Assert.isTrue(this.playerKnowsRefugee(defendant));

		player = (Player) this.actorService.findActorByPrincipal();

		attacker = this.refugeService.findRefugeByPlayer(player.getId());

		startMoment = new Date(System.currentTimeMillis() - 10);
		time = this.moveService.timeBetweenLocations(attacker.getLocation(), defendant.getLocation());
		endMoment = new Date(System.currentTimeMillis() + time);

		result = new Attack();

		result.setAttacker(attacker);
		result.setDefendant(defendant);
		result.setStartDate(startMoment);
		result.setEndMoment(endMoment);

		return result;

	}
	//Controlador: entra la id del refugio al que atacar; se comprueba que la persona tenga en su lista de refugios conocidos tal refugio, crear la mision de ataque, se pone el timer y empieza
	//Cuando termine el ataque, mostrar pantalla de resultados para el que ataca y el que ha sido atacado, poniendo lo que ha ganado y perdido cada uno
	//Un jugador que ya está en una mision de ataque no puede crear otra mision de ataque.

	//Recoleccion: controlador que reciba una location y redirigir a una vista que seleccione el personaje con un select (este no debe estar en una mision de recoleccion ya).
	//Cuando llegue de la mision, mostrar lo que ha ganado. Si ha ganado más de lo que puede llevar en la capacidad, tiene que decidir qué materias tirar y cuales quedarse.

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
		Date startMoment, endMoment;
		Long time;

		startMoment = new Date(System.currentTimeMillis() - 10);
		time = this.moveService.timeBetweenLocations(attack.getAttacker().getLocation(), attack.getDefendant().getLocation());
		endMoment = new Date(System.currentTimeMillis() + time);

		if (attack.getId() == 0) {
			attack.setStartDate(startMoment);
			attack.setEndMoment(endMoment);
		}

		result = this.attackRepository.save(attack);

		return result;

	}
	public void delete(final Attack attack) {

		assert attack != null;
		assert attack.getId() != 0;

		Assert.isTrue(this.attackRepository.exists(attack.getId()));

		this.attackRepository.delete(attack);

	}

	public boolean playerKnowsRefugee(final Refuge refuge) {
		Boolean result;
		Player player;

		result = false;
		player = (Player) this.actorService.findActorByPrincipal();

		if (player.getRefuges().contains(refuge))
			result = true;

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

		strengthSumAttacker = this.getStrengthSumByRefuge(attack.getAttacker().getId());
		strengthSumDefendant = this.getStrengthSumByRefuge(attack.getDefendant().getId());

		result = strengthSumAttacker - strengthSumDefendant;

		return result;

	}
	public Collection<Attack> findAttacksByAttacker(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Attack> result;

		result = this.attackRepository.findAttacksByAttacker(refugeId);

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
}
