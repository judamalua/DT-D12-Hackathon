
package services;

import java.util.ArrayList;
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
import domain.Event;
import domain.ItemDesign;
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
		now = new Date(System.currentTimeMillis() - 1000);
		player = (Player) this.actorService.findActorByPrincipal();

		result.setMoment(now);
		result.setCharacterId(null);
		result.setFoundRefuge(false);
		result.setPlayer(player);
		result.setEvents(new ArrayList<Event>());
		result.setItemDesigns(new ArrayList<ItemDesign>());

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
			now = new Date(System.currentTimeMillis() - 100);

			notification.setMoment(now);
		}

		result = this.notificationRepository.save(notification);

		return result;

	}

	public Notification saveToDefendant(final Notification notification) {
		Assert.notNull(notification);

		Notification result;
		Date now;

		if (notification.getId() == 0) {
			now = new Date(System.currentTimeMillis() - 100);

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
			now = new Date(System.currentTimeMillis() - 100);

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
		final Attack attack;
		Player player;
		final Notification notification;
		final Map<String, String> title, body;
		Date now;

		player = (Player) this.actorService.findActorByPrincipal();
		//attack = this.attackService.findAttackByPlayer(player.getId());
		now = new Date();

		if (attack != null)
			if (attack.getEndMoment().before(now)) {
				notification = this.findNotificationByMission(attack.getId());

		//}
		//}

					notification.setBody(body);
					notification.setTitle(title);
					notification.setMission(attack);

					this.save(notification);

				}

			}

	}

	public Map<String, String> generetateTitleMapResultByAttack(final Attack attack) {
		Map<String, String> result;
		Integer resources;
		String titleEs, titleEn;

		result = new HashMap<String, String>();
		resources = this.attackService.getResourcesOfAttack(attack);

		if (resources <= 0) {
			titleEs = "El defensor resisti� el ataque";
			titleEn = "The defendant resisted the attack";
		} else {
			titleEs = "El atacante consigui� robar recursos";
			titleEn = "The attacker managed to steal resources";
		}

		result.put("es", titleEs);
		result.put("en", titleEn);

		return result;

	}

	public Map<String, String> generetateMapBodyResultByAttack(final Attack attack) {
		Map<String, String> result;
		Integer resources;
		Integer waterStolen, foodStolen, metalStolen, woodStolen;
		ArrayList<Integer> resourcesStolen;
		String bodyEs, bodyEn;

		result = new HashMap<String, String>();
		resources = this.attackService.getResourcesOfAttack(attack);

		if (resources <= 0) {
			bodyEs = "El atacante no condigui� robar ning�n recurso.";
			bodyEn = "The attacker didn't steal any resources.";
		} else {
			resourcesStolen = this.attackService.calculateResourcesToSteal(attack, resources);
			waterStolen = resourcesStolen.get(0);
			foodStolen = resourcesStolen.get(1);
			metalStolen = resourcesStolen.get(2);
			woodStolen = resourcesStolen.get(3);
			bodyEs = "El atacante consigui� robar:," + waterStolen + "," + foodStolen + "," + metalStolen + "," + woodStolen;

			bodyEn = "The attacker could steal:," + waterStolen + "," + foodStolen + "," + metalStolen + "," + woodStolen;
		}

		result.put("es", bodyEs);
		result.put("en", bodyEn);

		return result;

	}

	public Notification findNotificationByMission(final int missionId) {
		Notification result;

		result = this.notificationRepository.findNotificationByMission(missionId);

		return result;
	}

}
