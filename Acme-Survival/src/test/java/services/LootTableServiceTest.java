
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.LootTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LootTableServiceTest extends AbstractTest {

	@Autowired
	public ActorService			actorService;
	@Autowired
	public LootTableService		lootTableService;
	@Autowired
	public ConfigurationService	configurationService;


	//******************************************Positive Methods*******************************************************************
	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * create lootTables in the marketplace, every test is explained inside.
	 * 
	 * @author Ale
	 */
	@Test
	public void driverCreateLootTables() {

		final Object testingData[][] = {

			{
				// This test checks that authenticated designers can create a lootTable
				"Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, null
			}, {
				// This test checks that authenticated designers can create a lootTable with price 0.0
				"Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, null
			}, {
				// This test checks that unauthenticated users cannot create a lootTable
				null, "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot create a lootTable with a blank name in English
				"Designer1", "", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a lootTable with a blank name in Spanish
				"Designer1", "Test name", "", "Test description", "Descripción prueba", 5, 10, 8, true,  javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a lootTable with a blank description in English
				"Designer1", "Test name", "Nombre prueba", "", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a lootTable with a blank description in Spanish
				"Designer1", "Test name", "Nombre prueba", "Test description", "", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a lootTable
				"Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated players cannot create a lootTable
				"Player1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot create a lootTable
				"Manager1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderator cannot create a lootTable
				"Moderator1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderator cannot create a lootTable
				"Admin1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++){
			this.templateCreateLootTables((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Integer) testingData[i][5],
					(Integer) testingData[i][6], (Integer) testingData[i][7], (Boolean) testingData[i][8], (Class<?>) testingData[i][9]);
			System.out.println("TEST-F-" + i); 
		}
		}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: A user who is authenticated as Designer must
	 * be able to list the lootTables in final Mode
	 * 
	 * @author Ale
	 */
	@Test
	public void driverListFinalModeLootTables() {
		final Object testingData[][] = {
			{
				// This test checks that unauthenticated users can not list lootTables
				null, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players can not list lootTables
				"Player1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers can list lootTables
				"Designer1", null
			}, {
				// This test checks that authenticated moderators can not list lootTables
				"Moderator1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins can list lootTables
				"Admin1", IllegalArgumentException.class
			}, {
				// This test checks that a user that does not exist cannot list lootTables
				"Player500", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++){
			
			this.templateListFinalModeLootTables((String) testingData[i][0], (Class<?>) testingData[i][1]);
			System.out.println("TEST-D-" + i); 
		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An authenticated manager must be able to list draft mode lootTables
	 * 
	 * @author Ale
	 */
	@Test
	public void driverListDraftModeLootTables() {
		final Object testingData[][] = {
			{
				// This test checks that authenticated designers can list draft mode lootTables
				"Designer1", null
			}, {
				// This test checks that unauthenticated users cannot list draft mode lootTables
				null, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot list draft mode lootTables
				"Player1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot list draft mode lootTables
				"Manager1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated users moderators list draft mode lootTables
				"Moderator1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins can list draft mode lootTables
				"Admin1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++){
			this.templateListDraftModeLootTables((String) testingData[i][0], (Class<?>) testingData[i][1]);
			System.out.println("TEST-C-" + i); 
		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * edit draft mode lootTables, every test is explained inside.
	 * 
	 * @author Ale
	 */
	@Test
	public void driverEditLootTables() {

		final Object testingData[][] = {

			{
				// This test checks that authenticated designers can edit a draft mode lootTable
				"Designer1", "LootTable3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 2, 8, true, null
			}, {
				// This test checks that authenticated designers cannot edit a draft mode lootTable inserting a blank English name
				"Designer1", "LootTable3", "", "Nombre prueba", "Test description", "Descripción prueba", 1, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a draft mode lootTable inserting a blank Spanish name
				"Designer1", "LootTable3", "Test name", "", "Test description", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a draft mode lootTable inserting a blank English description
				"Designer1", "LootTable3", "Test name", "Nombre prueba", "", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a draft mode lootTable inserting a blank Spanish description
				"Designer1", "LootTable3", "Test name", "Nombre prueba", "Test description", "", 5, -10, 8, true, javax.validation.ConstraintViolationException.class
			},{
				// This test checks that unauthenticated users cannot edit a draft mode lootTable
				null, "LootTable3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 3, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot edit a draft mode lootTable
				"Player1", "LootTable3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot edit a draft mode lootTable
				"Moderator1", "LootTable3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 0, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins can edit a draft mode lootTable
				"Admin1", "LootTable3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 2, 8, true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++){
			this.templateEditLootTables((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Integer) testingData[i][7], (Integer) testingData[i][8], (Boolean) testingData[i][9], (Class<?>) testingData[i][10]);
		System.out.println("TEST-B-" + i); 
		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * delete draft mode lootTables, every test is explained inside.
	 * 
	 * @author Ale
	 */
	@Test
	public void driverDeleteLootTable() {
		final Object testingData[][] = {
			
			
			{
				// This test checks that designers cannot delete a final mode lootTable
				"Designer1", "LootTable1", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot delete a draft mode lootTable
				null, "LootTable5", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot delete a draft mode lootTable
				"Player1", "LootTable5", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot delete a draft mode lootTable
				"Manager1", "LootTable5", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot delete a draft mode lootTable
				"Moderator1", "LootTable5", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot delete a draft mode lootTable
				"Admin1", "LootTable5", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot delete a final mode lootTable
				null, "LootTable1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot delete a final mode lootTable
				"Player1", "LootTable1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot delete a final mode lootTable
				"Manager1", "LootTable1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot delete a final mode lootTable
				"Moderator1", "LootTable1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot delete a final mode lootTable
				"Admin1", "LootTable1", IllegalArgumentException.class
			},
			{
				// This test checks that designers can delete a draft mode lootTable
				"Designer1", "LootTable5", null
			}
		};

		for (int i = 0; i < testingData.length; i++){
			this.templateDeleteLootTables((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			System.out.println("TEST-A-" + i); 
		}
		}


	// Ancillary methods ---------------------------------------------------------------------------------------

	protected void templateCreateLootTables(final String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final Integer health, final Integer water, final Integer food, final Boolean findCharacter,
		final Class<?> expected) {
		Class<?> caught;
		LootTable result;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			result = this.lootTableService.create();

			result.setName(nameMap);
			result.setDescription(descriptionMap);
			result.setHealth(health);
			result.setWater(water);
			result.setFood(food);
			result.setFindCharacter(findCharacter);

			this.lootTableService.save(result);
			this.lootTableService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateListFinalModeLootTables(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<LootTable> lootTables;
		Page<LootTable> pageLootTables;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageLootTables = this.lootTableService.findFinal(pageable);
			lootTables = pageLootTables.getContent();

			Assert.notNull(lootTables);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateListDraftModeLootTables(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<LootTable> lootTables;
		Page<LootTable> pageLootTables;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageLootTables = this.lootTableService.findNotFinal(pageable);
			lootTables = pageLootTables.getContent();

			Assert.notNull(lootTables);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateEditLootTables(final String username, final String lootTablePopulateName, final String nameEn, final Boolean finalMode, final String name, final String descriptionEn, final String descriptionEs, final Integer health, final Integer water, final Integer food, final Boolean findCharacter,
		final Class<?> expected) {
		Class<?> caught;
		int lootTableId;
		LootTable lootTable;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			lootTableId = super.getEntityId(lootTablePopulateName);

			lootTable = this.lootTableService.findOne(lootTableId);
			lootTable.setName(name);
			lootTable.setFinalMode(finalMode);
			lootTable.setProbabilityEvents(probabilityEvents);
			lootTable.setProbabilityItems(probabilityItems)
			lootTable.setDescription(descriptionMap);
			lootTable.setHealth(health);
			lootTable.setWater(water);
			lootTable.setFood(food);
			lootTable.setFindCharacter(findCharacter);

			this.lootTableService.save(lootTable);
			this.lootTableService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateDeleteLootTables(final String username, final String lootTablePopulateName, final Class<?> expected) {
		Class<?> caught;
		int lootTableId;
		LootTable lootTable;

		caught = null;

		try {
			super.authenticate(username);

			lootTableId = super.getEntityId(lootTablePopulateName);

			lootTable = this.lootTableService.findOne(lootTableId);

			this.lootTableService.delete(lootTable);
			this.lootTableService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	 
}
