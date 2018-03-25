package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ThreadRepository;
import domain.Thread;

@Service
@Transactional
public class ThreadService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ThreadRepository	threadRepository;


	// Supporting services --------------------------------------------------

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

		result = this.threadRepository.findOne(threadId);

		return result;

	}

	public Thread save(final Thread thread) {

		assert thread != null;

		Thread result;

		result = this.threadRepository.save(thread);

		return result;

	}

	public void delete(final Thread thread) {

		assert thread != null;
		assert thread.getId() != 0;

		Assert.isTrue(this.threadRepository.exists(thread.getId()));

		this.threadRepository.delete(thread);

	}
}

