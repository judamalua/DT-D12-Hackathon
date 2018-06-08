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

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CharacterService;
import services.ConfigurationService;
import services.InventoryService;
import services.RoomDesignService;
import services.RoomService;
import services.ShelterService;
import controllers.AbstractController;
import domain.Actor;
import domain.Barrack;
import domain.Configuration;
import domain.Inventory;
import domain.Player;
import domain.ResourceRoom;
import domain.RestorationRoom;
import domain.Room;
import domain.RoomDesign;
import domain.Shelter;
import domain.Warehouse;

@Controller
@RequestMapping("/room/player")
public class RoomPlayerController extends AbstractController {

	@Autowired
	private RoomService				roomService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RoomDesignService		roomDesignService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ShelterService			shelterService;

	@Autowired
	private InventoryService		inventoryService;

	@Autowired
	private CharacterService		characterService;


	// Constructors -----------------------------------------------------------

	public RoomPlayerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Room room;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Player);

			room = this.roomService.create();

			result = this.createEditModelAndView(room);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer roomId) {
		ModelAndView result;
		final Room room;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Player);

			room = this.roomService.findOne(roomId);

			this.roomService.delete(room);

			result = new ModelAndView("redirect:/shelter/player/display.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("You not have space")) {
				result = new ModelAndView("redirect:shelter/player/display.do");
				result.addObject("message", "shelter.capacity.error");
			} else if (oops.getMessage().contains("You have a lot of objects")) {
				result = new ModelAndView("redirect:shelter/player/display.do");
				result.addObject("message", "shelter.objects.error");
			} else {
				result = new ModelAndView("redirect:/misc/403");
			}
		}
		return result;
	}

	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public @ResponseBody
	String getResources(@RequestParam final String roomDesignId) {
		String result;
		RoomDesign roomDesign;
		final Shelter shelter;
		final Inventory inventory;
		final Actor actor;

		actor = this.actorService.findActorByPrincipal();
		shelter = this.shelterService.findShelterByPlayer(actor.getId());
		inventory = this.inventoryService.findInventoryByShelter(shelter.getId());

		if (roomDesignId != null && roomDesignId != "" && !roomDesignId.equals("0")) {

			roomDesign = this.roomDesignService.findOne(Integer.parseInt(roomDesignId));
			if (roomDesign.getCostMetal() <= inventory.getMetal() && roomDesign.getCostWood() <= inventory.getWood()) {
				result = roomDesign.getCostWood() + "," + roomDesign.getCostMetal();
				if (roomDesign instanceof Barrack) {
					result += ",0,0,0,0,0," + ((Barrack) roomDesign).getCharacterCapacity() + ",0";
				} else if (roomDesign instanceof RestorationRoom) {
					result += "," + ((RestorationRoom) roomDesign).getHealth() + "," + ((RestorationRoom) roomDesign).getFood() + "," + ((RestorationRoom) roomDesign).getWater() + ",0,0,0,0";
				} else if (roomDesign instanceof ResourceRoom) {
					result += ",0," + ((ResourceRoom) roomDesign).getFood() + "," + ((ResourceRoom) roomDesign).getWater() + "," + ((ResourceRoom) roomDesign).getMetal() + "," + ((ResourceRoom) roomDesign).getWood() + ",0,0";
				} else if (roomDesign instanceof Warehouse) {
					result += ",0,0,0,0,0,0," + ((Warehouse) roomDesign).getItemCapacity();
				}
			} else {
				result = "error";
			}
		} else {
			result = "";
		}
		return result;
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the rooms of a player shelter
	 * 
	 * @param page
	 * 
	 * @return ModelandView
	 * @author Luis
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") final int page, @RequestParam(required = true) final int characterId) {
		ModelAndView result;
		final Page<Room> rooms;
		Shelter shelter;
		Pageable pageable;
		Configuration configuration;
		Player player;
		domain.Character character;

		try {
			result = new ModelAndView("room/list");
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());
			player = (Player) this.actorService.findActorByPrincipal();
			shelter = this.shelterService.findShelterByPlayer(player.getId());
			character = this.characterService.findOne(characterId);
			final int characterRoomID = character.getRoom().getId();
			rooms = this.roomService.findRoomsByShelterMove(shelter.getId(), characterRoomID, pageable);

			result.addObject("rooms", rooms.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", rooms.getTotalPages());
			result.addObject("characterId", characterId);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}
	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("room") Room room, final BindingResult binding) {
		ModelAndView result;
		Room sendedRoom = null;
		try {
			sendedRoom = room;
			room = this.roomService.reconstruct(room, binding);
		} catch (final Throwable oops) {//Not delete
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sendedRoom, "room.params.error");
		} else {
			try {
				Assert.notNull(room.getRoomDesign());
				this.roomService.save(room);
				result = new ModelAndView("redirect:/shelter/player/display.do");

			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Not enough resources")) {
					result = this.createEditModelAndView(room, "room.resources.error");
				} else {
					result = this.createEditModelAndView(room, "room.commit.error");
				}
			}
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Room room) {
		ModelAndView result;

		result = this.createEditModelAndView(room, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Room room, final String message) {
		ModelAndView result;
		final Collection<RoomDesign> roomDesigns;

		result = new ModelAndView("room/edit");
		roomDesigns = this.roomDesignService.findFinalRoomDesign();

		result.addObject("room", room);
		result.addObject("roomDesigns", roomDesigns);
		result.addObject("message", message);

		return result;

	}
}
