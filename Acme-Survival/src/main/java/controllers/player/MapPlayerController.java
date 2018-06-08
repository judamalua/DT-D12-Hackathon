
package controllers.player;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AttackService;
import services.ConfigurationService;
import services.GatherService;
import services.LocationService;
import services.MoveService;
import services.ShelterService;
import domain.Actor;
import domain.Attack;
import domain.Gather;
import domain.Location;
import domain.Move;
import domain.Player;
import domain.Shelter;

@Controller
@RequestMapping("/map/player")
public class MapPlayerController {

	// Services -------------------------------------------------------
	@Autowired
	private LocationService			locationService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ShelterService			shelterService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AttackService			attackService;

	@Autowired
	private GatherService			gatherService;

	@Autowired
	private MoveService				moveService;


	// Map display -----------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		try {
			result = new ModelAndView("map/display");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	//Information retrival---------------------------------------
	//JSON example:

	//	{
	//		  "shelter": {
	//		    "gpsCoordinates": "37.3891,-5.9845",
	//		    "name": "Refugio 1",
	//		    "playerName": "pepito",
	//		    "id": "12345"
	//		  },
	//		  "knownShelters": [
	//		    {
	//		      "gpsCoordinates": "37.3891,-5.9845",
	//		      "name": "Refugio 1",
	//		      "playerName": "pepito",
	//		      "id": "12345"
	//		    },
	//		    {
	//		      "gpsCoordinates": "37.3891,-5.9845",
	//		      "name": "Refugio 1",
	//		      "playerName": "pepito",
	//		      "id": "12345"
	//		    }
	//		  ],
	//		  "locations": [
	//		    {
	//		      "point_a": "37.3891,-5.9845",
	//		      "point_b": "37.3891,-5.9845",
	//		      "point_c": "37.3891,-5.9845",
	//		      "point_d": "37.3891,-5.9845",
	//		      "name": {
	//		        "es": "sitio1",
	//		        "en": "place1"
	//		      },
	//		      "id": "1234"
	//		    },
	//		    {
	//		      "point_a": "37.3891,-5.9845",
	//		      "point_b": "37.3891,-5.9845",
	//		      "point_c": "37.3891,-5.9845",
	//		      "point_d": "37.3891,-5.9845",
	//		      "name": {
	//		        "es": "sitio2",
	//		        "en": "place2"
	//		      },
	//		      "id": "1234"
	//		    }
	//		  ],
	//		  "onGoingGathers": [
	//		    {
	//		      "location": {
	//		        "point_a": "37.3891,-5.9845",
	//		        "point_b": "37.3891,-5.9845",
	//		        "point_c": "37.3891,-5.9845",
	//		        "point_d": "37.3891,-5.9845",
	//		        "name": {
	//		          "es": "sitio1",
	//		          "en": "place1"
	//		        },
	//		        "id": "1234"
	//		      },
	//		      "character": {
	//		        "name": "Pepe",
	//		        "surname": "Palotes",
	//				"genre":"Male",
	//		      },
	//		      "endMoment": 123424323423
	//		    }
	//		  ],
	//		  "onGoingAttack": {
	//		    "shelter": {
	//		      "gpsCoordinates": "37.3891,-5.9845",
	//		      "name": "Refugio 1",
	//		      "playerName": "pepito",
	//		      "id": "12345"
	//		    },
	//		    "endMoment": 123424323423
	//		  }
	//		}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public @ResponseBody
	String info() {
		String result = "";
		try {
			final JSONObject json = new JSONObject();
			final JSONObject shelter = this.getShelter();
			json.put("shelter", shelter);
			final JSONArray knownShelters = this.getKnownShelters();
			json.put("knownShelters", knownShelters);
			final JSONArray locations = this.getLocations();
			json.put("locations", locations);
			final JSONArray onGoingGathers = this.getOnGoingGathers();
			json.put("onGoingGathers", onGoingGathers);
			final JSONObject onGoingAttack = this.getOnGoingAttack();
			json.put("onGoingAttack", onGoingAttack);
			final JSONObject onGoingMove = this.getOnGoingMove();
			json.put("onGoingMove", onGoingMove);
			result = json.toString();
		} catch (final Throwable e) {
			result = e.getMessage();
		}
		return result;
	}
	// get Methods ----------------------------------

	private JSONObject getOnGoingMove() {
		final JSONObject result = new JSONObject();
		final Move move = this.moveService.findCurrentMoveByShelter(this.shelterService.findShelterByPlayer(this.actorService.findActorByPrincipal().getId()).getId());
		if (move != null) {
			result.put("endMoment", move.getEndDate().getTime() - new Date().getTime());
			result.put("location", this.makeLocation(move.getLocation()));
		}
		return result;
	}
	private JSONObject getOnGoingAttack() {
		final JSONObject result = new JSONObject();
		final Attack attack = this.attackService.findAttackByPlayer(this.actorService.findActorByPrincipal().getId());
		if (attack != null) {
			result.put("endMoment", attack.getEndMoment().getTime() - new Date().getTime());
			result.put("shelter", this.makeShelter(attack.getDefendant()));
		}
		return result;
	}
	private JSONArray getOnGoingGathers() {
		final JSONArray result = new JSONArray();
		final Collection<Gather> gathers = this.gatherService.findAllGathersOfPlayer(this.actorService.findActorByPrincipal().getId());
		for (final Gather gather : gathers) {
			final JSONObject jsonGather = new JSONObject();
			final JSONObject jsonCharacter = new JSONObject();
			jsonCharacter.put("fullName", gather.getCharacter().getFullName());
			jsonCharacter.put("isMale", gather.getCharacter().getMale());
			jsonCharacter.put("id", gather.getCharacter().getId());
			jsonGather.put("character", jsonCharacter);
			jsonGather.put("location", this.makeLocation(gather.getLocation()));
			jsonGather.put("endMoment", gather.getEndMoment().getTime() - new Date().getTime());
			result.put(jsonGather);
		}
		return result;
	}
	private JSONArray getLocations() {
		final JSONArray result = new JSONArray();
		final Collection<Location> locations = this.locationService.findAllLocationsByFinal();
		for (final Location location : locations) {
			final JSONObject jsonLocation = this.makeLocation(location);
			result.put(jsonLocation);
		}
		return result;
	}
	private JSONArray getKnownShelters() {
		final Player player = (Player) this.actorService.findActorByPrincipal();
		final JSONArray result = new JSONArray();
		for (final Shelter shelter : player.getShelters()) {
			final JSONObject jsonShelter = this.makeShelter(shelter);
			result.put(jsonShelter);
		}
		return result;
	}

	private JSONObject getShelter() {
		final Actor actor = this.actorService.findActorByPrincipal();
		final Shelter shelter = this.shelterService.findShelterByPlayer(actor.getId());
		final JSONObject result = this.makeShelter(shelter);
		return result;
	}
	// Make functions -------------------------------
	private JSONObject makeLocation(final Location location) {
		final JSONObject jsonLocation = new JSONObject();
		jsonLocation.put("id", location.getId());
		jsonLocation.put("point_a", location.getPoint_a());
		jsonLocation.put("point_b", location.getPoint_b());
		jsonLocation.put("point_c", location.getPoint_c());
		jsonLocation.put("point_d", location.getPoint_d());
		final JSONObject name = new JSONObject();
		for (final String language : location.getName().keySet()) {
			name.put(language, location.getName().get(language));
		}
		jsonLocation.put("name", name);
		return jsonLocation;
	}
	private JSONObject makeShelter(final Shelter shelter) {
		final JSONObject result = new JSONObject();
		result.put("id", shelter.getId());
		result.put("name", shelter.getName());
		result.put("gpsCoordinates", shelter.getGpsCoordinates());
		result.put("playerName", shelter.getPlayer().getName() + " " + shelter.getPlayer().getSurname());

		return result;
	}

}
