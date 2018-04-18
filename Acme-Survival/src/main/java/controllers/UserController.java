/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.PlayerService;
import domain.Configuration;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private PlayerService				userService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the system users list
	 * 
	 * @param page
	 * @param anonymous
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final boolean anonymous, @RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<User> users;
		Pageable pageable;
		Configuration configuration;

		try {
			if (!anonymous)
				this.actorService.checkUserLogin();

			result = new ModelAndView("user/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			users = this.userService.getUsers(pageable);

			result.addObject("users", users.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", users.getTotalPages());
			result.addObject("anonymous", anonymous);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	// Displaying  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the display of an actor
	 * 
	 * @param actorId
	 * @param anonymous
	 * @param rsvPage
	 * @param createdRendezvousPage
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam(required = false) final Integer actorId, @RequestParam(defaultValue = "true") final boolean anonymous) {
		ModelAndView result;
		User user;

		try {
			if (!anonymous)
				this.actorService.checkUserLogin();
			result = new ModelAndView("actor/display");
			if (actorId != null)
				user = this.userService.findOne(actorId);
			else
				user = (User) this.actorService.findActorByPrincipal();

			Assert.notNull(user);

			result.addObject("actor", user);
			result.addObject("anonymous", anonymous);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
}
