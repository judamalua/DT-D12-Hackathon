
package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AttackService;
import services.CharacterService;
import services.EventService;
import services.ItemDesignService;
import services.LocationService;
import services.OrderService;
import services.PlayerService;
import services.RoomService;
import services.ThreadService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/admin")
public class DashboardAdminController extends AbstractController {

	// Services -------------------------------------------------------

	@Autowired
	private PlayerService		playerService;
	@Autowired
	private CharacterService	characterService;
	@Autowired
	private LocationService		locationService;
	@Autowired
	private ItemDesignService	itemDesignService;
	@Autowired
	private ItemDesignService	roomDesignService;
	@Autowired
	private EventService		eventService;
	@Autowired
	private ThreadService		threadService;
	@Autowired
	private OrderService		orderService;
	@Autowired
	private AttackService		attackService;
	@Autowired
	private RoomService			roomService;


	// Listing ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView result;
		final String numPlayers, numCharacters, numLocations, numItemDesigns, numRoomDesigns, numEvents;
		final Collection<String> threadsByActor, ordersByPlayer, attacksByRefuge, defensesByRefuge, roomsPerRefuge;

		numPlayers = this.playerService.findNumPlayers();
		numCharacters = this.characterService.findNumCharacters();
		numLocations = this.locationService.findNumLocations();
		numItemDesigns = this.itemDesignService.findNumItemDesigns();
		numEvents = this.eventService.findNumEvents();
		numRoomDesigns = this.roomDesignService.findNumItemDesigns();
		threadsByActor = this.threadService.findNumThreadsByActor();
		ordersByPlayer = this.orderService.findNumOrdersByActor();
		attacksByRefuge = this.attackService.findNumAttacksByRefuge();
		defensesByRefuge = this.attackService.findNumDefensesByRefuge();
		roomsPerRefuge = this.roomService.findNumRoomsByRefuge();

		result = new ModelAndView("dashboard/list");

		result.addObject("numPlayers", numPlayers);
		result.addObject("numCharacters", numCharacters);
		result.addObject("numLocations", numLocations);
		result.addObject("numItemDesigns", numItemDesigns);
		result.addObject("numEvents", numEvents);
		result.addObject("numRoomDesigns", numRoomDesigns);
		result.addObject("threadsByActor", threadsByActor);
		result.addObject("ordersByPlayer", ordersByPlayer);
		result.addObject("attacksByRefuge", attacksByRefuge);
		result.addObject("defensesByRefuge", defensesByRefuge);
		result.addObject("roomsPerRefuge", roomsPerRefuge);

		return result;
	}
}
