
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MissionRepository;
import domain.Mission;

@Service
@Transactional
public class MissionService {

	// Managed repository --------------------------------------------------

	@Autowired
	private MissionRepository	missionRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Collection<Mission> findAll() {

		Collection<Mission> result;

		Assert.notNull(this.missionRepository);
		result = this.missionRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Mission findOne(final int missionId) {

		Mission result;

		result = this.missionRepository.findOne(missionId);

		return result;

	}

	public Mission save(final Mission mission) {
		Assert.notNull(mission);

		Mission result;

		result = this.missionRepository.save(mission);

		return result;

	}

	public void delete(final Mission mission) {

		//TODO

	}
}
