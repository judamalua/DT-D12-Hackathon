
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		Assert.isTrue(forumId != 0);

		result = this.forumRepository.findOne(forumId);

		return result;

	}

	public Forum save(final Forum forum) {

		Assert.notNull(forum);
		if (forum.getForum() != null)
			Assert.isTrue(forum != forum.getForum());

		Forum result;
		Collection<Thread> threads;
		Collection<Forum> allSubForums;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		if (forum.getStaff()) {
			Assert.isTrue(!(actor instanceof Player));
			if (forum.getForum() != null)
				Assert.isTrue(forum.getForum().getStaff());
		}

		if (forum.getId() != 0) {
			allSubForums = this.findAllSubForums(forum.getId());
			if (forum.getForum() != null)
				Assert.isTrue(!allSubForums.contains(forum.getForum()));
		}
		Assert.isTrue(actor.equals(forum.getOwner()));

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

		Assert.notNull(forum);
		Assert.notNull(forum.getId() != 0);
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		if (forum.getStaff() || forum.getStaff())
			Assert.isTrue(!(actor instanceof Player));

		Assert.isTrue(this.forumRepository.exists(forum.getId()));

		this.deleteRecursive(forum);
	}

	/**
	 * Delete the forum passed as parameter and his subDorums recursively
	 * 
	 * @param forum
	 * @author MJ
	 */
	public void deleteRecursive(final Forum forum) {
		Assert.notNull(forum);
		Assert.isTrue(forum.getId() != 0);
		Collection<Forum> subForums, subsubForums;
		Collection<Thread> threads;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		Assert.isTrue(actor.equals(forum.getOwner()));

		subForums = this.findSubForums(forum.getId());

		//Iterate in every subForum to delete it
		for (final Forum subForum : subForums) {
			subsubForums = this.findSubForums(subForum.getId());

			if (subsubForums.size() == 0) {
				if (subForum.getStaff() || subForum.getStaff())
					Assert.isTrue(!(actor instanceof Player));
				this.forumRepository.delete(subForum);
				threads = this.threadService.findThreadsByForum(subForum.getId());
				for (final Thread thread : threads)
					this.threadService.delete(thread);
			} else
				//In other case then call again the method
				this.deleteRecursive(subForum);
		}
		//Finally delete the forum
		this.forumRepository.delete(forum);
	}

	public Page<Forum> findForums(final Boolean staff, final Pageable pageable) {
		Page<Forum> result;
		Assert.notNull(pageable);

		result = this.forumRepository.findForums(staff, pageable);

		return result;
	}

	public Forum reconstruct(final Forum forum, final BindingResult binding) {
		Forum result;
		Actor actor;

		if (forum.getId() == 0) {
			actor = this.actorService.findActorByPrincipal();
			result = forum;
			result.setOwner(actor);
			if (actor instanceof Player) {
				result.setStaff(false);
				result.setSupport(false);
			}

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

	public Collection<Forum> findSubForums(final int forumId) {
		Collection<Forum> result;
		Assert.isTrue(forumId != 0);

		result = this.forumRepository.findSubForums(forumId);

		return result;
	}

	public Page<Forum> findSubForums(final int forumId, final Boolean staff, final Pageable pageable) {
		Page<Forum> result;
		Assert.isTrue(forumId != 0);

		result = this.forumRepository.findSubForums(forumId, staff, pageable);

		return result;
	}

	public Collection<Forum> findAllSubForums(final int forumId) {
		Collection<Forum> result, subForums, subsubForums;

		subForums = this.findSubForums(forumId);
		result = subForums;

		for (final Forum subForum : subForums) {
			subsubForums = this.findSubForums(subForum.getId());
			if (subsubForums.size() > 0)
				result.addAll(this.findAllSubForums(subForum.getId()));
		}

		return result;
	}

	public Collection<Forum> findForums(final Boolean staff) {
		Collection<Forum> result;

		result = this.forumRepository.findForums(staff);

		return result;
	}
}
