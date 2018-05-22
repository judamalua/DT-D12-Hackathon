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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.PlayerService;
import domain.Configuration;
import domain.Player;

@Controller
@RequestMapping("/player")
public class PlayerController extends AbstractController {

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public PlayerController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the system players list
	 * 
	 * @param page
	 * @param anonymous
	 * 
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final boolean anonymous, @RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Player> players;
		Pageable pageable;
		Configuration configuration;

		try {
			if (!anonymous)
				this.actorService.checkActorLogin();

			result = new ModelAndView("player/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			players = this.playerService.getPlayers(pageable);

			result.addObject("players", players.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", players.getTotalPages());
			result.addObject("anonymous", anonymous);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

}
