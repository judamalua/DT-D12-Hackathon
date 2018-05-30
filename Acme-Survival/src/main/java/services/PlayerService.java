
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PlayerRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Player;
import domain.Shelter;
import forms.ActorForm;

@Service
@Transactional
public class PlayerService {

	// Managed repository --------------------------------------------------

	@Autowired
	private PlayerRepository	playerRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	public Player create() {
		//TODO orders must be bidirectional with user 
		Player result;

		final UserAccount userAccount;
		final Collection<Authority> auth;
		final Authority authority;
		Collection<Shelter> knownShelters;

		knownShelters = new HashSet<Shelter>();
		//TODO orders = new HashSet<Order>();

		result = new Player();

		userAccount = new UserAccount();
		auth = new HashSet<Authority>();
		authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		auth.add(authority);
		userAccount.setAuthorities(auth);

		result.setUserAccount(userAccount);

		result.setShelters(knownShelters);

		return result;
	}

	public Collection<Player> findAll() {

		Collection<Player> result;

		Assert.notNull(this.playerRepository);
		result = this.playerRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Player findOne(final int playerId) {
		Assert.isTrue(playerId != 0);

		Player result;

		result = this.playerRepository.findOne(playerId);

		Assert.notNull(result);

		return result;

	}

	public Player save(final Player player) {
		assert player != null;
		Actor actor = null;

		if (player.getId() != 0)
			actor = this.actorService.findActorByPrincipal();

		if (actor instanceof Player && player.getId() != 0)
			Assert.isTrue(player.equals(actor));

		Player result;

		result = this.playerRepository.save(player);

		return result;

	}

	public void delete(final Player player) {

		assert player != null;
		assert player.getId() != 0;

		Assert.isTrue(this.playerRepository.exists(player.getId()));

		this.playerRepository.delete(player);

	}

	// Other business methods ------------------------------------------------------------------------------------

	public Player reconstruct(final ActorForm userAdminForm, final BindingResult binding) {
		Player result;
		Actor actor;

		if (userAdminForm.getId() == 0) {
			Collection<Shelter> knownShelters;

			knownShelters = new HashSet<Shelter>();

			result = this.create();

			result.getUserAccount().setUsername(userAdminForm.getUserAccount().getUsername());
			result.getUserAccount().setPassword(userAdminForm.getUserAccount().getPassword());
			result.setName(userAdminForm.getName());
			result.setSurname(userAdminForm.getSurname());
			result.setAvatar(userAdminForm.getAvatar());
			result.setPhoneNumber(userAdminForm.getPhoneNumber());
			result.setEmail(userAdminForm.getEmail());
			result.setBirthDate(userAdminForm.getBirthDate());

			result.setShelters(knownShelters);

		} else {
			actor = this.actorService.findActorByPrincipal();
			result = this.playerRepository.findOne(userAdminForm.getId());

			Assert.isTrue(result.getId() == actor.getId());

			result.setName(userAdminForm.getName());
			result.setSurname(userAdminForm.getSurname());
			result.setAvatar(userAdminForm.getAvatar());
			result.setPhoneNumber(userAdminForm.getPhoneNumber());
			result.setEmail(userAdminForm.getEmail());
			result.setBirthDate(userAdminForm.getBirthDate());

		}
		this.validator.validate(result, binding);

		return result;
	}

	/**
	 * That method returns a collections of players of the system with pageable
	 * 
	 * @param pageable
	 * @return Page<Player>
	 * @author Luis
	 */
	public Page<Player> getPlayers(final Pageable pageable) {
		Page<Player> result;

		result = this.playerRepository.findAll(pageable);

		return result;

	}

	public Page<Shelter> findKnowSheltersByPlayer(final int playerId, final Pageable pageable) {
		Page<Shelter> result;

		result = this.playerRepository.findKnowSheltersByPlayer(playerId, pageable);

		return result;
	}

	public Collection<Player> findPlayersKnowsShelter(final int shelterId) {
		Collection<Player> result;

		result = this.playerRepository.findPlayersKnowsShelter(shelterId);

		return result;
	}

	public String findNumPlayers() {
		String result;

		result = this.playerRepository.findNumPlayers();

		return result;
	}

	public void flush() {
		this.playerRepository.flush();

	}

}
