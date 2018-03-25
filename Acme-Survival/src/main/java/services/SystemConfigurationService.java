package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	// Managed repository --------------------------------------------------

	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public SystemConfiguration create() {
		SystemConfiguration result;

		result = new SystemConfiguration();

		return result;
	}

	public Collection<SystemConfiguration> findAll() {

		Collection<SystemConfiguration> result;

		Assert.notNull(this.systemConfigurationRepository);
		result = this.systemConfigurationRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public SystemConfiguration findOne(final int systemConfigurationId) {

		SystemConfiguration result;

		result = this.systemConfigurationRepository.findOne(systemConfigurationId);

		return result;

	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {

		assert systemConfiguration != null;

		SystemConfiguration result;

		result = this.systemConfigurationRepository.save(systemConfiguration);

		return result;

	}

	public void delete(final SystemConfiguration systemConfiguration) {

		assert systemConfiguration != null;
		assert systemConfiguration.getId() != 0;

		Assert.isTrue(this.systemConfigurationRepository.exists(systemConfiguration.getId()));

		this.systemConfigurationRepository.delete(systemConfiguration);

	}
}

