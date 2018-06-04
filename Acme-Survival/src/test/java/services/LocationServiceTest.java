
package services;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Location;
import domain.LootTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LocationServiceTest extends AbstractTest {

	// The SUT ---------------------------------------------------------------
	@Autowired
	private LocationService		locationService;

	@Autowired
	private LootTableService	lootTableService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		//name_en, name_es, coordinates, finalMode, lootTable, error
		final Object testingData[][] = {
			{
				//positive test
				"Sahara desert", "Desierto del sahara", "19.67134517234259,9.388784847476245", true, "LootTable1", null
			}, {
				//A loot table must be final
				"Sahara desert", "Desierto del sahara", "19.67134517234259,9.388784847476245", true, "LootTable5", IllegalArgumentException.class
			}, {
				//The patron must work
				"Sahara desert", "Desierto del sahara", "19.67134517234259-9.388784847476245", true, "LootTable1", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Boolean) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
		}

	}

	protected void template(final String name_en, final String name_es, final String coordinates, final Boolean finalMode, final String lootTablePopulate, final Class<?> expected) {
		Location location;
		Class<?> caught;

		caught = null;

		try {
			super.authenticate("designer1");

			location = this.locationService.create();

			final int lootTableId = super.getEntityId(lootTablePopulate);

			final LootTable lootTable = this.lootTableService.findOne(lootTableId);

			final Map<String, String> name = new HashMap<String, String>();

			name.put("en", name_en);
			name.put("es", name_es);

			location.setName(name);
			location.setLootTable(lootTable);
			location.setPoint_a(coordinates);
			location.setPoint_b(coordinates);
			location.setPoint_c(coordinates);
			location.setPoint_d(coordinates);
			location.setFinalMode(finalMode);

			this.locationService.save(location);

			this.locationService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
