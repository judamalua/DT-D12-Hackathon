
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MoveRepository;
import domain.DesignerConfiguration;
import domain.Location;
import domain.Move;

@Service
@Transactional
public class MoveService {

	// Managed repository --------------------------------------------------

	@Autowired
	private MoveRepository					moveRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private DesignerConfigurationService	designerConfigurationService;

	@Autowired
	private ActorService					actorService;


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

		Assert.notNull(move);

		Move result;
		long time;
		this.actorService.checkActorLogin();

		move.setStartDate(new Date(System.currentTimeMillis() - 1));
		time = this.timeBetweenLocations(move.getRefuge().getLocation(), move.getLocation());
		move.setEndDate(new Date(System.currentTimeMillis() + time));

		result = this.moveRepository.save(move);

		return result;

	}

	public void delete(final Move move) {

		Assert.notNull(move);
		Assert.isTrue(move.getId() != 0);
		this.actorService.checkActorLogin();

		Assert.isTrue(this.moveRepository.exists(move.getId()));

		this.moveRepository.delete(move);

	}

	public Collection<Move> findMovesByRefuge(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Move> result;

		result = this.moveRepository.findMovesByRefuge(refugeId);

		return result;
	}

	private long timeBetweenLocations(final Location origin, final Location end) {
		final long result = 0;
		DesignerConfiguration designerConfiguration;

		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();

		designerConfiguration.getKmPerSecond();
		//TODO: calculate km between locations

		return result;
	}
}
