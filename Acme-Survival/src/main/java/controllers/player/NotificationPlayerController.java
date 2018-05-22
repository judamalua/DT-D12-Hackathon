
package controllers.player;

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
			result.addObject("requestUri", "notification/player/list.do?");

		} catch (final Throwable oops) {
			if (oops.getMessage().contains("You don't have refuge"))
				result = new ModelAndView("redirect:/refuge/player/create.do");
			else
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
