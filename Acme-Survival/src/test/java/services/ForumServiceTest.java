
package services;

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
public class ForumServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private ForumService	forumService;

	@Autowired
	private ActorService	actorService;


	/**
	 * This test checks that the Player can Attack a Refuge that he already knows.
	 */
	@Test
	public void testSaveForumPositive() {
		Forum forum;
		Player player;

		super.authenticate("player1"); //The player knows the Refuge

		forum = this.forumService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(false);

		this.forumService.save(forum);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumNotLoggedPositive() {
		Forum forum;
		Player player;

		forum = this.forumService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(false);

		this.forumService.save(forum);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumFatherOwnNegative() {
		Forum forum, savedForum;
		Player player;

		super.authenticate("player1"); //The player knows the Refuge

		forum = this.forumService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(false);

		savedForum = this.forumService.save(forum);
		savedForum.setForum(savedForum);
		this.forumService.save(forum);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEditForumNotOwnerNegative() {
		Forum forum;
		int forumId;

		super.authenticate("player2"); //The player knows the Refuge

		forumId = super.getEntityId("Forum1");
		forum = this.forumService.findOne(forumId);

		this.forumService.save(forum);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumStaffNegative() {
		Forum forum;
		Player player;

		super.authenticate("player1"); //The player knows the Refuge

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

	@Test(expected = IllegalArgumentException.class)
	public void testSaveForumNoFatherStaffNegative() {
		Forum forum;
		Player player;
		Forum father;
		int forumId;

		super.authenticate("player1"); //The player knows the Refuge

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

		this.forumService.save(forum);

		super.unauthenticate();
	}

	@Test
	public void testDeleteForumPositive() {
		Forum forum;
		Player player;
		Forum father, savedForum;
		int forumId;

		super.authenticate("player1"); //The player knows the Refuge

		forumId = super.getEntityId("Forum1");
		father = this.forumService.findOne(forumId);

		forum = this.forumService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(false);
		forum.setForum(father);

		savedForum = this.forumService.save(forum);
		this.forumService.delete(savedForum);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteForumStaffNegative() {
		Forum forum;
		Player player;
		Forum father, savedForum;
		int forumId;

		super.authenticate("moderator1"); //The player knows the Refuge

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

		super.authenticate("player1");

		this.forumService.delete(savedForum);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteForumNotOwnerNegative() {
		Forum forum;
		Player player;
		Forum father, savedForum;
		int forumId;

		super.authenticate("player2"); //The player knows the Refuge

		forumId = super.getEntityId("Forum1");
		father = this.forumService.findOne(forumId);

		forum = this.forumService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(false);
		forum.setForum(father);

		savedForum = this.forumService.save(forum);
		super.unauthenticate();

		super.authenticate("player1");

		this.forumService.delete(savedForum);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteForumNotLoggedNegative() {
		Forum forum;
		Player player;
		Forum father, savedForum;
		int forumId;

		super.authenticate("player2");

		forumId = super.getEntityId("Forum1");
		father = this.forumService.findOne(forumId);

		forum = this.forumService.create();
		player = (Player) this.actorService.findActorByPrincipal();

		forum.setName("Test");
		forum.setDescription("Test");
		forum.setImage("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
		forum.setOwner(player);
		forum.setSupport(false);
		forum.setStaff(false);
		forum.setForum(father);

		savedForum = this.forumService.save(forum);
		super.unauthenticate();

		this.forumService.delete(savedForum);

	}

}
