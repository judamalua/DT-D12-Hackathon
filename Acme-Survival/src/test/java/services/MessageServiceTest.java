
package services;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Forum;
import domain.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private ForumService	forumService;

	@Autowired
	private ThreadService	threadService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks that the Player can Attack a Refuge that he already knows.
	 */
	@Test
	public void testSaveThreadPositive() {
		domain.Thread thread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player1"); //The player knows the Refuge

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test");
		thread.setTags(new HashSet<String>());
		thread.setForum(forum);
		thread.setActor(player);

		this.threadService.save(thread);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveThreadNotOwnerNegative() {
		domain.Thread thread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player2"); //The player knows the Refuge

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test");
		thread.setTags(new HashSet<String>());
		thread.setForum(forum);
		thread.setActor(player);

		this.threadService.save(thread);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveThreadNotAuthenticatedNegative() {
		domain.Thread thread;
		Forum forum;
		Player player;
		int forumId;

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test");
		thread.setTags(new HashSet<String>());
		thread.setForum(forum);
		thread.setActor(player);

		this.threadService.save(thread);

	}

	@Test
	public void testDeleteThreadPositive() {
		domain.Thread thread, savedThread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player1"); //The player knows the Refuge

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test");
		thread.setTags(new HashSet<String>());
		thread.setForum(forum);
		thread.setActor(player);

		savedThread = this.threadService.save(thread);

		this.threadService.delete(savedThread);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteThreadNotLoggedNegative() {
		domain.Thread thread, savedThread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player1"); //The player knows the Refuge

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test");
		thread.setTags(new HashSet<String>());
		thread.setForum(forum);
		thread.setActor(player);

		savedThread = this.threadService.save(thread);
		super.unauthenticate();

		this.threadService.delete(savedThread);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteThreadNotOwnerNegative() {
		domain.Thread thread, savedThread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player1"); //The player knows the Refuge

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test");
		thread.setTags(new HashSet<String>());
		thread.setForum(forum);
		thread.setActor(player);

		savedThread = this.threadService.save(thread);
		super.unauthenticate();

		super.authenticate("Player2");

		this.threadService.delete(savedThread);

		super.unauthenticate();

	}

}
