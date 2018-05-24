
package controllers.player;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.GatherService;
import services.NotificationService;
import services.RefugeService;
import controllers.AbstractController;
import domain.Attack;
import domain.Configuration;
import domain.Notification;
import domain.Player;
import domain.Refuge;

@Controller
@RequestMapping("/notification/player")
public class NotificationPlayerController extends AbstractController {

	@Autowired
	private NotificationService		notificationService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private GatherService			gatherService;

	@Autowired
	private RefugeService			refugeService;


	// Constructors -----------------------------------------------------------

	public NotificationPlayerController() {
		super();
	}

	//List --------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Player player;
		final Configuration configuration;
		Pageable pageable;
		Page<Notification> notifications;
		Refuge refuge;

		try {
			player = (Player) this.actorService.findActorByPrincipal();

			refuge = this.refugeService.findRefugeByPlayer(player.getId());

			Assert.notNull(refuge, "You don't have refuge");

			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());

			result = new ModelAndView("notification/list");

			this.gatherService.updateGatheringMissions();

			this.notificationService.generateNotifications();
			notifications = this.notificationService.findNotificationsByPlayer(player.getId(), pageable);

			result.addObject("notifications", notifications.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", notifications.getTotalPages());
			result.addObject("requestUri", "notification/player/list.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("You don't have refuge"))
				result = new ModelAndView("redirect:/refuge/player/create.do");
			else
				result = new ModelAndView("redirect:/misc/403");
		}

		return result;

	}

	//Display ------------------------------------
	@RequestMapping(value = "/displayGatherNotification", method = RequestMethod.GET)
	public ModelAndView displayGatherNotification(final int notificationId) {
		ModelAndView result;
		Notification notification;

		try {
			notification = this.notificationService.findOne(notificationId);
			result = new ModelAndView("notification/displayGatherNotification");

			result.addObject("notification", notification);

			if (notification.getGather() != null)
				result.addObject("gatherId", notification.getGather().getId());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}
	//Display ------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int notificationId) {
		ModelAndView result;
		Notification notification;
		String bodyEn, titleEn;
		ArrayList<String> resources;

		try {
			notification = this.notificationService.findOne(notificationId);
			result = new ModelAndView("notification/display");
			bodyEn = notification.getBody().get("en");
			titleEn = notification.getTitle().get("en");

			if (titleEn.equals("The attacker managed to steal resources")) {
				resources = this.splitBodyNotification(bodyEn);

				result.addObject("notificationMessage", resources.get(0));
				result.addObject("notificationWater", resources.get(1));
				result.addObject("notificationFood", resources.get(2));
				result.addObject("notificationMetal", resources.get(3));
				result.addObject("notificationWood", resources.get(4));

				result.addObject("attackWon", true);
			} else
				result.addObject("attackWon", false);

			result.addObject("notification", notification);

			if (notification.getAttack() != null)
				if (notification.getAttack() instanceof Attack)
					result.addObject("attackId", notification.getAttack().getId());
				else
					result.addObject("gatherId", notification.getGather().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	//Delete -----------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int notificationId) {
		ModelAndView result;
		Notification notification;

		try {
			notification = this.notificationService.findOne(notificationId);

			this.notificationService.delete(notification);

			result = new ModelAndView("redirect:/notification/player/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;

	}

	//Ancilliary methods ------------------------------------------------

	public ArrayList<String> splitBodyNotification(final String body) {
		ArrayList<String> result;

		result = new ArrayList<String>();

		result.add(body.split(",")[0]); //Message
		result.add(body.split(",")[1]); //Water
		result.add(body.split(",")[2]); //Food
		result.add(body.split(",")[3]); //Metal
		result.add(body.split(",")[4]); //Wood

		return result;
	}
}
