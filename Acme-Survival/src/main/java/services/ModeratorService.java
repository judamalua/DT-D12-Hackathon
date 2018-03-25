package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ModeratorRepository;
import domain.Moderator;

@Service
@Transactional
public class ModeratorService {

	// Managed repository --------------------------------------------------

	@Autowired
	private ModeratorRepository	moderatorRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Moderator create() {
		Moderator result;

		result = new Moderator();

		return result;
	}

	public Collection<Moderator> findAll() {

		Collection<Moderator> result;

		Assert.notNull(this.moderatorRepository);
		result = this.moderatorRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Moderator findOne(final int moderatorId) {

		Moderator result;

		result = this.moderatorRepository.findOne(moderatorId);

		return result;

	}

	public Moderator save(final Moderator moderator) {

		assert moderator != null;

		Moderator result;

		result = this.moderatorRepository.save(moderator);

		return result;

	}

	public void delete(final Moderator moderator) {

		assert moderator != null;
		assert moderator.getId() != 0;

		Assert.isTrue(this.moderatorRepository.exists(moderator.getId()));

		this.moderatorRepository.delete(moderator);

	}
}

