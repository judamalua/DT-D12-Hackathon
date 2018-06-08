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
import services.ShelterService;
import domain.Actor;
import domain.Configuration;
import domain.Player;
import domain.Shelter;

@Controller
@RequestMapping("/shelter")
public class ShelterController extends AbstractController {

	@Autowired
	private ShelterService			shelterService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ShelterController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the system shelter list
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
		Page<Shelter> shelters;
		Pageable pageable;
		Configuration configuration;
		Actor actor;
		Shelter shelter = null;

		try {
			result = new ModelAndView("shelter/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());

			shelters = this.shelterService.findAll(pageable);

			if (this.actorService.getLogged()) {
				actor = this.actorService.findActorByPrincipal();
				if (actor instanceof Player)
					shelter = this.shelterService.findShelterByPlayer(actor.getId());
			}

			result.addObject("shelters", shelters.getContent());
			result.addObject("page", page);
			result.addObject("hasShelter", shelter != null);
			result.addObject("pageNum", shelters.getTotalPages());
			result.addObject("requestURI", "shelter/list.do?");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	/**
	 * That method returns a model and view with the shelter display
	 * 
	 * @param page
	 * @param anonymous
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final int shelterId) {
		ModelAndView result;
		final Shelter shelter;
		Actor actor;

		try {
			result = new ModelAndView("shelter/display");

			shelter = this.shelterService.findOne(shelterId);
			Assert.notNull(shelter);

			if (this.actorService.getLogged()) {
				actor = this.actorService.findActorByPrincipal();
				Assert.isTrue(!(actor instanceof Player));
			}

			result.addObject("shelter", shelter);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
}
