
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ThreadRepository;
import domain.Actor;
import domain.Message;
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

	@Autowired
	private MessageService		messageService;


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
		final Collection<Message> messages;

		this.actorService.checkActorLogin();
		result = this.threadRepository.save(thread);

		if (thread.getId() != 0) {
			messages = this.messageService.findMessagesByThread(thread.getId());
			for (final Message message : messages) {
				message.setThread(result);
				this.messageService.save(message);
			}
		}

		return result;
	}

	public void delete(final Thread thread) {

		Assert.notNull(thread);
		Assert.isTrue(thread.getId() != 0);
		this.actorService.checkActorLogin();
		Assert.isTrue(this.threadRepository.exists(thread.getId()));

		final Collection<Message> messages;

		messages = this.messageService.findMessagesByThread(thread.getId());

		for (final Message message : messages)
			this.messageService.delete(message);

		this.threadRepository.delete(thread);

	}

	public Collection<Thread> findThreadsByForum(final int forumId) {
		Collection<Thread> result;

		result = this.threadRepository.findThreadsByForum(forumId);

		return result;
	}

	public Page<Thread> findThreadsByForum(final int forumId, final Pageable pageable) {
		Page<Thread> result;
		Assert.isTrue(forumId != 0);
		Assert.notNull(pageable);

		result = this.threadRepository.findThreadsByForum(forumId, pageable);

		return result;
	}

	public Thread reconstruct(final Thread thread, final BindingResult binding) {
		Thread result;
		Actor actor;

		if (thread.getId() == 0) {

			actor = this.actorService.findActorByPrincipal();
			result = thread;

			result.setActor(actor);

		} else {
			result = this.threadRepository.findOne(thread.getId());

			result.setName(thread.getName());
			result.setTags(thread.getTags());

		}
		this.validator.validate(result, binding);

		return result;
	}
}
