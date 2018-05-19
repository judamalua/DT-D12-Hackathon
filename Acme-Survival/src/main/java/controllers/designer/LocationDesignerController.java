
package controllers.designer;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.LocationService;
import controllers.AbstractController;
import domain.Location;

@Controller
@RequestMapping("/location/designer")
public class LocationDesignerController extends AbstractController {

	@Autowired
	private LocationService			locationService;

	@Autowired
	private ConfigurationService	configurationService;


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
	//		  "locationsNotFinal": [
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
	//	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public @ResponseBody
	String info() {
		String result = "";
		try {
			final JSONObject json = new JSONObject();
			final JSONArray locations = this.getLocations();
			json.put("locations", locations);
			final JSONArray locationsNotFinal = this.getLocationsNotFinal();
			json.put("locationsNotFinal", locationsNotFinal);
			result = json.toString();
		} catch (final Throwable e) {
		}
		return result;
	}

	//get Methods ----------------------------------

	private JSONArray getLocations() {
		final JSONArray result = new JSONArray();
		final Collection<Location> locations = this.locationService.findAllLocationsByFinal();
		for (final Location location : locations) {
			final JSONObject jsonLocation = this.makeLocation(location);
			result.put(jsonLocation);
		}
		return result;
	}

	private JSONArray getLocationsNotFinal() {
		final JSONArray result = new JSONArray();
		final Collection<Location> locations = this.locationService.findAllLocationsByNotFinal();
		for (final Location location : locations) {
			final JSONObject jsonLocation = this.makeLocation(location);
			result.put(jsonLocation);
		}
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
		for (final String language : location.getName().keySet())
			name.put(language, location.getName().get(language));
		jsonLocation.put("name", name);
		return jsonLocation;
	}
}
