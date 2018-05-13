
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RecolectionRepository;
import domain.Character;
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


	// Simple CRUD methods --------------------------------------------------

	public Recolection create() {
		Recolection result;

		result = new Recolection();

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

		assert recolection != null;

		Recolection result;

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
}
