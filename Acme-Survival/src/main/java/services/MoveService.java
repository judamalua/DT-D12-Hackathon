package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MoveRepository;
import domain.Move;

@Service
@Transactional
public class MoveService {

	// Managed repository --------------------------------------------------

	@Autowired
	private MoveRepository	moveRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Move create() {
		Move result;

		result = new Move();

		return result;
	}

	public Collection<Move> findAll() {

		Collection<Move> result;

		Assert.notNull(this.moveRepository);
		result = this.moveRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Move findOne(final int moveId) {

		Move result;

		result = this.moveRepository.findOne(moveId);

		return result;

	}

	public Move save(final Move move) {

		assert move != null;

		Move result;

		result = this.moveRepository.save(move);

		return result;

	}

	public void delete(final Move move) {

		assert move != null;
		assert move.getId() != 0;

		Assert.isTrue(this.moveRepository.exists(move.getId()));

		this.moveRepository.delete(move);

	}
}

