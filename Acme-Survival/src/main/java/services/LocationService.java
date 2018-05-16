
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LocationRepository;
import domain.Location;

@Service
@Transactional
public class LocationService {

	// Managed repository --------------------------------------------------

	@Autowired
	private LocationRepository	locationRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Location create() {
		Location result;

		result = new Location();

		return result;
	}

	public Collection<Location> findAll() {

		Collection<Location> result;

		Assert.notNull(this.locationRepository);
		result = this.locationRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Location findOne(final int locationId) {

		Location result;

		result = this.locationRepository.findOne(locationId);

		return result;

	}

	public Location save(final Location location) {

		assert location != null;

		Location result;

		result = this.locationRepository.save(location);

		return result;

	}

	public void delete(final Location location) {

		assert location != null;
		assert location.getId() != 0;

		Assert.isTrue(this.locationRepository.exists(location.getId()));

		this.locationRepository.delete(location);

	}

	public Collection<Location> findAllLocationsByFinal() {
		Collection<Location> result;
		result = this.locationRepository.findAllLocationsByFinal();
		return result;
	}

	public Collection<Location> findAllLocationsByNotFinal() {
		Collection<Location> result;
		result = this.locationRepository.findAllLocationsByNotFinal();
		return result;
	}
	//Business methods --------------------

}
