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
import java.util.HashSet;

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

import services.ActorService;
import services.CharacterService;
import services.ConfigurationService;
import services.GatherService;
import services.PlayerService;
import services.RoomService;
import services.ShelterService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Inventory;
import domain.Player;
import domain.RestorationRoom;
import domain.Room;
import domain.Shelter;

@Controller
@RequestMapping("/shelter/player")
public class ShelterPlayerController extends AbstractController {

	@Autowired
	private ShelterService			shelterService;

	@Autowired
	private PlayerService			playerService;

	@Autowired
	private RoomService				roomService;

	@Autowired
	private CharacterService		characterService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private GatherService			gatherService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ShelterPlayerController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the shelter display
	 * 
	 * @param pageRoom
	 * @param shelterId
	 *            (Optional)
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int pageRoom) {
		ModelAndView result;
		final Shelter shelter, ownShelter;
		Configuration configuration;
		Actor actor;
		Pageable pageable;
		Page<Room> rooms;
		boolean owner = false;
		boolean knowShelter = false;
		Inventory inventory;
		final Collection<domain.Character> characters, charactersUpdated;
		Integer capacity;
		try {
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(pageRoom, configuration.getPageSize());

			result = new ModelAndView("shelter/display");

			actor = this.actorService.findActorByPrincipal();

			Assert.isTrue((actor instanceof Player));

			ownShelter = this.shelterService.findShelterByPlayer(actor.getId());

			shelter = ownShelter;
			Assert.notNull(shelter, "Not have shelter");

			characters = this.characterService.findCharactersByShelter(shelter.getId());
			charactersUpdated = new HashSet<>();
			for (final domain.Character character : characters)
				if (character.getRoom() != null && (character.getRoom().getRoomDesign() instanceof RestorationRoom))
					charactersUpdated.add(this.characterService.updateStats(character));
				else
					charactersUpdated.add(character);

			inventory = this.shelterService.updateInventory(shelter);
			this.gatherService.updateGatheringMissions();

			knowShelter = ((Player) actor).getShelters().contains(shelter);
			owner = ownShelter != null && ownShelter.equals(shelter);
			rooms = this.roomService.findRoomsByShelter(shelter.getId(), pageable);

			capacity = this.shelterService.getCurrentCharacterCapacity(ownShelter);
			//Assert.isTrue(shelter.equals(ownShelter) || player.getShelters().contains(shelter));

			result.addObject("shelter", shelter);
			result.addObject("rooms", rooms.getContent());
			result.addObject("pageRoom", pageRoom);
			result.addObject("pageNumRoom", rooms.getTotalPages());
			result.addObject("knowShelter", knowShelter);
			result.addObject("characters", charactersUpdated);
			result.addObject("inventory", inventory);
			result.addObject("owner", owner);
			result.addObject("characterCapacity", capacity);

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have shelter"))
				result = new ModelAndView("redirect:/shelter/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Shelter ownShelter;
		Player actor;

		try {
			actor = (Player) this.actorService.findActorByPrincipal();
			ownShelter = this.shelterService.findShelterByPlayer(actor.getId());
			Assert.notNull(ownShelter, "Not have shelter");
			result = this.createEditModelAndView(ownShelter);

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have shelter"))
				result = new ModelAndView("redirect:/shelter/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Shelter shelter;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Player);

			shelter = this.shelterService.create();

			result = this.createEditModelAndView(shelter);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:misc/403");
		}
		return result;
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("shelter") Shelter shelter, final BindingResult binding) {
		ModelAndView result;
		Shelter sendedShelter = null;
		Player player;
		Shelter ownShelter;

		try {
			sendedShelter = shelter;
			shelter = this.shelterService.reconstruct(shelter, binding);
		} catch (final Throwable oops) {//Not delete
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(sendedShelter, "shelter.params.error");
		else
			try {
				player = (Player) this.actorService.findActorByPrincipal();
				ownShelter = this.shelterService.findShelterByPlayer(player.getId());
				if (shelter.getId() != 0)
					Assert.isTrue(ownShelter.equals(shelter));
				else
					Assert.isTrue(ownShelter == null, "Not have shelter");
				this.shelterService.save(shelter);
				result = new ModelAndView("redirect:/shelter/player/display.do");

			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(shelter, "shelter.name.error");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Not have shelter"))
					result = new ModelAndView("redirect:/shelter/player/create.do");
				else
					result = this.createEditModelAndView(shelter, "shelter.commit.error");
			}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Shelter shelter) {
		ModelAndView result;

		result = this.createEditModelAndView(shelter, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Shelter shelter, final String message) {
		ModelAndView result;

		result = new ModelAndView("shelter/edit");
		result.addObject("shelter", shelter);
		result.addObject("message", message);

		return result;

	}

}
