
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Manager;
import forms.ActorForm;

@Service
@Transactional
public class ManagerService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ManagerRepository	managerRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	public Manager create() {
		Manager result;

		final UserAccount userAccount;
		final Collection<Authority> auth;
		final Authority authority;

		result = new Manager();

		userAccount = new UserAccount();
		auth = new HashSet<Authority>();
		authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		auth.add(authority);
		userAccount.setAuthorities(auth);

		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Manager> findAll() {

		Collection<Manager> result;

		Assert.notNull(this.managerRepository);
		result = this.managerRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Manager findOne(final int managerId) {

		Manager result;

		result = this.managerRepository.findOne(managerId);

		return result;

	}

	public Manager save(final Manager manager) {
		assert manager != null;
		Actor actor = null;

		if (manager.getId() != 0)
			actor = this.actorService.findActorByPrincipal();

		if (actor instanceof Manager && manager.getId() != 0)
			Assert.isTrue(manager.equals(actor));

		Manager result;

		result = this.managerRepository.save(manager);

		return result;

	}

	public void delete(final Manager manager) {

		assert manager != null;
		assert manager.getId() != 0;

		Assert.isTrue(this.managerRepository.exists(manager.getId()));

		this.managerRepository.delete(manager);

	}

	public Manager reconstruct(final ActorForm userAdminForm, final BindingResult binding) {
		Manager result;
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
			result = this.managerRepository.findOne(userAdminForm.getId());

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

	public void flush() {
		this.managerRepository.flush();

	}
}
