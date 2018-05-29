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
import services.RefugeService;
import services.RoomService;
import controllers.AbstractController;
import domain.Actor;
import domain.Configuration;
import domain.Inventory;
import domain.Player;
import domain.Refuge;
import domain.RestorationRoom;
import domain.Room;

@Controller
@RequestMapping("/refuge/player")
public class RefugePlayerController extends AbstractController {

	@Autowired
	private RefugeService			refugeService;

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

	public RefugePlayerController() {
		super();
	}

	// Listing  ---------------------------------------------------------------		

	/**
	 * That method returns a model and view with the refuge display
	 * 
	 * @param pageRoom
	 * @param refugeId
	 *            (Optional)
	 * 
	 * @return ModelandView
	 * @author MJ
	 */
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int pageRoom) {
		ModelAndView result;
		final Refuge refuge, ownRefuge;
		Configuration configuration;
		Actor actor;
		Pageable pageable;
		Page<Room> rooms;
		boolean owner = false;
		boolean knowRefuge = false;
		Inventory inventory;
		final Collection<domain.Character> characters, charactersUpdated;
		Integer capacity;
		long time;
		try {
			time = System.currentTimeMillis();
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(pageRoom, configuration.getPageSize());

			result = new ModelAndView("refuge/display");

			actor = this.actorService.findActorByPrincipal();

			Assert.isTrue((actor instanceof Player));

			ownRefuge = this.refugeService.findRefugeByPlayer(actor.getId());

			refuge = ownRefuge;
			Assert.notNull(refuge, "Not have refuge");

			characters = this.characterService.findCharactersByRefuge(refuge.getId());
			charactersUpdated = new HashSet<>();
			for (final domain.Character character : characters) {
				if (character.getRoom() != null && (character.getRoom().getRoomDesign() instanceof RestorationRoom)) {
					charactersUpdated.add(this.characterService.updateStats(character));
				} else {
					charactersUpdated.add(character);
				}
			}

			inventory = this.refugeService.updateInventory(refuge);
			this.gatherService.updateGatheringMissions();

			knowRefuge = ((Player) actor).getRefuges().contains(refuge);
			owner = ownRefuge != null && ownRefuge.equals(refuge);
			rooms = this.roomService.findRoomsByRefuge(refuge.getId(), pageable);

			capacity = this.refugeService.getCurrentCharacterCapacity(ownRefuge);
			//Assert.isTrue(refuge.equals(ownRefuge) || player.getRefuges().contains(refuge));

			result.addObject("refuge", refuge);
			result.addObject("rooms", rooms.getContent());
			result.addObject("pageRoom", pageRoom);
			result.addObject("pageNumRoom", rooms.getTotalPages());
			result.addObject("knowRefuge", knowRefuge);
			result.addObject("characters", charactersUpdated);
			result.addObject("inventory", inventory);
			result.addObject("owner", owner);
			result.addObject("characterCapacity", capacity);

			System.out.println(System.currentTimeMillis() - time);
		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have refuge")) {
				result = new ModelAndView("redirect:/refuge/player/create.do");
			} else {
				result = new ModelAndView("redirect:/misc/403");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Refuge ownRefuge;
		Player actor;

		try {
			actor = (Player) this.actorService.findActorByPrincipal();
			ownRefuge = this.refugeService.findRefugeByPlayer(actor.getId());
			Assert.notNull(ownRefuge, "Not have refuge");
			result = this.createEditModelAndView(ownRefuge);

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Not have refuge")) {
				result = new ModelAndView("redirect:/refuge/player/create.do");
			} else {
				result = new ModelAndView("redirect:/misc/403");
			}
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Refuge refuge;
		Actor actor;

		try {
			actor = this.actorService.findActorByPrincipal();
			Assert.isTrue(actor instanceof Player);

			refuge = this.refugeService.create();

			result = this.createEditModelAndView(refuge);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:misc/403");
		}
		return result;
	}

	//Updating forum ---------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("refuge") Refuge refuge, final BindingResult binding) {
		ModelAndView result;
		Refuge sendedRefuge = null;
		Player player;
		Refuge ownRefuge;

		try {
			sendedRefuge = refuge;
			refuge = this.refugeService.reconstruct(refuge, binding);
		} catch (final Throwable oops) {//Not delete
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sendedRefuge, "refuge.params.error");
		} else {
			try {
				player = (Player) this.actorService.findActorByPrincipal();
				ownRefuge = this.refugeService.findRefugeByPlayer(player.getId());
				if (refuge.getId() != 0) {
					Assert.isTrue(ownRefuge.equals(refuge));
				} else {
					Assert.isTrue(ownRefuge == null, "Not have refuge");
				}
				this.refugeService.save(refuge);
				result = new ModelAndView("redirect:/refuge/player/display.do");

			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(refuge, "refuge.name.error");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Not have refuge")) {
					result = new ModelAndView("redirect:/refuge/player/create.do");
				} else {
					result = this.createEditModelAndView(refuge, "refuge.commit.error");
				}
			}
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Refuge refuge) {
		ModelAndView result;

		result = this.createEditModelAndView(refuge, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Refuge refuge, final String message) {
		ModelAndView result;

		result = new ModelAndView("refuge/edit");
		result.addObject("refuge", refuge);
		result.addObject("message", message);

		return result;

	}

}
