
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MoveRepository;
import domain.Actor;
import domain.DesignerConfiguration;
import domain.Inventory;
import domain.Location;
import domain.Move;
import domain.Player;
import domain.Shelter;

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
	private ShelterService					shelterService;

	@Autowired
	private PlayerService					playerService;

	@Autowired
	private InventoryService				inventoryService;

	@Autowired
	private Validator						validator;


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
		Shelter shelter, ownShelter;
		final Collection<Player> playersKnowsShelter;
		Inventory inventory;
		DesignerConfiguration designerConfiguration;
		Actor actor;

		move.setStartDate(new Date(System.currentTimeMillis() - 1));
		time = this.timeBetweenLocations(move.getShelter().getLocation(), move.getLocation());
		move.setEndDate(new Date(System.currentTimeMillis() + time));
		shelter = move.getShelter();

		if (move.getId() == 0) {
			actor = this.actorService.findActorByPrincipal();
			ownShelter = this.shelterService.findShelterByPlayer(actor.getId());
			Assert.isTrue(shelter.equals(ownShelter));

			designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();

			inventory = this.inventoryService.findInventoryByShelter(shelter.getId());

			inventory.setWood(inventory.getWood() - designerConfiguration.getMovingWood());
			inventory.setWater(inventory.getWater() - designerConfiguration.getMovingWater());
			inventory.setWood(inventory.getMetal() - designerConfiguration.getMovingMetal());
			inventory.setWood(inventory.getFood() - designerConfiguration.getMovingFood());

			this.inventoryService.save(inventory);
		}

		playersKnowsShelter = this.playerService.findPlayersKnowsShelter(shelter.getId());

		for (final Player player : playersKnowsShelter) {
			player.getShelters().remove(shelter);
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

	public Collection<Move> findMovesByShelter(final int shelterId) {
		Assert.isTrue(shelterId != 0);

		Collection<Move> result;

		result = this.moveRepository.findMovesByShelter(shelterId);

		return result;
	}

	public Move findMostRecentMoveByShelter(final int shelterId) {
		Assert.isTrue(shelterId != 0);

		List<Move> resultList;
		Move result;
		Pageable pageable;

		pageable = new PageRequest(0, 1);

		resultList = this.moveRepository.findMostRecentMoveByShelter(shelterId, pageable).getContent();

		if (resultList.size() > 0)
			result = resultList.get(0);
		else
			result = null;

		return result;
	}

	public Move findCurrentMoveByShelter(final int shelterId) {
		Assert.isTrue(shelterId != 0);

		Move result;

		result = this.moveRepository.findCurrentMoveByShelter(shelterId);

		return result;
	}

	public Page<Move> findMovesByShelter(final int shelterId, final Pageable pageable) {
		Assert.isTrue(shelterId != 0);

		Page<Move> result;

		result = this.moveRepository.findMovesByShelter(shelterId, pageable);

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
		Double kmPerSecond;

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
		kmPerSecond = designerConfiguration.getKmPerSecond();

		result = (long) ((distance * 1000) / kmPerSecond);

		return result;
	}

	public Move reconstruct(final Move move, final BindingResult binding) {
		Move result;
		Actor actor;
		Shelter shelter;

		actor = this.actorService.findActorByPrincipal();

		shelter = this.shelterService.findShelterByPlayer(actor.getId());
		result = move;

		result.setShelter(shelter);

		this.validator.validate(result, binding);
		this.moveRepository.flush();

		return result;
	}

}
