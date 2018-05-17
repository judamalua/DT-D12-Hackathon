
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.NotificationRepository;
import domain.Attack;
import domain.Gather;
import domain.Notification;
import domain.Player;

@Service
@Transactional
public class NotificationService {

	// Managed repository --------------------------------------------------

	@Autowired
	private NotificationRepository	notificationRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AttackService			attackService;

	@Autowired
	private GatherService			gatherService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods --------------------------------------------------
	public Notification create() {
		Notification result;
		Date now;
		Player player;

		result = new Notification();
		now = new Date();
		player = (Player) this.actorService.findActorByPrincipal();

		result.setMoment(now);
		result.setPlayer(player);

		return result;
	}

	public Collection<Notification> findAll() {
		Collection<Notification> result;

		result = this.notificationRepository.findAll();

		return result;
	}

	public Notification findOne(final int notificationId) {
		Assert.isTrue(notificationId != 0);

		Notification result;

		result = this.notificationRepository.findOne(notificationId);

		return result;
	}

	public Notification save(final Notification notification) {
		Assert.notNull(notification);

		Notification result;
		Player player;
		Date now;

		player = (Player) this.actorService.findActorByPrincipal();

		Assert.isTrue(notification.getPlayer().equals(player));

		if (notification.getId() == 0) {
			now = new Date();

			notification.setMoment(now);
		}

		result = this.notificationRepository.save(notification);

		return result;

	}

	public void delete(final Notification notification) {
		Assert.notNull(notification);
		Assert.isTrue(notification.getId() != 0);

		Player player;

		player = (Player) this.actorService.findActorByPrincipal();

		Assert.isTrue(notification.getPlayer().equals(player));

		this.notificationRepository.delete(notification);
	}

	// Other business rules methods ----------------------------------
	public Notification reconstruct(final Notification notification, final BindingResult binding) {
		Notification result = null;
		Player player;
		Date now;

		if (notification.getId() == 0) {
			result = notification;

			player = (Player) this.actorService.findActorByPrincipal();
			now = new Date();

			result.setMoment(now);
			result.setPlayer(player);
		}
		this.validator.validate(result, binding);

		return result;
	}

	public Page<Notification> findNotificationsByPlayer(final int playerId, final Pageable pageable) {
		Page<Notification> result;

		result = this.notificationRepository.findNotificationsByPlayer(playerId, pageable);

		return result;
	}

	public void flush() {
		this.notificationRepository.flush();
	}

	public void generateNotifications() {
		Attack attack;
		Player player;
		Notification notification;
		Collection<Gather> gathers;
		Map<String, String> title, body;
		Date now;

		player = (Player) this.actorService.findActorByPrincipal();
		attack = this.attackService.findAttackByPlayer(player.getId());
		now = new Date();

		if (attack != null)
			if (attack.getEndMoment().before(now)) {
				notification = this.findNotificationByMission(attack.getId());

				if (notification == null) {
					notification = this.create();
					title = new HashMap<String, String>();
					body = new HashMap<String, String>();

					title.put("es", "elbixo");
					title.put("en", "thebixo");
					body.put("es", "elbody");
					body.put("en", "thebody");

					notification.setBody(body);
					notification.setTitle(title);
					notification.setMission(attack);

					this.save(notification);

					this.flush();
				}

			}

		gathers = this.gatherService.findGathersFinishedByPlayer(player.getId());

		for (final Gather g : gathers) {

		}
	}

	public Notification findNotificationByMission(final int missionId) {
		Notification result;

		result = this.notificationRepository.findNotificationByMission(missionId);

		return result;
	}

}
