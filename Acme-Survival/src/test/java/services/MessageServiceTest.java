
package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Actor;
import domain.Player;
import domain.Thread;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private ThreadService	threadService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test
	public void testSaveMessagePositive() {
		domain.Message message;
		Thread thread;
		Player player;
		int threadId;

		super.authenticate("player1"); //The player knows the Shelter

		threadId = super.getEntityId("Thread1");
		thread = this.threadService.findOne(threadId);
		message = this.messageService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		message.setActor(player);
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setText("Test text");
		message.setThread(thread);

		this.messageService.save(message);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveMessageNotOwnerNegative() {
		domain.Message message;
		Thread thread;
		Player player;
		int threadId;

		super.authenticate("player2");

		threadId = super.getEntityId("Thread1");
		thread = this.threadService.findOne(threadId);
		message = this.messageService.create();
		player = (Player) this.actorService.findActorByPrincipal();
		super.unauthenticate();
		super.authenticate("Player1");
		message.setActor(player);
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setText("Test text");
		message.setThread(thread);

		this.messageService.save(message);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveThreadNotAuthenticatedNegative() {
		domain.Message message;
		Thread thread;
		Actor player;
		int threadId;

		super.unauthenticate();
		threadId = super.getEntityId("Thread1");
		thread = this.threadService.findOne(threadId);
		message = this.messageService.create();
		player = this.actorService.findActorByPrincipal();

		message.setActor(player);
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setText("Test text");
		message.setThread(thread);

		this.messageService.save(message);

	}

	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSaveBlankMessageNegative() {
		domain.Message message;
		Thread thread;
		Player player;
		int threadId;

		super.authenticate("player1");
		threadId = super.getEntityId("Thread1");
		thread = this.threadService.findOne(threadId);
		message = this.messageService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		message.setActor(player);
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setText("");
		message.setThread(thread);

		this.messageService.save(message);
		this.messageService.flush();
		super.unauthenticate();

	}

}
