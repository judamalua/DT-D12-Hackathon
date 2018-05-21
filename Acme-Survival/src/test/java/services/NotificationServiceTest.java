
package services;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Notification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class NotificationServiceTest extends AbstractTest {

	@Autowired
	private NotificationService	notificationService;


	@Test
	public void createNotificationPositive() {
		Notification notification;
		Map<String, String> body, title;

		super.authenticate("player1");
		notification = this.notificationService.create();
		body = new HashMap<String, String>();
		title = new HashMap<String, String>();

		body.put("es", "valor body");
		body.put("en", "body value");
		title.put("en", "title value");
		title.put("es", "valor titulo");

		notification.setBody(body);
		notification.setTitle(title);

		this.notificationService.save(notification);

		this.notificationService.flush();

		super.unauthenticate();

	}

	@Test(expected = ClassCastException.class)
	public void createNotificationNegative() {
		Notification notification;
		Map<String, String> body, title;

		super.authenticate("admin");
		notification = this.notificationService.create();
		body = new HashMap<String, String>();
		title = new HashMap<String, String>();

		body.put("es", "valor body");
		body.put("en", "body value");
		title.put("en", "title value");
		title.put("es", "valor titulo");

		notification.setBody(body);
		notification.setTitle(title);

		this.notificationService.save(notification);

		this.notificationService.flush();

		super.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void createAndSaveNotificationNegative() {
		Notification notification;
		Map<String, String> body, title, newBody;

		super.authenticate("player1");
		notification = this.notificationService.create();
		body = new HashMap<String, String>();
		title = new HashMap<String, String>();
		newBody = new HashMap<String, String>();

		body.put("es", "valor body");
		body.put("en", "body value");
		title.put("en", "title value");
		title.put("es", "valor titulo");
		newBody.put("es", "nuevo cuerpo");
		newBody.put("en", "new body");

		notification.setBody(body);
		notification.setTitle(title);

		notification = this.notificationService.save(notification);

		this.notificationService.flush();

		super.unauthenticate();

		super.authenticate("player2");

		notification.setBody(newBody);

		this.notificationService.save(notification);

		super.unauthenticate();

		this.notificationService.flush();

	}

}
