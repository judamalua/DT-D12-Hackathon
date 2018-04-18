
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ModeratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Moderator;
import forms.ActorForm;

@Service
@Transactional
public class ModeratorService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ModeratorRepository	moderatorRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	public Moderator create() {
		Moderator result;

		final UserAccount userAccount;
		final Collection<Authority> auth;
		final Authority authority;

		result = new Moderator();

		userAccount = new UserAccount();
		auth = new HashSet<Authority>();
		authority = new Authority();
		authority.setAuthority(Authority.MODERATOR);
		auth.add(authority);
		userAccount.setAuthorities(auth);

		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Moderator> findAll() {

		Collection<Moderator> result;

		Assert.notNull(this.moderatorRepository);
		result = this.moderatorRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Moderator findOne(final int moderatorId) {

		Moderator result;

		result = this.moderatorRepository.findOne(moderatorId);

		return result;

	}

	public Moderator save(final Moderator moderator) {
		assert moderator != null;
		Actor actor = null;

		if (moderator.getId() != 0)
			actor = this.actorService.findActorByPrincipal();

		if (actor instanceof Moderator && moderator.getId() != 0)
			Assert.isTrue(moderator.equals(actor));

		Moderator result;

		result = this.moderatorRepository.save(moderator);

		return result;

	}

	public void delete(final Moderator moderator) {

		assert moderator != null;
		assert moderator.getId() != 0;

		Assert.isTrue(this.moderatorRepository.exists(moderator.getId()));

		this.moderatorRepository.delete(moderator);

	}

	public Moderator reconstruct(final ActorForm userAdminForm, final BindingResult binding) {
		Moderator result;
		Actor actor;

		if (userAdminForm.getId() == 0) {
			result = this.create();

			result.getUserAccount().setUsername(userAdminForm.getUserAccount().getUsername());
			result.getUserAccount().setPassword(userAdminForm.getUserAccount().getPassword());
			result.setName(userAdminForm.getName());
			result.setSurname(userAdminForm.getSurname());
			result.setAvatar(userAdminForm.getAvatar());
			result.setPhoneNumber(userAdminForm.getPhoneNumber());
			result.setEmail(userAdminForm.getEmail());
			result.setBirthDate(userAdminForm.getBirthDate());

		} else {
			actor = this.actorService.findActorByPrincipal();
			result = this.moderatorRepository.findOne(userAdminForm.getId());

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
}
