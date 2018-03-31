
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RefugeRepository;
import domain.Actor;
import domain.Attack;
import domain.Item;
import domain.Move;
import domain.Player;
import domain.Refuge;

@Service
@Transactional
public class RefugeService {

	// Managed repository --------------------------------------------------

	@Autowired
	private RefugeRepository	refugeRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PlayerService		playerService;

	@Autowired
	private AttackService		attackService;

	@Autowired
	private CharacterService	characterService;

	@Autowired
	private ItemService			itemService;

	@Autowired
	private MoveService			moveService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods --------------------------------------------------

	public Refuge create() {
		Refuge result;

		result = new Refuge();

		return result;
	}

	public Collection<Refuge> findAll() {

		Collection<Refuge> result;

		Assert.notNull(this.refugeRepository);
		result = this.refugeRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Refuge findOne(final int refugeId) {

		Refuge result;
		Assert.isTrue(refugeId != 0);

		result = this.refugeRepository.findOne(refugeId);

		return result;

	}

	public Refuge save(final Refuge refuge) {

		Assert.isTrue(refuge != null);

		Refuge result;

		result = this.refugeRepository.save(refuge);

		if (refuge.getId() != 0) {
			//Hablar tema de water, food, metal y wood en refugee.
		}

		return result;

	}

	public void delete(final Refuge refuge) {

		Assert.isTrue(refuge != null);
		Assert.isTrue(refuge.getId() != 0);
		Assert.isTrue(this.refugeRepository.exists(refuge.getId()));

		Collection<Player> playersKnowsRefuge;
		Collection<Attack> attacks;
		Collection<Item> items;
		Collection<Move> moves;
		Collection<domain.Character> characters;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor.equals(refuge.getPlayer()));

		playersKnowsRefuge = this.playerService.findPlayersKnowsRefuge(refuge.getId());
		attacks = this.attackService.findAttacksByAttacker(refuge.getId());
		attacks.addAll(this.attackService.findAttacksByDefendant(refuge.getId()));
		characters = this.characterService.findCharactersByRefuge(refuge.getId());
		items = this.itemService.findItemsByRefuge(refuge.getId());
		moves = this.moveService.findMovesByRefuge(refuge.getId());

		for (final Player player : playersKnowsRefuge)
			player.getRefuges().remove(refuge);

		for (final Attack attack : attacks)
			this.attackService.delete(attack);

		for (final domain.Character character : characters)
			this.characterService.delete(character);

		for (final Item item : items)
			this.itemService.delete(item);

		for (final Move move : moves)
			this.moveService.delete(move);

		this.refugeRepository.delete(refuge);

	}

	public Refuge reconstruct(final Refuge refuge, final BindingResult binding) {
		Refuge result;
		Actor actor;

		if (refuge.getId() == 0) {

			actor = this.actorService.findActorByPrincipal();
			result = refuge;

			result.setCode(this.generateCode());
			result.setPlayer((Player) actor);
			result.setMomentOfCreation(new Date(System.currentTimeMillis() - 1));
			result.setWater(3);
			result.setFood(3);
			result.setMetal(3);
			result.setWood(3);

		} else {
			result = this.findOne(refuge.getId());

			result.setName(refuge.getName());

		}
		this.validator.validate(result, binding);

		return result;
	}
	public String generateCode() {
		String result;
		final String alphabet;
		Random random;

		random = new Random();

		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";

		result = "";
		for (int i = 0; i < 10; i++)
			result += alphabet.charAt(random.nextInt(alphabet.length()));

		return result;
	}
}
