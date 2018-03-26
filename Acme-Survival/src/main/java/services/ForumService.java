
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ForumRepository;
import domain.Actor;
import domain.Forum;
import domain.Player;
import domain.Thread;

@Service
@Transactional
public class ForumService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ForumRepository	forumRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService	actorService;
	@Autowired
	private ThreadService	threadService;

	@Autowired
	private Validator		validator;


	// Simple CRUD methods --------------------------------------------------

	public Forum create() {
		Forum result;

		result = new Forum();

		return result;
	}

	public Collection<Forum> findAll() {

		Collection<Forum> result;

		Assert.notNull(this.forumRepository);
		result = this.forumRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Forum findOne(final int forumId) {

		Forum result;

		result = this.forumRepository.findOne(forumId);

		return result;

	}

	public Forum save(final Forum forum) {

		Assert.notNull(forum);

		Forum result;
		Collection<Thread> threads;
		//TODO: Check hierarchy
		threads = new HashSet<>();

		result = this.forumRepository.save(forum);

		if (forum.getId() != 0)
			threads = this.threadService.findThreadsByForum(result.getId());

		for (final domain.Thread thread : threads) {
			thread.setForum(result);
			this.threadService.save(thread);
		}

		return result;

	}
	public void delete(final Forum forum) {

		assert forum != null;
		assert forum.getId() != 0;

		Assert.isTrue(this.forumRepository.exists(forum.getId()));

		this.forumRepository.delete(forum);

	}

	public Forum reconstruct(final Forum forum, final BindingResult binding) {
		Forum result;
		Actor actor;

		if (forum.getId() == 0) {

			actor = this.actorService.findActorByPrincipal();
			result = this.create();

			result.setName(forum.getName());
			result.setDescription(forum.getDescription());
			result.setImage(forum.getImage());
			if (actor instanceof Player) {
				result.setStaff(false);
				result.setSupport(false);
			} else {
				result.setStaff(forum.getStaff());
				result.setSupport(forum.getSupport());
			}
			result.setForum(forum.getForum());

		} else {
			result = this.forumRepository.findOne(forum.getId());

			result.setName(forum.getName());
			result.setDescription(forum.getDescription());
			result.setImage(forum.getImage());
			result.setStaff(forum.getStaff());
			result.setSupport(forum.getSupport());

		}
		this.validator.validate(result, binding);

		return result;
	}
}
