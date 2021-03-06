
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
import domain.Thread;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ThreadServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private ForumService	forumService;

	@Autowired
	private ThreadService	threadService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test
	public void testSaveThreadPositive() {
		domain.Thread thread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player1"); //The player knows the Shelter

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

	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveThreadNotOwnerNegative() {
		domain.Thread thread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("Player2");

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test");
		thread.setTags(new HashSet<String>());
		thread.setForum(forum);
		thread.setActor(player);
		super.unauthenticate();
		super.authenticate("Player1");
		this.threadService.save(thread);

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

	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test
	public void testDeleteThreadPositive() {
		Thread thread, savedThread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player1"); //The player knows the Shelter

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test name");
		thread.setTags(new HashSet<String>());
		thread.setForum(forum);
		thread.setActor(player);

		savedThread = this.threadService.save(thread);

		this.threadService.delete(savedThread);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteThreadNotLoggedNegative() {
		Thread thread, savedThread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player1"); //The player knows the Shelter

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

	/**
	 * This test checks create a new forum regarding functional requirement number 18.3: An actor who is authenticated as a player must be able to
	 * open and delete threads, write messages in the forum.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteThreadNotOwnerNegative() {
		Thread thread, savedThread;
		Forum forum;
		Player player;
		int forumId;

		super.authenticate("player1"); //The player knows the Shelter

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);
		thread = this.threadService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		thread.setName("Test name");
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
