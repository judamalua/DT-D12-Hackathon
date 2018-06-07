
package controllers.designer;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.LocationService;
import services.LootTableService;
import controllers.AbstractController;
import domain.Configuration;
import domain.Location;
import domain.LootTable;

@Controller
@RequestMapping("/location/designer")
public class LocationDesignerController extends AbstractController {

	@Autowired
	private LocationService			locationService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private LootTableService		lootTableService;


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

	// Creating ---------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		try {
			final Location location = this.locationService.create();
			result = this.createEditModelAndView(location);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Editing ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int locationId) {
		ModelAndView result;
		final Location location;

		try {
			location = this.locationService.findOne(locationId);
			Assert.notNull(location);
			result = this.createEditModelAndView(location);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	// Saving -------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("location") Location location, final BindingResult binding) {
		ModelAndView result;

		try {
			location = this.locationService.reconstruct(location, binding);
		} catch (final Throwable oops) {
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(location, "location.params.error");
		} else {
			try {
				this.locationService.save(location);
				result = new ModelAndView("redirect:/location/designer/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(location, "location.commit.error");
			}
		}

		return result;
	}

	// Saving Final-------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@ModelAttribute("location") Location location, final BindingResult binding) {
		ModelAndView result;

		try {
			location.setFinalMode(true);
			location = this.locationService.reconstruct(location, binding);
		} catch (final Throwable oops) {
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(location, "location.params.error");
		} else {
			try {
				this.locationService.save(location);
				result = new ModelAndView("redirect:/location/designer/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(location, "location.commit.error");
			}
		}

		return result;
	}

	// Delete -------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("location") final Location location, final BindingResult binding) {
		ModelAndView result;
		final Location locationFinal;

		try {
			Assert.notNull(location);
			Assert.isTrue(location.getId() != 0);
			locationFinal = this.locationService.findOne(location.getId());
			this.locationService.delete(locationFinal);
			result = new ModelAndView("redirect:/location/designer/display.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Location location) {
		ModelAndView result;

		result = this.createEditModelAndView(location, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Location location, final String message) {
		ModelAndView result;
		Configuration configuration;
		Collection<LootTable> lootTables;

		configuration = this.configurationService.findConfiguration();
		lootTables = this.lootTableService.findAllFinal();

		result = new ModelAndView("location/edit");
		result.addObject("location", location);
		result.addObject("message", message);
		result.addObject("languages", configuration.getLanguages());
		result.addObject("lootTables", lootTables);

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
		for (final String language : location.getName().keySet()) {
			name.put(language, location.getName().get(language));
		}
		jsonLocation.put("name", name);
		return jsonLocation;
	}

}
