
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.DesignerConfigurationRepository;
import domain.DesignerConfiguration;

@Service
@Transactional
public class DesignerConfigurationService {

	// Managed repository --------------------------------------------------

	@Autowired
	private DesignerConfigurationRepository	designerConfigurationRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public DesignerConfiguration create() {
		DesignerConfiguration result;

		result = new DesignerConfiguration();

		return result;
	}

	public Collection<DesignerConfiguration> findAll() {

		Collection<DesignerConfiguration> result;

		Assert.notNull(this.designerConfigurationRepository);
		result = this.designerConfigurationRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public DesignerConfiguration findOne(final int designerConfigurationId) {

		DesignerConfiguration result;

		result = this.designerConfigurationRepository.findOne(designerConfigurationId);

		return result;

	}

	public DesignerConfiguration save(final DesignerConfiguration designerConfiguration) {

		assert designerConfiguration != null;

		DesignerConfiguration result;

		result = this.designerConfigurationRepository.save(designerConfiguration);

		return result;

	}

	public void delete(final DesignerConfiguration designerConfiguration) {

		assert designerConfiguration != null;
		assert designerConfiguration.getId() != 0;

		Assert.isTrue(this.designerConfigurationRepository.exists(designerConfiguration.getId()));

		this.designerConfigurationRepository.delete(designerConfiguration);

	}

	public DesignerConfiguration findDesignerConfiguration() {
		DesignerConfiguration result;

		result = this.designerConfigurationRepository.findDesignerConfiguration();

		return result;
	}
}
