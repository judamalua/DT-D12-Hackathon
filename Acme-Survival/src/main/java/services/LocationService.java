
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

	/**
	 * This method calculates the center of the given location
	 * 
	 * @param location
	 * @return the center of the given location
	 * 
	 * @author Juanmi
	 */
	public Location getLocationCenter(final Location location) {
		Location result;
		final Double center_x, center_y;
		String center;

		center_x = (new Double(location.getPoint_a().split(",")[0].trim()) + new Double(location.getPoint_b().split(",")[0].trim()) + new Double(location.getPoint_c().split(",")[0].trim()) + new Double(location.getPoint_d().split(",")[0].trim())) / 4;
		center_y = (new Double(location.getPoint_a().split(",")[1].trim()) + new Double(location.getPoint_b().split(",")[1].trim()) + new Double(location.getPoint_c().split(",")[1].trim()) + new Double(location.getPoint_d().split(",")[1].trim())) / 4;

		center = center_x + "," + center_y;

		result = this.create();

		result.setPoint_a(center);
		result.setPoint_b(center);
		result.setPoint_c(center);
		result.setPoint_d(center);

		return result;
	}
}
