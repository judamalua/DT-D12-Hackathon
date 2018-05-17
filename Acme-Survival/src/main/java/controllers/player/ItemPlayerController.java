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
		Refuge refuge;
		Item item;

		try {
			result = new ModelAndView("character/display");
			player = this.actorService.findActorByPrincipal();
			Assert.isTrue((player instanceof Player));
			refuge = this.refugeService.findRefugeByPlayer(player.getId());
			character = this.characterService.findOne(characterId);
			item = this.itemService.findOne(itemId);
			//Actualización de objeto equipado y el que desequipa
			if (character.getItem() != null) {
				character.getItem().setEquipped(false);
				this.itemService.save(character.getItem());
				character.setItem(null);
			}
			Assert.isTrue(!item.isEquipped());
			character.setItem(item);
			item.setEquipped(true);
			this.itemService.save(item);
			this.characterService.save(character);

			result.addObject("character", character);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

}
