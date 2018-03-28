
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import forms.UserAdminForm;

@Service
@Transactional
public class ActorService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ActorRepository	actorRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------
	/**
	 * Get all the actors in the system
	 * 
	 * @return all the actors registered in the system
	 * @author MJ
	 */
	public Collection<Actor> findAll() {

		Collection<Actor> result;

		Assert.notNull(this.actorRepository);
		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	/**
	 * Get the actor with the id passed as parameter
	 * 
	 * @param actorId
	 * @return an actor with id equals to actorId
	 * 
	 * @author MJ
	 */
	public Actor findOne(final int actorId) {

		Actor result;

		result = this.actorRepository.findOne(actorId);

		return result;

	}

	/**
	 * That method create a instance of a user
	 * 
	 * @return User
	 * @author Luis
	 */
	public Administrator createAdmin() {
		Administrator result;

		UserAccount userAccount;
		Collection<Authority> authorities;
		Authority authority;

		result = new Administrator();

		userAccount = new UserAccount();
		authorities = new HashSet<Authority>();
		authority = new Authority();

		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setUserAccount(userAccount);

		return result;
	}

	/**
	 * Saves the actor passed as parameter
	 * 
	 * @param actor
	 * @return The actor saved in the system
	 * @author Luis
	 */
	public Actor save(final Actor actor) {

		assert actor != null;

		Actor result;

		result = this.actorRepository.save(actor);

		return result;

	}

	/**
	 * Delete the actor passed as parameter
	 * 
	 * @param actor
	 * @author Luis
	 */
	public void delete(final Actor actor) {

		assert actor != null;
		assert actor.getId() != 0;

		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);

	}

	/**
	 * Get the actor logged in the system
	 * 
	 * @return the actor logged in the system
	 * @author MJ
	 */
	public Actor findActorByPrincipal() {
		UserAccount userAccount;
		Actor result;

		userAccount = LoginService.getPrincipal();
		result = this.findActorByUserAccount(userAccount);

		return result;
	}

	/**
	 * Get an actor with the UserAccount passed
	 * 
	 * @param userAccount
	 * @return The actor with the UserAccount
	 * @author MJ
	 */
	public Actor findActorByUserAccount(final UserAccount userAccount) {

		Assert.notNull(userAccount);

		Actor result;

		result = this.actorRepository.findActorByUserAccountId(userAccount.getId());

		return result;
	}

	/**
	 * Checks there is an actor logged in the system
	 * 
	 * @author MJ
	 */
	public void checkUserLogin() {
		Actor actor;

		actor = this.findActorByPrincipal();

		Assert.notNull(actor);
	}

	/**
	 * This method checks if there is someone logged in the system
	 * 
	 * @return true if there is someone logged, false otherwise
	 * @author Juanmi
	 */
	public boolean getLogged() {
		boolean result;

		//		result = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		try {
			this.checkUserLogin();
			result = true;
		} catch (final Throwable oops) {
			result = false;
		}
		return result;
	}

	/**
	 * This method obtains the age of the actor passed by parameters
	 * 
	 * @param actor
	 * @return age
	 * @author MJ
	 */
	private int getAge(final Actor actor) {
		Assert.notNull(actor);

		final int result;
		LocalDate birthDay;
		LocalDate currentDate;

		currentDate = LocalDate.now();
		birthDay = LocalDate.fromDateFields(actor.getBirthDate());
		result = Years.yearsBetween(birthDay, currentDate).getYears();
		Assert.isTrue(result > 0);

		return result;
	}

	/**
	 * This method registers in the system the actor passed by parameters
	 * 
	 * @param actor
	 * @return the actor registered
	 * @author Luis
	 */
	public Actor registerActor(final Actor actor) {
		Actor result;
		String password;
		Md5PasswordEncoder encoder;

		Assert.notNull(actor.getUserAccount());
		Assert.isTrue(!this.actorRepository.exists((actor.getId())));

		encoder = new Md5PasswordEncoder();

		password = actor.getUserAccount().getPassword();
		password = encoder.encodePassword(password, null);
		actor.getUserAccount().setPassword(password);

		result = this.actorRepository.save(actor);

		return result;
	}

	/**
	 * This method deconstructs an User/Administrator object, that is, transforms
	 * an User/Administrator object into a UserAdminForm object to be edited
	 * 
	 * @param user
	 *            to be deconstructed into an UserAdminForm
	 * @return UserAdminForm with the data of the user given by parameters
	 * 
	 * @author Juanmi
	 */
	public UserAdminForm deconstruct(final Actor actor) {
		UserAdminForm result;

		result = new UserAdminForm();

		result.setId(actor.getId());
		result.setVersion(actor.getVersion());
		result.setName(actor.getName());
		result.setSurname(actor.getSurname());
		result.setAvatar(actor.getAvatar());
		result.setPhoneNumber(actor.getPhoneNumber());
		result.setEmail(actor.getEmail());
		result.setBirthDate(actor.getBirthDate());

		return result;
	}
}
