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
import services.RefugeService;
import domain.Actor;
import domain.Configuration;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/refuge")
public class RefugeController extends AbstractController {

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public RefugeController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the system refuge list
	 * 
	 * @param page
	 * @param anonymous
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Refuge> refuges;
		Pageable pageable;
		Configuration configuration;
		Actor actor;
		Refuge refuge = null;

		try {
			result = new ModelAndView("refuge/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());

			refuges = this.refugeService.findAll(pageable);

			if (this.actorService.getLogged()) {
				actor = this.actorService.findActorByPrincipal();
				if (actor instanceof Player)
					refuge = this.refugeService.findRefugeByPlayer(actor.getId());
			}

			result.addObject("refuges", refuges.getContent());
			result.addObject("page", page);
			result.addObject("hasRefuge", refuge != null);
			result.addObject("pageNum", refuges.getTotalPages());
			result.addObject("requestURI", "refuge/list.do?");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	/**
	 * That method returns a model and view with the refuge display
	 * 
	 * @param page
	 * @param anonymous
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final int refugeId) {
		ModelAndView result;
		final Refuge refuge;
		Actor actor;

		try {
			result = new ModelAndView("refuge/display");

			refuge = this.refugeService.findOne(refugeId);
			Assert.notNull(refuge);

			if (this.actorService.getLogged()) {
				actor = this.actorService.findActorByPrincipal();
				Assert.isTrue(!(actor instanceof Player));
			}

			result.addObject("refuge", refuge);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
}
