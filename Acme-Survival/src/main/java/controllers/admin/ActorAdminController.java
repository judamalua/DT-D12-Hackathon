/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdminService;
import services.ConfigurationService;
import services.DesignerService;
import services.ManagerService;
import services.ModeratorService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Actor;
import domain.Admin;
import domain.Configuration;
import domain.Designer;
import domain.Manager;
import domain.Moderator;
import forms.ActorForm;

@Controller
@RequestMapping("/actor/admin")
public class ActorAdminController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private ModeratorService		moderatorService;
	@Autowired
	private DesignerService			designerService;
	@Autowired
	private AdminService			adminService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ActorAdminController() {
		super();
	}

	// Registering admin ------------------------------------------------------------
	/**
	 * That method registers an admin in the system and saves it.
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerAdmin() {
		ModelAndView result;
		ActorForm admin;

		admin = new ActorForm();

		result = this.createEditModelAndViewRegister(admin);

		result.addObject("actionURL", "actor/admin/register.do");

		return result;
	}

	//Edit an admin
	/**
	 * That method edits the profile of a admin
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editUser() {
		ModelAndView result;
		Admin admin;
		final ActorForm adminForm;

		admin = (Admin) this.actorService.findActorByPrincipal();
		Assert.notNull(admin);
		adminForm = this.actorService.deconstruct(admin);
		result = this.createEditModelAndView(adminForm);

		return result;
	}

	//Saving admin ---------------------------------------------------------------------
	/**
	 * That method saves an admin in the system
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView registerAdministrator(@ModelAttribute("admin") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Authority auth;
		Admin admin = null;
		try {
			admin = this.adminService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		if (binding.hasErrors())
			result = this.createEditModelAndViewRegister(actor, "admin.params.error");
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.ADMIN);
				Assert.isTrue(admin.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(actor.getConfirmPassword().equals(admin.getUserAccount().getPassword()), "Passwords do not match");
				this.actorService.registerActor(admin);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndViewRegister(actor, "admin.username.error");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndViewRegister(actor, "admin.password.error");
				else
					result = this.createEditModelAndViewRegister(actor, "admin.commit.error");
			}

		return result;
	}

	//Updating profile of a admin ---------------------------------------------------------------------
	/**
	 * 
	 * 
	 * That method update the profile of a administrator.
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView updateAdministrator(@ModelAttribute("actor") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Admin admin = null;

		try {
			admin = this.adminService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(actor, "actor.params.error");
		else
			try {
				this.actorService.save(admin);
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	// Registering manager ------------------------------------------------------------
	/**
	 * That method registers an manager in the system and saves it.
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/registerManager", method = RequestMethod.GET)
	public ModelAndView registerManager() {
		ModelAndView result;
		ActorForm admin;

		admin = new ActorForm();

		result = this.createEditModelAndViewRegister(admin);

		result.addObject("actionURL", "actor/admin/registerManager.do");

		return result;
	}

	//Saving manager ---------------------------------------------------------------------
	/**
	 * That method saves an manager in the system
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/registerManager", method = RequestMethod.POST, params = "save")
	public ModelAndView registerManager(@ModelAttribute("manager") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Authority auth;
		Manager manager = null;
		try {
			manager = this.managerService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		if (binding.hasErrors())
			result = this.createEditModelAndViewRegister(actor, "admin.params.error");
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.MANAGER);
				Assert.isTrue(manager.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(actor.getConfirmPassword().equals(manager.getUserAccount().getPassword()), "Passwords do not match");
				this.actorService.registerActor(manager);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndViewRegister(actor, "admin.username.error");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndViewRegister(actor, "admin.password.error");
				else
					result = this.createEditModelAndViewRegister(actor, "admin.commit.error");
			}

		return result;
	}
	// Registering moderator ------------------------------------------------------------
	/**
	 * That method registers an manager in the system and saves it.
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/registerModerator", method = RequestMethod.GET)
	public ModelAndView registerModerator() {
		ModelAndView result;
		ActorForm admin;

		admin = new ActorForm();

		result = this.createEditModelAndViewRegister(admin);

		result.addObject("actionURL", "actor/admin/registerModerator.do");

		return result;
	}

	//Saving Moderator ---------------------------------------------------------------------
	/**
	 * That method saves a moderator in the system
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/registerModerator", method = RequestMethod.POST, params = "save")
	public ModelAndView registerModerator(@ModelAttribute("moderator") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Authority auth;
		Moderator moderator = null;
		try {
			moderator = this.moderatorService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		if (binding.hasErrors())
			result = this.createEditModelAndViewRegister(actor, "admin.params.error");
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.MODERATOR);
				Assert.isTrue(moderator.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(actor.getConfirmPassword().equals(moderator.getUserAccount().getPassword()), "Passwords do not match");
				this.actorService.registerActor(moderator);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndViewRegister(actor, "admin.username.error");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndViewRegister(actor, "admin.password.error");
				else
					result = this.createEditModelAndViewRegister(actor, "admin.commit.error");
			}

		return result;
	}

	// Registering designer ------------------------------------------------------------
	/**
	 * That method registers an manager in the system and saves it.
	 * 
	 * @param
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/registerDesigner", method = RequestMethod.GET)
	public ModelAndView registerDesigner() {
		ModelAndView result;
		ActorForm admin;

		admin = new ActorForm();

		result = this.createEditModelAndViewRegister(admin);

		result.addObject("actionURL", "actor/admin/registerDesigner.do");

		return result;
	}

	//Saving designer ---------------------------------------------------------------------
	/**
	 * That method saves a designer in the system
	 * 
	 * @param save
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping(value = "/registerDesigner", method = RequestMethod.POST, params = "save")
	public ModelAndView registerDesginer(@ModelAttribute("designer") final ActorForm actor, final BindingResult binding) {
		ModelAndView result;
		Authority auth;
		Designer designer = null;
		try {
			designer = this.designerService.reconstruct(actor, binding);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		if (binding.hasErrors())
			result = this.createEditModelAndViewRegister(actor, "admin.params.error");
		else
			try {
				auth = new Authority();
				auth.setAuthority(Authority.DESIGNER);
				Assert.isTrue(designer.getUserAccount().getAuthorities().contains(auth));
				Assert.isTrue(actor.getConfirmPassword().equals(designer.getUserAccount().getPassword()), "Passwords do not match");
				this.actorService.registerActor(designer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndViewRegister(actor, "admin.username.error");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Passwords do not match"))
					result = this.createEditModelAndViewRegister(actor, "admin.password.error");
				else
					result = this.createEditModelAndViewRegister(actor, "admin.commit.error");
			}

		return result;
	}

	@RequestMapping("/list-actors")
	public ModelAndView listActors(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Actor> actors;
		Pageable pageable;
		Configuration configuration;

		result = new ModelAndView("actor/list-actors");

		configuration = this.configurationService.findConfiguration();

		pageable = new PageRequest(page, configuration.getPageSize());
		actors = this.actorService.findAllActors(pageable);

		result.addObject("actors", actors.getContent());
		result.addObject("requestURI", "actor/admin/list-actors.do");
		result.addObject("page", page);
		result.addObject("pageNum", actors.getTotalPages());

		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(final int actorId) {
		ModelAndView result;

		try {
			final Actor actor = this.actorService.findOne(actorId);

			Assert.notNull(actor);
			Assert.isTrue(!actor.getUserAccount().getBanned());

			this.userAccountService.ban(actor);

			result = this.listActors(0);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(final int actorId) {
		ModelAndView result;

		try {
			final Actor actor = this.actorService.findOne(actorId);

			Assert.notNull(actor);
			Assert.isTrue(actor.getUserAccount().getBanned());

			this.userAccountService.unban(actor);

			result = this.listActors(0);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final ActorForm actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}
	protected ModelAndView createEditModelAndViewRegister(final ActorForm actor) {
		ModelAndView result;

		result = this.createEditModelAndViewRegister(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("message", messageCode);
		result.addObject("actor", actor);

		return result;

	}

	protected ModelAndView createEditModelAndViewRegister(final ActorForm actor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("admin/register");
		result.addObject("message", messageCode);
		result.addObject("actor", actor);

		return result;

	}
}
