package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import domain.ProbabilityEvent;
import domain.ProbabilityItem;

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
	@Autowired
	public ProbabilityItemService probabilityItemService;
	@Autowired
	public ProbabilityEventService	probabilityEventService;


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
					// This test checks that authenticated designers can edit a draft mode lootTable
					"Designer1", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, null
				}, {
					// This test checks that authenticated designers cannot edit a draft mode lootTable inserting a blank English name
					"Designer1", "", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, javax.validation.ConstraintViolationException.class
				},{
					// This test checks that unauthenticated users cannot edit a draft mode lootTable
					null, "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, IllegalArgumentException.class
				}, {
					// This test checks that authenticated players cannot edit a draft mode lootTable
					"Player1", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, IllegalArgumentException.class
				}, {
					// This test checks that authenticated moderators cannot edit a draft mode lootTable
					"Moderator1", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, IllegalArgumentException.class
				}, {
					// This test checks that authenticated admins can edit a draft mode lootTable
					"Admin1", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, IllegalArgumentException.class
				}

		};

		for (int i = 0; i < testingData.length; i++){
			this.templateCreateLootTables((String) testingData[i][0], (String) testingData[i][1], (List<String>) testingData[i][2], (List<String>) testingData[i][3], (Boolean) testingData[i][4], (Class<?>) testingData[i][5]);
		}
		}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: A user who is authenticated as Designer must
	 * be able to list the lootTables in final Mode
	 * 
	 * @author Ale
	 */
	@Test
	public void driverListLootTables() {
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
			
			this.templateListLootTables((String) testingData[i][0], (Class<?>) testingData[i][1]);
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
				"Designer1", "LootTable3", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, null
			}, {
				// This test checks that authenticated designers cannot edit a draft mode lootTable inserting a blank English name
				"Designer1", "LootTable3", "", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, javax.validation.ConstraintViolationException.class
			},{
				// This test checks that unauthenticated users cannot edit a draft mode lootTable
				null, "LootTable3", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot edit a draft mode lootTable
				"Player1", "LootTable3", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot edit a draft mode lootTable
				"Moderator1", "LootTable3", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins can edit a draft mode lootTable
				"Admin1", "LootTable3", "Test name", Arrays.asList("ProbabilityItemTest1", "ProbabilityItemTest2"), Arrays.asList("ProbabilityEventTest1", "ProbabilityEventTest2"), true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++){
			this.templateEditLootTables((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (List<String>) testingData[i][3], (List<String>) testingData[i][4], (Boolean) testingData[i][5], (Class<?>) testingData[i][6]);
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
				null, "LootTable5", IllegalArgumentException.class
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
		}
		}


	// Ancillary methods ---------------------------------------------------------------------------------------

	protected void templateCreateLootTables(final String username, final String name, final List<String> items, final List<String> events, final Boolean finalMode,
		final Class<?> expected) {
		Class<?> caught;
		LootTable result;
		caught = null;

		try {
			super.authenticate(username);

			result = this.lootTableService.create();
			
			Collection<ProbabilityItem> probItems = new HashSet<ProbabilityItem>();
			Collection<ProbabilityEvent> probEvents = new HashSet<ProbabilityEvent>();
			
			for (String pItem : items){
				Integer itemId = super.getEntityId(pItem);
				probItems.add(this.probabilityItemService.findOne(itemId));
			}
			for (String pEvent: events){
				Integer eventId = super.getEntityId(pEvent);
				probEvents.add(this.probabilityEventService.findOne(eventId));
			}

			result.setName(name);
			result.setProbabilityEvents(probEvents);
			result.setProbabilityItems(probItems);
			result.setFinalMode(finalMode);

			this.lootTableService.save(result);
			this.lootTableService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateListLootTables(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<LootTable> lootTables;
		Page<LootTable> pageLootTables;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageLootTables = this.lootTableService.findAll(pageable);
			lootTables = pageLootTables.getContent();

			Assert.notNull(lootTables);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}


	protected void templateEditLootTables(final String username, final String lootTablePopulateName, final String name, final List<String> items, final List<String> events, final Boolean finalMode,
		final Class<?> expected) {
		Class<?> caught;
		int lootTableId;
		LootTable lootTable;
		caught = null;

		try {
			super.authenticate(username);

			lootTableId = super.getEntityId(lootTablePopulateName);

			lootTable = this.lootTableService.findOne(lootTableId);
			
			Collection<ProbabilityItem> probItems = new HashSet<ProbabilityItem>();
			Collection<ProbabilityEvent> probEvents = new HashSet<ProbabilityEvent>();
			
			for (String pItem : items){
				Integer itemId = super.getEntityId(pItem);
				probItems.add(this.probabilityItemService.findOne(itemId));
			}
			for (String pEvent: events){
				Integer itemId = super.getEntityId(pEvent);
				probEvents.add(this.probabilityEventService.findOne(itemId));
			}
			
			
			lootTable.setName(name);
			lootTable.setProbabilityEvents(probEvents);
			lootTable.setProbabilityItems(probItems);
			lootTable.setFinalMode(finalMode);

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