
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;
import domain.Moderator;

@Service
@Transactional
public class MessageService {

	// Managed repository --------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods --------------------------------------------------

	public Message create() {
		Message result;

		result = new Message();

		return result;
	}

	public Collection<Message> findAll() {

		Collection<Message> result;

		Assert.notNull(this.messageRepository);
		result = this.messageRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Message findOne(final int messageId) {

		Message result;
		Assert.isTrue(messageId != 0);

		result = this.messageRepository.findOne(messageId);

		return result;

	}

	public Message save(final Message message) {

		Assert.notNull(message);

		final Message result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		if (!(actor instanceof Moderator)) {
			Assert.isTrue(message.getActor().equals(actor));
		}

		message.setMoment(new Date(System.currentTimeMillis() - 1));
		result = this.messageRepository.save(message);

		return result;

	}

	public void delete(final Message message) {

		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		Actor actor;

		actor = this.actorService.findActorByPrincipal();
		if (!(actor instanceof Moderator)) {
			Assert.isTrue(message.getActor().equals(actor));
		}

		Assert.isTrue(this.messageRepository.exists(message.getId()));

		this.messageRepository.delete(message);

	}

	public Collection<Message> findMessagesByThread(final int threadId) {
		Collection<Message> result;

		Assert.isTrue(threadId != 0);

		result = this.messageRepository.findMessagesByThread(threadId);

		return result;
	}

	public Page<Message> findMessagesByThread(final int threadId, final Pageable pageable) {
		Page<Message> result;

		Assert.isTrue(threadId != 0);

		result = this.messageRepository.findMessagesByThread(threadId, pageable);

		return result;
	}

	public Message reconstruct(final Message message, final BindingResult binding) {
		Message result;
		Actor actor;

		if (message.getId() == 0) {

			actor = this.actorService.findActorByPrincipal();
			result = message;

			result.setMoment(new Date(System.currentTimeMillis() - 1));
			result.setActor(actor);

		} else {
			result = this.messageRepository.findOne(message.getId());

			result.setText(message.getText());

		}
		this.validator.validate(result, binding);
		this.messageRepository.flush();

		return result;
	}

	public void flush() {
		this.messageRepository.flush();
	}
}
