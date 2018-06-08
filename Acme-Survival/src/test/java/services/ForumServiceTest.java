
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Forum;
import domain.Moderator;
import domain.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ForumServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private ForumService	forumService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test
	public void testSaveForumPositive() {
		Forum forum;
		Moderator moderator;

		super.authenticate("moderator1");

		forum = this.forumService.create();
		moderator = (Moderator) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(moderator);
		forum.setSupport(false);
		forum.setStaff(false);

		this.forumService.save(forum);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumNotLoggedPositive() {
		Forum forum;
		Moderator player;

		super.unauthenticate();
		forum = this.forumService.create();
		player = (Moderator) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(false);

		this.forumService.save(forum);

	}

	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumFatherOwnNegative() {
		Forum forum, savedForum;
		Moderator moderator;

		super.authenticate("moderator1");

		forum = this.forumService.create();
		moderator = (Moderator) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(moderator);
		forum.setSupport(false);
		forum.setStaff(false);

		savedForum = this.forumService.save(forum);
		savedForum.setForum(savedForum);
		this.forumService.save(savedForum);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditForumNotOwnerNegative() {
		Forum forum;
		int forumId;

		super.authenticate("player2"); //The player knows the Shelter

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);

		this.forumService.save(forum);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumStaffNegative() {
		Forum forum;
		Player player;

		super.authenticate("player1");

		forum = this.forumService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(true);

		this.forumService.save(forum);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumNoFatherStaffNegative() {
		Forum forum;
		Moderator moderator;
		Forum father;
		int forumId;

		super.authenticate("moderator1"); //The player knows the Shelter

		forumId = super.getEntityId("Forum1");
		father = this.forumService.findOne(forumId);

		forum = this.forumService.create();
		moderator = (Moderator) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(moderator);
		forum.setSupport(false);
		forum.setStaff(true);
		forum.setForum(father);

		this.forumService.save(forum);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test
	public void testDeleteForumPositive() {
		Forum forum;
		Moderator player;
		Forum father, savedForum;
		int forumId;

		super.authenticate("moderator1");

		forumId = super.getEntityId("Forum1");
		father = this.forumService.findOne(forumId);

		forum = this.forumService.create();
		player = (Moderator) this.actorService.findActorByPrincipal();

		forum.setName("Test name");
		forum.setDescription("Test description");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(false);
		forum.setForum(father);

		savedForum = this.forumService.save(forum);
		this.forumService.delete(savedForum);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteForumStaffNegative() {
		Forum forum;
		Player player;
		Forum father, savedForum;
		int forumId;

		super.authenticate("Player1");

		forumId = super.getEntityId("Forum1");
		father = this.forumService.findOne(forumId);

		forum = this.forumService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(true);
		forum.setForum(father);

		savedForum = this.forumService.save(forum);
		super.unauthenticate();

		super.authenticate("Moderator1");

		this.forumService.delete(savedForum);

		super.unauthenticate();
	}

	/**
	 * This test checks create a new forum regarding functional requirement number 20.2: An actor who is authenticated as a moderator must be able to
	 * open and delete threads, write messages in the forum and delete them if they are not appropriate.
	 * 
	 * @author Manuel
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteForumNotLoggedNegative() {
		Forum forum;
		Moderator moderator;
		Forum father, savedForum;
		int forumId;

		super.authenticate("moderator1");

		forumId = super.getEntityId("Forum1");
		father = this.forumService.findOne(forumId);

		forum = this.forumService.create();
		moderator = (Moderator) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(moderator);
		forum.setSupport(false);
		forum.setStaff(false);
		forum.setForum(father);

		savedForum = this.forumService.save(forum);
		super.unauthenticate();

		this.forumService.delete(savedForum);

	}

}
