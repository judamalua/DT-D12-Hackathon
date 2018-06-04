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

import java.util.Collection;
import java.util.Date;

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
import services.AttackService;
import services.CharacterService;
import services.ConfigurationService;
import services.ItemService;
import services.RoomService;
import services.ShelterService;
import controllers.AbstractController;
import domain.Actor;
import domain.Character;
import domain.Configuration;
import domain.Player;
import domain.Room;
import domain.Shelter;

@Controller
@RequestMapping("/character/player")
public class CharacterPlayerController extends AbstractController {

	@Autowired
	private ShelterService			shelterService;

	@Autowired
	private CharacterService		characterService;

	@Autowired
	private AttackService			attackService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ItemService				itemService;

	@Autowired
	private RoomService				roomService;

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
		Shelter shelter;
		Pageable pageable;
		Configuration configuration;
		Player player;

		try {
			result = new ModelAndView("character/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			player = (Player) this.actorService.findActorByPrincipal();
			shelter = this.shelterService.findShelterByPlayer(player.getId());
			characters = this.characterService.findCharactersByShelterPageable(shelter.getId(), pageable);

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
		Shelter shelter;

		try {
			result = new ModelAndView("character/display");
			player = this.actorService.findActorByPrincipal();
			Assert.isTrue((player instanceof Player));
			shelter = this.shelterService.findShelterByPlayer(player.getId());
			character = this.characterService.findOne(characterId);

			if (discard) {
				this.itemService.UpdateDiscard(character);
			}
			Assert.isTrue(character.getShelter().getId() == shelter.getId());

			character = this.characterService.findOne(characterId);
			result.addObject("character", character);
			result.addObject("isAttacking", this.attackService.playerAlreadyAttacking(player.getId()));

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
	@RequestMapping("/move")
	public ModelAndView move(@RequestParam final Integer characterId, @RequestParam(required = false) final Integer roomId) {
		ModelAndView result;
		Actor player;
		Character character;
		Shelter shelter;
		Room room;
		Integer numCharacter;
		Collection<Character> charactersInMission;

		try {
			result = new ModelAndView("character/display");
			player = this.actorService.findActorByPrincipal();
			Assert.isTrue((player instanceof Player));
			shelter = this.shelterService.findShelterByPlayer(player.getId());
			character = this.characterService.findOne(characterId);
			charactersInMission = this.characterService.findCharactersCurrentlyInMission(shelter.getId());

			Assert.isTrue(character.getShelter().getId() == shelter.getId());
			Assert.isTrue(!charactersInMission.contains(character));
			Assert.isTrue(character.getCurrentHealth() > 0);

			if (roomId != null) {
				room = this.roomService.findOne(roomId);
				numCharacter = this.characterService.findCharactersByRoom(room.getId()).size();
				Assert.isTrue(numCharacter < room.getRoomDesign().getMaxCapacityCharacters());
				character.setRoomEntrance(new Date(System.currentTimeMillis() - 1));
				Assert.isTrue(room.getShelter().equals(shelter));
			} else {
				room = null;
				character.setRoomEntrance(null);
			}
			Assert.isTrue(!this.attackService.playerAlreadyAttacking(player.getId()));
			character.setRoom(room);

			character = this.characterService.save(character);

			result.addObject("character", character);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
}
