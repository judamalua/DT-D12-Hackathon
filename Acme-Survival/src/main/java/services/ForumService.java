package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ForumRepository;
import domain.Forum;

@Service
@Transactional
public class ForumService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ForumRepository	forumRepository;


	// Supporting services --------------------------------------------------

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

		assert forum != null;

		Forum result;

		result = this.forumRepository.save(forum);

		return result;

	}

	public void delete(final Forum forum) {

		assert forum != null;
		assert forum.getId() != 0;

		Assert.isTrue(this.forumRepository.exists(forum.getId()));

		this.forumRepository.delete(forum);

	}
}

