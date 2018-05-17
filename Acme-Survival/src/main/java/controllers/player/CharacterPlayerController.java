/*
 * RendezvousController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.player;

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
import services.CharacterService;
import services.ConfigurationService;
import services.ItemService;
import services.RefugeService;
import controllers.AbstractController;
import domain.Actor;
import domain.Character;
import domain.Configuration;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/character/player")
public class CharacterPlayerController extends AbstractController {

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private CharacterService		characterService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ItemService				itemService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public CharacterPlayerController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the characters list of a player
	 * 
	 * @param page
	 * 
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Character> characters;
		Refuge refuge;
		Pageable pageable;
		Configuration configuration;
		Player player;

		try {
			result = new ModelAndView("character/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			characters = this.characterService.findCharactersByRefugePageable(refuge.getId(), pageable);

			result.addObject("characters", characters.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", characters.getTotalPages());
			result.addObject("requestURI", "character/list");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	/**
	 * That method returns a model and view with the character display
	 * 
	 * @param characterId
	 * 
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam(required = true) final Integer characterId, @RequestParam(required = false, defaultValue = "false") final boolean discard) {
		ModelAndView result;
		Actor player;
		Character character;
		Refuge refuge;

		try {
			result = new ModelAndView("character/display");
			player = this.actorService.findActorByPrincipal();
			Assert.isTrue((player instanceof Player));
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			character = this.characterService.findOne(characterId);
			if (discard == true)
				this.itemService.UpdateDiscard(character);
			Assert.isTrue(character.getRefuge().getId() == refuge.getId());
			character = this.characterService.findOne(characterId);
			result.addObject("character", character);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
}
