
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.DesignerRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Designer;
import forms.ActorForm;

@Service
@Transactional
public class DesignerService {

	// Managed repository --------------------------------------------------

	@Autowired
	private DesignerRepository	designerRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	public Designer create() {
		Designer result;

		final UserAccount userAccount;
		final Collection<Authority> auth;
		final Authority authority;

		result = new Designer();

		userAccount = new UserAccount();
		auth = new HashSet<Authority>();
		authority = new Authority();
		authority.setAuthority(Authority.DESIGNER);
		auth.add(authority);
		userAccount.setAuthorities(auth);

		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Designer> findAll() {

		Collection<Designer> result;

		Assert.notNull(this.designerRepository);
		result = this.designerRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Designer findOne(final int designerId) {

		Designer result;

		result = this.designerRepository.findOne(designerId);

		return result;

	}

	public Designer save(final Designer designer) {
		assert designer != null;
		Actor actor = null;

		if (designer.getId() != 0)
			actor = this.actorService.findActorByPrincipal();

		if (actor instanceof Designer && designer.getId() != 0)
			Assert.isTrue(designer.equals(actor));

		Designer result;

		result = this.designerRepository.save(designer);

		return result;

	}

	public void delete(final Designer designer) {

		assert designer != null;
		assert designer.getId() != 0;

		Assert.isTrue(this.designerRepository.exists(designer.getId()));

		this.designerRepository.delete(designer);

	}

	public Designer reconstruct(final ActorForm userAdminForm, final BindingResult binding) {
		Designer result;
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
			result = this.designerRepository.findOne(userAdminForm.getId());

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
		this.designerRepository.flush();

	}
}
