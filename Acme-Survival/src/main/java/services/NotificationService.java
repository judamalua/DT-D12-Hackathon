
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.NotificationRepository;
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

		now = new Date();

		notification.setMoment(now);

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

}
