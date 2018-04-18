
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdminRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Admin;
import forms.ActorForm;

@Service
@Transactional
public class AdminService {

	// Managed repository --------------------------------------------------

	@Autowired
	private AdminRepository	adminRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private Validator		validator;
	@Autowired
	private ActorService	actorService;


	// Simple CRUD methods --------------------------------------------------

	public Admin create() {
		Admin result;

		final UserAccount userAccount;
		final Collection<Authority> auth;
		final Authority authority;

		result = new Admin();

		userAccount = new UserAccount();
		auth = new HashSet<Authority>();
		authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		auth.add(authority);
		userAccount.setAuthorities(auth);

		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Admin> findAll() {

		Collection<Admin> result;

		Assert.notNull(this.adminRepository);
		result = this.adminRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Admin findOne(final int adminId) {

		Admin result;

		result = this.adminRepository.findOne(adminId);

		return result;

	}

	public Admin save(final Admin admin) {
		assert admin != null;
		Actor actor = null;

		if (admin.getId() != 0)
			actor = this.actorService.findActorByPrincipal();

		if (actor instanceof Admin && admin.getId() != 0)
			Assert.isTrue(admin.equals(actor));

		Admin result;

		result = this.adminRepository.save(admin);

		return result;

	}

	public void delete(final Admin admin) {

		assert admin != null;
		assert admin.getId() != 0;

		Assert.isTrue(this.adminRepository.exists(admin.getId()));

		this.adminRepository.delete(admin);

	}

	public Admin reconstruct(final ActorForm userAdminForm, final BindingResult binding) {
		Admin result;
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
			result = this.adminRepository.findOne(userAdminForm.getId());

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
