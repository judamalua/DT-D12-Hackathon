
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.DesignerConfigurationRepository;
import domain.Actor;
import domain.Designer;
import domain.DesignerConfiguration;

@Service
@Transactional
public class DesignerConfigurationService {

	// Managed repository --------------------------------------------------

	@Autowired
	private DesignerConfigurationRepository	designerConfigurationRepository;

	@Autowired
	private ActorService					actorService;


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

		Assert.notNull(designerConfiguration);
		Assert.isTrue(designerConfiguration.getFoodLostGatherFactor() > designerConfiguration.getWaterLostGatherFactor());
		Assert.isTrue(designerConfiguration.getRefugeDefaultCapacity() >= designerConfiguration.getNumInitialCharacters());

		DesignerConfiguration result;
		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		Assert.isTrue(actor instanceof Designer);

		result = this.designerConfigurationRepository.save(designerConfiguration);

		return result;

	}
	public void delete(final DesignerConfiguration designerConfiguration) {

		Assert.notNull(designerConfiguration);
		Assert.isTrue(designerConfiguration.getId() != 0);

		Assert.isTrue(this.designerConfigurationRepository.exists(designerConfiguration.getId()));

		Actor actor;

		actor = this.actorService.findActorByPrincipal();

		Assert.isTrue(actor instanceof Designer);

		this.designerConfigurationRepository.delete(designerConfiguration);

	}

	public DesignerConfiguration findDesignerConfiguration() {
		DesignerConfiguration result;

		result = this.designerConfigurationRepository.findDesignerConfiguration();

		return result;
	}

	public void flush() {
		this.designerConfigurationRepository.flush();
	}
}
