
package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.GatherService;
import services.NotificationService;
import controllers.AbstractController;
import domain.Attack;
import domain.Configuration;
import domain.Notification;
import domain.Player;

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


	// Constructors -----------------------------------------------------------

	public NotificationPlayerController() {
		super();
	}

	//List --------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "0") final int page) {
		ModelAndView result;
		Player player;
		Configuration configuration;
		Pageable pageable;
		Page<Notification> notifications;

		try {
			configuration = this.configurationService.findConfiguration();
			pageable = new PageRequest(page, configuration.getPageSize());

			result = new ModelAndView("notification/list");

			player = (Player) this.actorService.findActorByPrincipal();
			this.notificationService.generateNotifications();
			notifications = this.notificationService.findNotificationsByPlayer(player.getId(), pageable);

			this.gatherService.updateGatheringMissions();

			result.addObject("notifications", notifications.getContent());
			result.addObject("page", page);
			result.addObject("pageNum", notifications.getTotalPages());
			result.addObject("requestUri", "notification/player/list.do?");

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

		try {
			notification = this.notificationService.findOne(notificationId);
			result = new ModelAndView("notification/display");

			result.addObject("notification", notification);

			if (notification.getMission() != null)
				if (notification.getMission() instanceof Attack)
					result.addObject("attackId", notification.getMission().getId());
				else
					result.addObject("gatherId", notification.getMission().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

}
