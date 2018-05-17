
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.GatherRepository;
import domain.Character;
import domain.Gather;
import domain.Location;
import domain.Player;
import domain.Refuge;

@Service
@Transactional
public class GatherService {

	// Managed repository --------------------------------------------------

	@Autowired
	private GatherRepository	gatherRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private RefugeService		refugeService;

	@Autowired
	private CharacterService	characterService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MoveService			moveService;

	@Autowired
	private LocationService		locationService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods --------------------------------------------------

	//Recoleccion: controlador que reciba una location y redirigir a una vista que seleccione el personaje con un select (este no debe estar en una mision de recoleccion ya).
	//Cuando llegue de la mision, mostrar lo que ha ganado. Si ha ganado más de lo que puede llevar en la capacidad, tiene que decidir qué materias tirar y cuales quedarse.

	public Gather create(final int locationId) {
		Gather result;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Refuge refuge;
		Location location;

		result = new Gather();
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

	public Collection<Gather> findAll() {

		Collection<Gather> result;

		Assert.notNull(this.gatherRepository);
		result = this.gatherRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Gather findOne(final int recolectionId) {

		Gather result;

		result = this.gatherRepository.findOne(recolectionId);

		return result;

	}

	public Gather save(final Gather gather) {
		Assert.notNull(gather);
		Assert.notNull(gather.getLocation());
		Assert.notNull(gather.getCharacter());

		Gather result;
		Collection<Gather> recolectionNotFinishedByCharacter;
		Player player;

		recolectionNotFinishedByCharacter = this.findRecolectionNotFinishedByCharacter(gather.getCharacter().getId());
		player = (Player) this.actorService.findActorByPrincipal();

		Assert.isTrue(recolectionNotFinishedByCharacter.size() == 0);
		Assert.isTrue(gather.getPlayer().equals(player));

		result = this.gatherRepository.save(gather);

		return result;

	}

	public void delete(final Gather gather) {

		assert gather != null;
		assert gather.getId() != 0;

		Assert.isTrue(this.gatherRepository.exists(gather.getId()));

		this.gatherRepository.delete(gather);

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
		result = this.gatherRepository.findCharactersInRecolectionMission(now);

		return result;

	}

	public Collection<Gather> findRecolectionNotFinishedByCharacter(final int characterId) {
		Collection<Gather> result;
		Date now;

		now = new Date();
		result = this.gatherRepository.findRecolectionNotFinishedByCharacter(characterId, now);

		return result;
	}

	public Page<Gather> findRecolectionsByPlayer(final int playerId, final Pageable pageable) {
		Page<Gather> result;

		Assert.notNull(pageable);

		result = this.gatherRepository.findRecolectionsByPlayer(playerId, pageable);

		return result;

	}
	public Gather reconstruct(final Gather gather, final BindingResult binding) {
		Gather result = null;
		Date startMoment, endMoment;
		Long time;
		Player player;
		Refuge refuge;

		if (gather.getId() == 0) {
			result = gather;

			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			startMoment = new Date(System.currentTimeMillis() - 10);
			time = this.moveService.timeBetweenLocations(refuge.getLocation(), gather.getLocation());
			endMoment = new Date(System.currentTimeMillis() + time);

			result.setStartDate(startMoment);
			result.setEndMoment(endMoment);
			result.setPlayer(player);

		}
		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Gather> findGathersFinishedByPlayer(final int playerId) {
		Collection<Gather> result;
		Date now;

		now = new Date();
		result = this.gatherRepository.findGathersFinishedByPlayer(playerId, now);

		return result;
	}
}
