
package controllers.player;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.LocationService;
import services.RefugeService;
import domain.Actor;
import domain.Location;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/map/player")
public class MapPlayerController {

	// Services -------------------------------------------------------
	@Autowired
	private LocationService			locationService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private RefugeService			refugeService;

	@Autowired
	private ActorService			actorService;


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
	//I'm scared mommy
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public @ResponseBody
	String ajax(@RequestParam final String cookieToken) {
		String result = "";
		try {
			final JSONObject json = new JSONObject();
			final JSONObject refuge = this.getRefuge();
			json.append("refuge", refuge);
			final JSONArray knownRefuges = this.getKnownRefuges();
			json.append("knownRefuges", knownRefuges);
			final JSONArray locations = this.getLocations();
			json.append("locations", locations);
			final JSONArray onGoingRecolections = this.getOnGoingRecolections();
			json.append("onGoingRecolections", onGoingRecolections);
			final JSONObject onGoingAttack = this.getOnGoingAttack();
			json.append("onGoingAttack", onGoingAttack);
			final JSONArray languages = this.getLanguages();
			json.append("languages", languages);
			result = json.toString();
		} catch (final Throwable e) {
		}
		return result;
	}

	// get Methods ----------------------------------
	private JSONArray getLanguages() {
		final JSONArray result = new JSONArray(this.configurationService.findConfiguration().getLanguages());
		return result;
	}

	private JSONObject getOnGoingAttack() {
		final JSONObject result = new JSONObject();
		// TODO Finish this
		return result;
	}

	private JSONArray getOnGoingRecolections() {
		final JSONArray result = new JSONArray();
		// TODO Finish this too
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
	private JSONArray getKnownRefuges() {
		final Player player = (Player) this.actorService.findActorByPrincipal();
		final JSONArray result = new JSONArray();
		for (final Refuge refuge : player.getRefuges()) {
			final JSONObject jsonRefuge = this.makeRefuge(refuge);
			result.put(jsonRefuge);
		}
		return result;
	}

	private JSONObject getRefuge() {
		final Actor actor = this.actorService.findActorByPrincipal();
		final Refuge refuge = this.refugeService.findRefugeByPlayer(actor.getId());
		final JSONObject result = this.makeRefuge(refuge);
		return result;
	}
	// Make functions -------------------------------
	private JSONObject makeLocation(final Location location) {
		final JSONObject jsonLocation = new JSONObject();
		jsonLocation.append("id", location.getId());
		jsonLocation.append("point_a", location.getPoint_a());
		jsonLocation.append("point_b", location.getPoint_b());
		jsonLocation.append("point_c", location.getPoint_c());
		jsonLocation.append("point_d", location.getPoint_d());
		final JSONObject name = new JSONObject();
		for (final String language : location.getName().keySet())
			name.append(language, location.getName().get(language));
		jsonLocation.append("name", name);
		return jsonLocation;
	}
	private JSONObject makeRefuge(final Refuge refuge) {
		final JSONObject result = new JSONObject();
		result.append("id", refuge.getId());
		result.append("name", refuge.getName());
		result.append("gpsCoordinates", refuge.getGpsCoordinates());
		result.append("playerName", refuge.getPlayer().getName() + " " + refuge.getPlayer().getSurname());

		return result;
	}

}
