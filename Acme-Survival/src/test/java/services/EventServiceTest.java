
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
import domain.Event;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EventServiceTest extends AbstractTest {

	@Autowired
	public ActorService			actorService;
	@Autowired
	public EventService		eventService;
	@Autowired
	public ConfigurationService	configurationService;


	//******************************************Positive Methods*******************************************************************
	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * create events in the marketplace, every test is explained inside.
	 * 
	 * @author Ale
	 */
	@Test
	public void driverCreateEvents() {

		final Object testingData[][] = {

			{
				// This test checks that authenticated designers can create a event
				"Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, null
			}, {
				// This test checks that authenticated designers can create a event with price 0.0
				"Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, null
			}, {
				// This test checks that unauthenticated users cannot create a event
				null, "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot create a event with a blank name in English
				"Designer1", "", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a event with a blank name in Spanish
				"Designer1", "Test name", "", "Test description", "Descripción prueba", 5, 10, 8, true,  javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a event with a blank description in English
				"Designer1", "Test name", "Nombre prueba", "", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a event with a blank description in Spanish
				"Designer1", "Test name", "Nombre prueba", "Test description", "", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a event
				"Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated players cannot create a event
				"Player1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot create a event
				"Manager1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderator cannot create a event
				"Moderator1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderator cannot create a event
				"Admin1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++){
			this.templateCreateEvents((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Integer) testingData[i][5],
					(Integer) testingData[i][6], (Integer) testingData[i][7], (Boolean) testingData[i][8], (Class<?>) testingData[i][9]);

		}
		}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: A user who is authenticated as Designer must
	 * be able to list the events in final Mode
	 * 
	 * @author Ale
	 */
	@Test
	public void driverListFinalModeEvents() {
		final Object testingData[][] = {
			{
				// This test checks that unauthenticated users can not list events
				null, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players can not list events
				"Player1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers can list events
				"Designer1", null
			}, {
				// This test checks that authenticated moderators can not list events
				"Moderator1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins can list events
				"Admin1", IllegalArgumentException.class
			}, {
				// This test checks that a user that does not exist cannot list events
				"Player500", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++){
			
			this.templateListFinalModeEvents((String) testingData[i][0], (Class<?>) testingData[i][1]);

		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An authenticated manager must be able to list draft mode events
	 * 
	 * @author Ale
	 */
	@Test
	public void driverListDraftModeEvents() {
		final Object testingData[][] = {
			{
				// This test checks that authenticated designers can list draft mode events
				"Designer1", null
			}, {
				// This test checks that unauthenticated users cannot list draft mode events
				null, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot list draft mode events
				"Player1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot list draft mode events
				"Manager1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated users moderators list draft mode events
				"Moderator1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins can list draft mode events
				"Admin1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++){
			this.templateListDraftModeEvents((String) testingData[i][0], (Class<?>) testingData[i][1]);
			
		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * edit draft mode events, every test is explained inside.
	 * 
	 * @author Ale
	 */
	@Test
	public void driverEditEvents() {

		final Object testingData[][] = {

			{
				// This test checks that authenticated designers can edit a draft mode event
				"Designer1", "Event3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 2, 8, true, null
			}, {
				// This test checks that authenticated designers cannot edit a draft mode event inserting a blank English name
				"Designer1", "Event3", "", "Nombre prueba", "Test description", "Descripción prueba", 1, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a draft mode event inserting a blank Spanish name
				"Designer1", "Event3", "Test name", "", "Test description", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a draft mode event inserting a blank English description
				"Designer1", "Event3", "Test name", "Nombre prueba", "", "Descripción prueba", 5, 10, 8, true, javax.validation.ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a draft mode event inserting a blank Spanish description
				"Designer1", "Event3", "Test name", "Nombre prueba", "Test description", "", 5, -10, 8, true, javax.validation.ConstraintViolationException.class
			},{
				// This test checks that unauthenticated users cannot edit a draft mode event
				null, "Event3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 3, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot edit a draft mode event
				"Player1", "Event3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot edit a draft mode event
				"Moderator1", "Event3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 0, 10, 8, true, IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins can edit a draft mode event
				"Admin1", "Event3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", 5, 2, 8, true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++){
			this.templateEditEvents((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Integer) testingData[i][7], (Integer) testingData[i][8], (Boolean) testingData[i][9], (Class<?>) testingData[i][10]);

		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	 * delete draft mode events, every test is explained inside.
	 * 
	 * @author Ale
	 */
	@Test
	public void driverDeleteEvent() {
		final Object testingData[][] = {
			
			
			{
				// This test checks that designers cannot delete a final mode event
				"Designer1", "Event1", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot delete a draft mode event
				null, "Event5", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot delete a draft mode event
				"Player1", "Event5", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot delete a draft mode event
				"Manager1", "Event5", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot delete a draft mode event
				"Moderator1", "Event5", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot delete a draft mode event
				"Admin1", "Event5", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot delete a final mode event
				null, "Event1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot delete a final mode event
				"Player1", "Event1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot delete a final mode event
				"Manager1", "Event1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot delete a final mode event
				"Moderator1", "Event1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot delete a final mode event
				"Admin1", "Event1", IllegalArgumentException.class
			},
			{
				// This test checks that designers can delete a draft mode event
				"Designer1", "Event5", null
			}
		};

		for (int i = 0; i < testingData.length; i++){
			this.templateDeleteEvents((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

		}
		}


	// Ancillary methods ---------------------------------------------------------------------------------------

	protected void templateCreateEvents(final String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final Integer health, final Integer water, final Integer food, final Boolean findCharacter,
		final Class<?> expected) {
		Class<?> caught;
		Event result;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			result = this.eventService.create();

			result.setName(nameMap);
			result.setDescription(descriptionMap);
			result.setHealth(health);
			result.setWater(water);
			result.setFood(food);
			result.setFindCharacter(findCharacter);

			this.eventService.save(result);
			this.eventService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateListFinalModeEvents(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<Event> events;
		Page<Event> pageEvents;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageEvents = this.eventService.findFinal(pageable);
			events = pageEvents.getContent();

			Assert.notNull(events);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateListDraftModeEvents(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<Event> events;
		Page<Event> pageEvents;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageEvents = this.eventService.findNotFinal(pageable);
			events = pageEvents.getContent();

			Assert.notNull(events);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateEditEvents(final String username, final String eventPopulateName, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final Integer health, final Integer water, final Integer food, final Boolean findCharacter,
		final Class<?> expected) {
		Class<?> caught;
		int eventId;
		Event event;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			eventId = super.getEntityId(eventPopulateName);

			event = this.eventService.findOne(eventId);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			event.setName(nameMap);
			event.setDescription(descriptionMap);
			event.setHealth(health);
			event.setWater(water);
			event.setFood(food);
			event.setFindCharacter(findCharacter);

			this.eventService.save(event);
			this.eventService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateDeleteEvents(final String username, final String eventPopulateName, final Class<?> expected) {
		Class<?> caught;
		int eventId;
		Event event;

		caught = null;

		try {
			super.authenticate(username);

			eventId = super.getEntityId(eventPopulateName);

			event = this.eventService.findOne(eventId);

			this.eventService.delete(event);
			this.eventService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	 
}
