package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository --------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;


	// Supporting services --------------------------------------------------

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

		result = this.messageRepository.findOne(messageId);

		return result;

	}

	public Message save(final Message message) {

		assert message != null;

		Message result;

		result = this.messageRepository.save(message);

		return result;

	}

	public void delete(final Message message) {

		assert message != null;
		assert message.getId() != 0;

		Assert.isTrue(this.messageRepository.exists(message.getId()));

		this.messageRepository.delete(message);

	}
}

