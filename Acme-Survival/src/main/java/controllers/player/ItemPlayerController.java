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
import domain.Item;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/item/player")
public class ItemPlayerController extends AbstractController {

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private CharacterService		characterService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ItemService				itemService;


	// Constructors -----------------------------------------------------------

	public ItemPlayerController() {
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
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final int page, @RequestParam(required = true) final int characterId) {
		ModelAndView result;
		Page<Item> items;
		Refuge refuge;
		Pageable pageable;
		Configuration configuration;
		Player player;

		try {
			result = new ModelAndView("item/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			items = this.itemService.findItemsByRefuge(refuge.getId(), pageable);

			result.addObject("items", items.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", items.getTotalPages());
			result.addObject("characterId", characterId);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	/**
	 * That method returns a model and view with the character display with a new Item Equipped
	 * 
	 * @param characterId
	 * 
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/equip")
	public ModelAndView display(@RequestParam(required = true) final Integer itemId, @RequestParam(required = true) final Integer characterId) {
		ModelAndView result;
		Actor player;
		Character character;
		Item item;

		try {
			result = new ModelAndView("character/display");
			player = this.actorService.findActorByPrincipal();
			Assert.isTrue((player instanceof Player));
			character = this.characterService.findOne(characterId);
			item = this.itemService.findOne(itemId);
			this.itemService.UpdateEquipped(item, characterId);

			result.addObject("character", character);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	/**
	 * That method remove a item from your refuge
	 * 
	 * @param characterId
	 * 
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/remove")
	public ModelAndView remove(@RequestParam(required = true) final Integer itemId) {
		ModelAndView result;
		Actor player;
		Item item;

		try {
			player = this.actorService.findActorByPrincipal();
			Assert.isTrue((player instanceof Player));
			item = this.itemService.findOne(itemId);
			this.itemService.delete(item);
			result = this.arsenal(0);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	/**
	 * That method returns a model and view with the characters list of a player
	 * 
	 * @param page
	 * 
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/armory")
	public ModelAndView arsenal(@RequestParam(required = false, defaultValue = "0") final int page) {
		ModelAndView result;
		Page<Item> items;
		Refuge refuge;
		Pageable pageable;
		Configuration configuration;
		Player player;

		try {
			result = new ModelAndView("item/armory");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			player = (Player) this.actorService.findActorByPrincipal();
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			items = this.itemService.findItemsByRefuge(refuge.getId(), pageable);

			result.addObject("items", items.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", items.getTotalPages());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

}
