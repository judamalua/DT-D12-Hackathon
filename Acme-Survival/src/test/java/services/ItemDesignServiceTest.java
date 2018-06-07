
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

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
import domain.ItemDesign;
import domain.Resource;
import domain.Tool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ItemDesignServiceTest extends AbstractTest {

	@Autowired
	public ActorService			actorService;
	@Autowired
	public ItemDesignService	itemDesignService;
	@Autowired
	public ToolService			toolService;
	@Autowired
	public ResourceService		resourceService;
	@Autowired
	public ConfigurationService	configurationService;


	public enum ItemType {
		TOOL, RESOURCE
	}


	//******************************************Positive Methods*******************************************************************
	/**
	 * This driver checks several tests regarding functional requirement number 19.5: An actor who is authenticated as a designer must be able to
	 * create itemDesigns designs (Tool and Resource), every test is explained inside.
	 * 
	 * @author Manuel
	 */
	@Test
	public void driverCreateItemDesigns() {
		// final ItemType itemType, String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs,
		// final String imageUrl, final Boolean finalMode, final Integer strength, final Integer luck, final Integer capacity,
		// final Double water, final Double food, final Double metal, final Double wood,

		final Object testingData[][] = {

			{
				// This test checks that authenticated designers can create a tool
				ItemType.TOOL, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */3,/* luck */4, /* capacity */3, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a resource with minimum values
				ItemType.RESOURCE, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */0.,
				/* food */0., /* metal */0., /* wood */0.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a tool with attributes set to the minimum
				ItemType.TOOL, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */0, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a resource
				ItemType.RESOURCE, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */1.,
				/* food */2., /* metal */3., /* wood */4.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a tool with strength set to less than the minimum
				ItemType.TOOL, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */-1,/* luck */0, /* capacity */0, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers can create a tool with luck set to less than the minimum
				ItemType.TOOL, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */-1, /* capacity */0, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers can create a tool with capacity set to less than the minimum
				ItemType.TOOL, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */-1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a tool with an empty name (en)
				ItemType.TOOL, "Designer1", "", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a tool with an empty name (es)
				ItemType.TOOL, "Designer1", "Test name", "", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a tool with an empty description (en)
				ItemType.TOOL, "Designer1", "Test name", "Nombre prueba", "", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a tool with an empty description (es)
				ItemType.TOOL, "Designer1", "Test name", "Nombre prueba", "Test description", "", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a tool with an empty imageUrl
				ItemType.TOOL, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción de prueba", /* imageUrl */"", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that unauthenticated users cannot create a tool
				ItemType.TOOL, null, "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers can create a resource with water less than minimum values
				ItemType.RESOURCE, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */-1.,
				/* food */0., /* metal */0., /* wood */0.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers can create a resource with food less than minimum values
				ItemType.RESOURCE, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */0.,
				/* food */-1., /* metal */0., /* wood */0.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers can create a resource with metal less minimum values
				ItemType.RESOURCE, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */0.,
				/* food */0., /* metal */-1., /* wood */0.,
				/* Class<?> expected */ConstraintViolationException.class
			}

		/*
		 * 0 -> ItemType
		 * 1 -> String
		 * 2 -> String
		 * 3 -> String
		 * 4 -> String
		 * 5 -> String
		 * 6 -> String
		 * 7 -> Boolean
		 * 8 -> Double
		 * 9 -> Double
		 * 10 -> Double
		 * 11 -> Double
		 * 12 -> Class<?>
		 */
		};
		for (int i = 0; i < testingData.length; i++) {
			this.templateCreateRoomDesigns((ItemType) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Boolean) testingData[i][7], (Integer) testingData[i][8], (Integer) testingData[i][9], (Integer) testingData[i][10], (Double) testingData[i][11], (Double) testingData[i][12], (Double) testingData[i][13], (Double) testingData[i][14],
				(Class<?>) testingData[i][15]);
		}
	}
	/**
	 * This driver checks several tests regarding functional requirement number 19.5: A user who is authenticated as designer must
	 * be able to list the tools and resources set to final mode
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverListFinalModeRoomDesigns() {
		final Object testingData[][] = {
			{
				// This test checks that designers can list final mode tools
				"Designer1", ItemType.TOOL, null
			}, {
				// This test checks that designers can list final mode resources
				"Designer1", ItemType.RESOURCE, null
			}, {
				// This test checks that designers that do not exist cannot list final mode tools
				"Designer100", ItemType.TOOL, IllegalArgumentException.class
			}, {
				// This test checks that designers that do not exist cannot list final mode resources
				"Designer100", ItemType.RESOURCE, IllegalArgumentException.class
			}, {
				// This test checks that players that do not exist cannot list final mode tools
				"Player100", ItemType.RESOURCE, IllegalArgumentException.class
			}, {
				// This test checks that players that do not exist cannot list final mode resources
				"Player100", ItemType.TOOL, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.templateListFinalModeRoomDesigns((String) testingData[i][0], (ItemType) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number 19.5: An authenticated designer must be able to list draft mode tools and resources
	 * 
	 * @author MJ
	 */
	@Test
	public void driverListDraftModeRoomDesigns() {
		final Object testingData[][] = {
			{
				// This test checks that designers can list draft mode tools
				"Designer1", ItemType.TOOL, null
			}, {
				// This test checks that designers can list draft mode resources
				"Designer1", ItemType.RESOURCE, null
			}, {
				// This test checks that designers that do not exist cannot list draft mode tools
				"Designer100", ItemType.TOOL, IllegalArgumentException.class
			}, {
				// This test checks that designers that do not exist cannot list draft mode resources
				"Designer100", ItemType.RESOURCE, IllegalArgumentException.class
			}, {
				// This test checks that players that do not exist cannot list final draft tools
				"Player100", ItemType.RESOURCE, IllegalArgumentException.class
			}, {
				// This test checks that players that do not exist cannot list final draft resources
				"Player100", ItemType.TOOL, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.templateListDraftModeRoomDesigns((String) testingData[i][0], (ItemType) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number 19.5: An actor who is authenticated as a designer must be able to
	 * edit draft mode item designs, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverEditRoomDesigns() {
		final Object testingData[][] = {

			{
				// This test checks that authenticated designers can edit a draft mode tool
				"Tool7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */3,/* luck */4, /* capacity */3, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a tool attributes with  set to the minimum
				"Tool7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */0, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a resource
				"Resource3", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */1.,
				/* food */2., /* metal */3., /* wood */4.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a resource with minimum values
				"Resource3", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */0.,
				/* food */0., /* metal */0., /* wood */0.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a tool with strength set to less than the minimum
				"Tool7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */-1,/* luck */0, /* capacity */0, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers can edit a tool with luck set to less than the minimum
				"Tool7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */-1, /* capacity */0, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers can edit a tool with capacity set to less than the minimum
				"Tool7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */-1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a tool with an empty name (en)
				"Tool7", "Designer1", "", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a tool with an empty name (es)
				"Tool7", "Designer1", "Test name", "", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a tool with an empty description (en)
				"Tool7", "Designer1", "Test name", "Nombre prueba", "", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a tool with an empty description (es)
				"Tool7", "Designer1", "Test name", "Nombre prueba", "Test description", "", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a tool with an empty imageUrl
				"Tool7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción de prueba", /* imageUrl */"", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that unauthenticated designers cannot edit a tool
				"Tool7", null, "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */0,/* luck */0, /* capacity */1, /* water */null,
				/* food */null, /* metal */null, /* wood */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers can edit a resource with water less than minimum values
				"Resource3", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */-1.,
				/* food */0., /* metal */0., /* wood */0.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a resource with food less than minimum values
				"Resource3", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */0.,
				/* food */-1., /* metal */0., /* wood */0.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a resource with metal less minimum values
				"Resource3", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* imageUrl */"https://www.myimage.com", /* finalMode */false,
				/* strength */null,/* luck */null, /* capacity */null, /* water */0.,
				/* food */0., /* metal */-1., /* wood */0.,
				/* Class<?> expected */ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.templateEditRoomDesigns((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Boolean) testingData[i][7], (Integer) testingData[i][8], (Integer) testingData[i][9], (Integer) testingData[i][10], (Double) testingData[i][11], (Double) testingData[i][12], (Double) testingData[i][13], (Double) testingData[i][14],
				(Class<?>) testingData[i][15]);
		}
	}

	/**
	 * This driver checks several tests regarding functional requirement number 19.5: An actor who is authenticated as a designer must be able to
	 * delete draft mode room designs, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverDeleteRoomDesigns() {
		final Object testingData[][] = {
			{
				// This test checks that unauthenticated users cannot delete a draft mode tool
				null, "Tool7", IllegalArgumentException.class
			}, {
				// This test checks that designers can delete a draft mode tool
				"Designer1", "Tool7", null
			}, {
				// This test checks that unauthenticated users cannot delete a draft mode resource
				null, "Resource3", IllegalArgumentException.class
			}, {
				// This test checks that designers can delete a draft mode tool
				"Designer1", "Resource3", null
			}, {
				// This test checks that managers cannot delete a final mode tool
				"Designer1", "Tool3", IllegalArgumentException.class
			}, {
				// This test checks that designers cannot delete a final mode resource
				"Designer1", "Resource1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.templateDeleteRoomDesigns((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ---------------------------------------------------------------------------------------

	protected void templateCreateRoomDesigns(final ItemType itemType, final String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final String imageUrl, final Boolean finalMode,
		final Integer strength, final Integer luck, final Integer capacity, final Double water, final Double food, final Double metal, final Double wood, final Class<?> expected) {
		Class<?> caught;
		ItemDesign resultTool = null;
		Resource resultResource = null;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			if (itemType.equals(ItemType.TOOL)) {
				resultTool = this.toolService.create();

				resultTool.setName(nameMap);
				resultTool.setDescription(descriptionMap);
				resultTool.setFinalMode(finalMode);
				resultTool.setImageUrl(imageUrl);
				((Tool) resultTool).setCapacity(capacity);
				((Tool) resultTool).setLuck(luck);
				((Tool) resultTool).setStrength(strength);

				this.toolService.save((Tool) resultTool);
				this.toolService.flush();
			} else if (itemType.equals(ItemType.RESOURCE)) {

				resultResource = this.resourceService.create();

				resultResource.setName(nameMap);
				resultResource.setDescription(descriptionMap);
				resultResource.setFinalMode(finalMode);
				resultResource.setFood(food);
				resultResource.setImageUrl(imageUrl);
				resultResource.setMetal(metal);
				resultResource.setWater(water);
				resultResource.setWood(wood);

				this.resourceService.save(resultResource);
				this.resourceService.flush();
			}
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	protected void templateListFinalModeRoomDesigns(final String username, final ItemType itemType, final Class<?> expected) {
		Class<?> caught;
		Collection<Tool> tools;
		Page<Tool> pageTool = null;
		Collection<Resource> resources;
		Page<Resource> pageResource = null;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			if (itemType.equals(ItemType.TOOL)) {
				pageTool = this.toolService.findFinal(pageable);
				tools = pageTool.getContent();

				Assert.notNull(tools);
			} else {
				pageResource = this.resourceService.findFinal(pageable);
				resources = pageResource.getContent();

				Assert.notNull(resources);
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateListDraftModeRoomDesigns(final String username, final ItemType itemType, final Class<?> expected) {
		Class<?> caught;
		Collection<Tool> tools;
		Page<Tool> pageTool = null;
		Collection<Resource> resources;
		Page<Resource> pageResource = null;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			if (itemType.equals(ItemType.TOOL)) {
				pageTool = this.toolService.findNotFinal(pageable);
				tools = pageTool.getContent();

				Assert.notNull(tools);
			} else {
				pageResource = this.resourceService.findNotFinal(pageable);
				resources = pageResource.getContent();

				Assert.notNull(resources);
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateEditRoomDesigns(final String itemDesignPopulate, final String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final String imageUrl, final Boolean finalMode,
		final Integer strength, final Integer luck, final Integer capacity, final Double water, final Double food, final Double metal, final Double wood, final Class<?> expected) {
		Class<?> caught;
		int itemDesignId;
		ItemDesign itemDesign;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			itemDesignId = super.getEntityId(itemDesignPopulate);

			itemDesign = this.itemDesignService.findOne(itemDesignId);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			if (itemDesign instanceof Tool) {

				itemDesign.setName(nameMap);
				itemDesign.setDescription(descriptionMap);
				itemDesign.setFinalMode(finalMode);
				itemDesign.setImageUrl(imageUrl);
				((Tool) itemDesign).setCapacity(capacity);
				((Tool) itemDesign).setLuck(luck);
				((Tool) itemDesign).setStrength(strength);

				this.toolService.save((Tool) itemDesign);
				this.toolService.flush();

			} else if (itemDesign instanceof Resource) {

				itemDesign = this.resourceService.create();

				itemDesign.setName(nameMap);
				itemDesign.setDescription(descriptionMap);
				itemDesign.setFinalMode(finalMode);
				itemDesign.setImageUrl(imageUrl);
				((Resource) itemDesign).setFood(food);
				((Resource) itemDesign).setMetal(metal);
				((Resource) itemDesign).setWater(water);
				((Resource) itemDesign).setWood(wood);

				this.resourceService.save((Resource) itemDesign);
				this.resourceService.flush();
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateDeleteRoomDesigns(final String username, final String itemDesignPopulate, final Class<?> expected) {
		Class<?> caught;
		int itemDesignId;
		ItemDesign itemDesign;

		caught = null;

		try {
			super.authenticate(username);

			itemDesignId = super.getEntityId(itemDesignPopulate);

			itemDesign = this.itemDesignService.findOne(itemDesignId);
			if (itemDesign instanceof Tool) {
				this.toolService.delete((Tool) itemDesign);
				this.toolService.flush();
			} else if (itemDesign instanceof Resource) {
				this.resourceService.delete((Resource) itemDesign);
				this.resourceService.flush();
			}

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
