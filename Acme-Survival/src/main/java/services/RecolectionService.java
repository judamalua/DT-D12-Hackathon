
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RecolectionRepository;
import domain.Character;
import domain.Location;
import domain.Player;
import domain.Recolection;
import domain.Refuge;

@Service
@Transactional
public class RecolectionService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RecolectionRepository	recolectionRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private CharacterService		characterService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MoveService				moveService;

	@Autowired
	private LocationService			locationService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods --------------------------------------------------

	//Recoleccion: controlador que reciba una location y redirigir a una vista que seleccione el personaje con un select (este no debe estar en una mision de recoleccion ya).
	//Cuando llegue de la mision, mostrar lo que ha ganado. Si ha ganado más de lo que puede llevar en la capacidad, tiene que decidir qué materias tirar y cuales quedarse.

	public Recolection create(final int locationId) {
		Recolection result;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Refuge refuge;
		Location location;

		result = new Recolection();
		location = this.locationService.findOne(locationId);
		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());
		startMoment = new Date(System.currentTimeMillis() - 10);
		time = this.moveService.timeBetweenLocations(refuge.getLocation(), location);
		endMoment = new Date(System.currentTimeMillis() + time);

		result.setStartDate(startMoment);
		result.setEndMoment(endMoment);
		result.setLocation(location);
		result.setPlayer(player);

		return result;
	}

	public Collection<Recolection> findAll() {

		Collection<Recolection> result;

		Assert.notNull(this.recolectionRepository);
		result = this.recolectionRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Recolection findOne(final int recolectionId) {

		Recolection result;

		result = this.recolectionRepository.findOne(recolectionId);

		return result;

	}

	public Recolection save(final Recolection recolection) {
		Assert.notNull(recolection);
		Assert.notNull(recolection.getLocation());
		Assert.notNull(recolection.getCharacter());

		Recolection result;
		Collection<Recolection> recolectionNotFinishedByCharacter;
		Player player;

		recolectionNotFinishedByCharacter = this.findRecolectionNotFinishedByCharacter(recolection.getCharacter().getId());
		player = (Player) this.actorService.findActorByPrincipal();

		Assert.isTrue(recolectionNotFinishedByCharacter.size() == 0);
		Assert.isTrue(recolection.getPlayer().equals(player));

		result = this.recolectionRepository.save(recolection);

		return result;

	}

	public void delete(final Recolection recolection) {

		assert recolection != null;
		assert recolection.getId() != 0;

		Assert.isTrue(this.recolectionRepository.exists(recolection.getId()));

		this.recolectionRepository.delete(recolection);

	}

	/**
	 * This method returns all characters that are elegible for a Recolection Mission for the Player logged (the principal).
	 * 
	 * @return Collection<Character>
	 * @author antrodart
	 */
	public Collection<Character> findCharactersElegible() {
		Collection<Character> result, charactersInMission;
		Player player;
		Refuge refuge;

		player = (Player) this.actorService.findActorByPrincipal();
		refuge = this.refugeService.findRefugeByPlayer(player.getId());
		result = this.characterService.findCharactersByRefuge(refuge.getId());
		charactersInMission = this.findCharacterInRecolectionMission();

		result.removeAll(charactersInMission);

		return result;
	}

	/**
	 * This method returns all the characters that are currently involved in a Recolection Mission.
	 * 
	 * @return Collection<Character>
	 * @author antrodart
	 */
	public Collection<Character> findCharacterInRecolectionMission() {
		Collection<Character> result;
		Date now;

		now = new Date();
		result = this.recolectionRepository.findCharactersInRecolectionMission(now);

		return result;

	}

	public Collection<Recolection> findRecolectionNotFinishedByCharacter(final int characterId) {
		Collection<Recolection> result;
		Date now;

		now = new Date();
		result = this.recolectionRepository.findRecolectionNotFinishedByCharacter(characterId, now);

		return result;
	}

	public Recolection reconstruct(final Recolection recolection, final BindingResult binding) {
		Recolection result = null;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Refuge refuge;

		if (recolection.getId() == 0) {
			result = recolection;

			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			startMoment = new Date(System.currentTimeMillis() - 10);
			time = this.moveService.timeBetweenLocations(refuge.getLocation(), recolection.getLocation());
			endMoment = new Date(System.currentTimeMillis() + time);

			result.setStartDate(startMoment);
			result.setEndMoment(endMoment);
			result.setPlayer(player);

		}
		this.validator.validate(result, binding);

		return result;
	}
}
