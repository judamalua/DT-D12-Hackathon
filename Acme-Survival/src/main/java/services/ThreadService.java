
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.ThreadRepository;
import domain.Actor;
import domain.Thread;

@Service
@Transactional
public class ThreadService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ThreadRepository	threadRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------

	public Thread create() {
		Thread result;

		result = new Thread();

		return result;
	}

	public Collection<Thread> findAll() {

		Collection<Thread> result;

		Assert.notNull(this.threadRepository);
		result = this.threadRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Thread findOne(final int threadId) {

		Thread result;
		Assert.isTrue(threadId != 0);

		result = this.threadRepository.findOne(threadId);

		return result;

	}

	public Thread save(final Thread thread) {

		Assert.notNull(thread);

		Thread result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		result = this.threadRepository.save(thread);

		return result;

	}

	public void delete(final Thread thread) {

		Assert.notNull(thread);
		Assert.isTrue(thread.getId() != 0);

		Assert.isTrue(this.threadRepository.exists(thread.getId()));

		this.threadRepository.delete(thread);

	}

	public Collection<Thread> findThreadsByForum(final int forumId) {
		Collection<Thread> result;

		result = this.threadRepository.findThreadsByForum(forumId);

		return result;
	}
}
