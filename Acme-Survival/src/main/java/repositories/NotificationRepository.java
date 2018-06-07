
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	@Query("select n from Notification n where n.player.id = ?1")
	Page<Notification> findNotificationsByPlayer(int playerId, Pageable pageable);

	@Query("select count(n) from Notification n where n.player.id = ?1")
	String findNumberNotifications(int playerId);

	@Query("select n from Notification n where n.attack.id = ?1")
	Notification findNotificationByAttack(int attackId);

}
