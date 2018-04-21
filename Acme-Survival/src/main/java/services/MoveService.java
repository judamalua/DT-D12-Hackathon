
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
import domain.Player;
import domain.Refuge;

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

	@Autowired
	private RefugeService					refugeService;

	@Autowired
	private PlayerService					playerService;


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

		final Move result;
		long time;
		this.actorService.checkActorLogin();
		Refuge refuge, savedRefuge;
		final Collection<Player> playersKnowsRefuge;

		move.setStartDate(new Date(System.currentTimeMillis() - 1));
		time = this.timeBetweenLocations(move.getRefuge().getLocation(), move.getLocation());
		move.setEndDate(new Date(System.currentTimeMillis() + time));
		refuge = move.getRefuge();
		refuge.setLocation(move.getLocation());
		refuge.setGpsCoordinates(this.refugeService.generateRandomCoordinates(move.getLocation()));

		savedRefuge = this.refugeService.save(refuge);
		move.setRefuge(savedRefuge);

		playersKnowsRefuge = this.playerService.findPlayersKnowsRefuge(savedRefuge.getId());
		for (final Player player : playersKnowsRefuge) {
			player.getRefuges().remove(savedRefuge);
			this.actorService.save(player);
		}
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

	public long timeBetweenLocations(final Location origin, final Location end) {
		long result;
		DesignerConfiguration designerConfiguration;
		final int earthRadius = 6371;
		final Double latitudeOrigin;
		final Double longitudeOrigin;
		final Double latitudeEnd;
		final Double longitudeEnd;
		final Double lonDistance;
		final Double latDistance;

		latitudeOrigin = new Double(origin.getPoint_a().split(",")[0]);
		longitudeOrigin = new Double(origin.getPoint_a().split(",")[1]);
		latitudeEnd = new Double(end.getPoint_a().split(",")[0]);
		longitudeEnd = new Double(end.getPoint_a().split(",")[1]);

		latDistance = Math.toRadians(latitudeEnd - latitudeOrigin);
		lonDistance = Math.toRadians(longitudeEnd - longitudeOrigin);

		final Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(latitudeOrigin)) * Math.cos(Math.toRadians(latitudeEnd)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		final Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double distance = earthRadius * c; // convert to meters

		distance = Math.pow(distance, 2);//Distanca in kilometres

		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();

		result = (long) (distance * 1000 / designerConfiguration.getKmPerSecond());

		return result;
	}

}
