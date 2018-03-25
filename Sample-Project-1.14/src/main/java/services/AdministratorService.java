
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import domain.Administrator;
import forms.UserAdminForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository --------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods --------------------------------------------------

	public Administrator create() {
		Administrator result;

		result = new Administrator();

		return result;
	}

	public Collection<Administrator> findAll() {

		Collection<Administrator> result;

		Assert.notNull(this.administratorRepository);
		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Administrator findOne(final int administratorId) {

		Administrator result;

		result = this.administratorRepository.findOne(administratorId);

		return result;

	}

	public Administrator save(final Administrator administrator) {

		assert administrator != null;

		Administrator result;

		result = this.administratorRepository.save(administrator);

		return result;

	}

	public void delete(final Administrator administrator) {

		assert administrator != null;
		assert administrator.getId() != 0;

		Assert.isTrue(this.administratorRepository.exists(administrator.getId()));

		this.administratorRepository.delete(administrator);

	}

	// Other business methods

	public Administrator reconstruct(final UserAdminForm userAdminForm, final BindingResult binding) {
		Administrator result;

		if (userAdminForm.getId() == 0) {

			result = this.actorService.createAdmin();

			result.getUserAccount().setUsername(userAdminForm.getUserAccount().getUsername());
			result.getUserAccount().setPassword(userAdminForm.getUserAccount().getPassword());
			result.setName(userAdminForm.getName());
			result.setSurname(userAdminForm.getSurname());
			result.setPostalAddress(userAdminForm.getPostalAddress());
			result.setPhoneNumber(userAdminForm.getPhoneNumber());
			result.setEmail(userAdminForm.getEmail());
			result.setBirthDate(userAdminForm.getBirthDate());

		} else {
			result = this.administratorRepository.findOne(userAdminForm.getId());

			result.setName(userAdminForm.getName());
			result.setSurname(userAdminForm.getSurname());
			result.setPostalAddress(userAdminForm.getPostalAddress());
			result.setPhoneNumber(userAdminForm.getPhoneNumber());
			result.setEmail(userAdminForm.getEmail());
			result.setBirthDate(userAdminForm.getBirthDate());
		}
		this.validator.validate(result, binding);

		return result;
	}
}
