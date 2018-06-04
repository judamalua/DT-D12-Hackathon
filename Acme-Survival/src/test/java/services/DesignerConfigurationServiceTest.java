
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.DesignerConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DesignerConfigurationServiceTest extends AbstractTest {

	//Service under test ---------------------------------------
	@Autowired
	private DesignerConfigurationService	designerConfigurationService;


	/**
	 * This test checks that the Player can Attack a Shelter that he already knows.
	 */
	@Test
	public void testEditDesignerConfigurationPositive() {
		DesignerConfiguration designerConfiguration;

		super.authenticate("Designer1");

		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();
		designerConfiguration.setExperiencePerMinute(2);

		this.designerConfigurationService.save(designerConfiguration);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveDesignerConfigurationNotLoggedNegative() {
		DesignerConfiguration designerConfiguration;
		super.unauthenticate();
		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();
		designerConfiguration.setExperiencePerMinute(2);

		this.designerConfigurationService.save(designerConfiguration);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveDesignerConfigurationFoodLostGatherGreaterThanWaterLostGatherNegative() {
		DesignerConfiguration designerConfiguration;

		super.authenticate("Designer1");

		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();
		designerConfiguration.setFoodLostGatherFactor(1);
		designerConfiguration.setWaterLostGatherFactor(2);

		this.designerConfigurationService.save(designerConfiguration);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveDesignerConfigurationNumCharactersGreaterThanShelterCapacityNegative() {
		DesignerConfiguration designerConfiguration;

		super.authenticate("Designer1");

		designerConfiguration = this.designerConfigurationService.findDesignerConfiguration();
		designerConfiguration.setNumInitialCharacters(3);
		designerConfiguration.setShelterDefaultCapacity(2);

		this.designerConfigurationService.save(designerConfiguration);

		super.unauthenticate();
	}
}
