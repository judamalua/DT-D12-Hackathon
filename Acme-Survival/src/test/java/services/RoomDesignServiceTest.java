
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
import domain.Barrack;
import domain.ResourceRoom;
import domain.RestorationRoom;
import domain.RoomDesign;
import domain.Warehouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RoomDesignServiceTest extends AbstractTest {

	@Autowired
	public ActorService				actorService;
	@Autowired
	public RoomDesignService		roomDesignService;
	@Autowired
	public BarrackService			barrackService;
	@Autowired
	public WarehouseService			warehouseService;
	@Autowired
	public RestorationRoomService	restorationRoomService;
	@Autowired
	public ResourceRoomService		resourceRoomService;
	@Autowired
	public ConfigurationService		configurationService;


	public enum RoomType {
		BARRACK, WAREHOUSE, RESTORATION_ROOM, RESOURCE_ROOM
	}


	//******************************************Positive Methods*******************************************************************
	/**
	 * This driver checks several tests regarding functional requirement number 19.6: An actor who is authenticated as a designer must be able to
	 * create room designs (barracks, warehouses, restoration rooms and resource rooms), every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverCreateRoomDesigns() {
		//		final RoomType roomType, final String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs,
		//		final Integer maxResistance, final Double costWood, final Double costMetal, final Integer maxCapacityCharacters, final Integer characterCapacityBarrack, 
		//		final Integer itemCapacityWarehouse, final Double healthRestorationRoom, final Double foodRestorationRoom, final Double waterRestorationRoom,
		//		final Double waterResourceRoom, final Double foodResourceRoom, final Double metalResourceRoom, final Double woodResourceRoom, final Class<?> expected

		final Object testingData[][] = {

			{
				// This test checks that authenticated designers can create a barrack
				RoomType.BARRACK, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a barrack with maxResistance set to the minimum
				RoomType.BARRACK, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */0, /* costWood */2.,
				/* costMetal */3.0,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a barrack with costWood set to the minimum
				RoomType.BARRACK, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */0.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a barrack with costMetal set to the minimum
				RoomType.BARRACK, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */0.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a barrack with maxCapacityCharacters set to the minimum
				RoomType.BARRACK, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */1, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a barrack with characterCapacityBarrack set to the minimum
				RoomType.BARRACK, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */1, /* Integer characterCapacityBarrack */1, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a warehouse
				RoomType.WAREHOUSE, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */3,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a warehouse with itemCapacityWarehouse set to minimum
				RoomType.WAREHOUSE, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */1,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a restoration room
				RoomType.RESTORATION_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a restoration room with healthRestorationRoom set to minimum
				RoomType.RESTORATION_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */0., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a restoration room with foodRestorationRoom set to minimum
				RoomType.RESTORATION_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */0., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a restoration room with waterRestorationRoom set to minimum
				RoomType.RESTORATION_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */0.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a resource room
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a resource room with waterResourceRoom set to minimum
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */0., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a resource room with foodResourceRoom set to minimum
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */0., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a resource room with metalResourceRoom set to minimum
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */0., /* Double woodResourceRoom */2.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can create a resource room with woodResourceRoom set to minimum
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */0.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers cannot create a resource room with a negative waterResourceRoom
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */-2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a resource room with a negative foodResourceRoom
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */-2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a resource room with a negative metalResourceRoom
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */-2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a resource room with a negative woodResourceRoom
				RoomType.RESOURCE_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */-2.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a restoration room with a negative healthRestorationRoom
				RoomType.RESTORATION_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */-2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a restoration room with a negative foodRestorationRoom
				RoomType.RESTORATION_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */-2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a restoration room with a negative waterRestorationRoom
				RoomType.RESTORATION_ROOM, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */-2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a warehouse with itemCapacityWarehouse set to 0
				RoomType.WAREHOUSE, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */0,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a barrack with a blank English name
				RoomType.BARRACK, "Designer1", "", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a barrack with a blank Spanish name
				RoomType.BARRACK, "Designer1", "TestName", "", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a room design with a blank English description
				RoomType.BARRACK, "Designer1", "TestName", "Nombre prueba", "", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a room design with a blank Spanish description
				RoomType.BARRACK, "Designer1", "TestName", "Nombre prueba", "Test description", "", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a room design with a negative maxResistance
				RoomType.BARRACK, "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */-3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a room design with a negative costWood
				RoomType.BARRACK, "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */-2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a room design with a negative costMetal
				RoomType.BARRACK, "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */-3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a room design with a maxCapacityCharacters equals to 0
				RoomType.BARRACK, "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */0, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot create a room design with a characterCapacityBarrack equals to 0
				RoomType.BARRACK, "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated players cannot create a barrack
				RoomType.BARRACK, "Player1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot create a warehouse
				RoomType.WAREHOUSE, "Player1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */2,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot create a restoration room
				RoomType.RESTORATION_ROOM, "Player1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot create a resource room
				RoomType.RESOURCE_ROOM, "Player1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot create a barrack
				RoomType.BARRACK, "Moderator1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot create a warehouse
				RoomType.WAREHOUSE, "Moderator1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */2,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot create a restoration room
				RoomType.RESTORATION_ROOM, "Moderator1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot create a resource room
				RoomType.RESOURCE_ROOM, "Moderator1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot create a barrack
				RoomType.BARRACK, "Manager1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot create a warehouse
				RoomType.WAREHOUSE, "Manager1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */2,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot create a restoration room
				RoomType.RESTORATION_ROOM, "Manager1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot create a resource room
				RoomType.RESOURCE_ROOM, "Manager1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot create a barrack
				RoomType.BARRACK, "Admin1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot create a warehouse
				RoomType.WAREHOUSE, "Admin1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */2,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot create a restoration room
				RoomType.RESTORATION_ROOM, "Admin1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot create a resource room
				RoomType.RESOURCE_ROOM, "Admin1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */IllegalArgumentException.class
			}

		/*
		 * 0 -> RoomType
		 * 1 -> String
		 * 2 -> String
		 * 3 -> String
		 * 4 -> String
		 * 5 -> String
		 * 6 -> Integer
		 * 7 -> Double
		 * 8 -> Double
		 * 9 -> Integer
		 * 10 -> Integer
		 * 11 -> Integer
		 * 12 -> 18 Double
		 * 19 -> Class<?>
		 */

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateRoomDesigns((RoomType) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Double) testingData[i][7], (Double) testingData[i][8], (Integer) testingData[i][9], (Integer) testingData[i][10], (Integer) testingData[i][11], (Double) testingData[i][12], (Double) testingData[i][13], (Double) testingData[i][14],
				(Double) testingData[i][15], (Double) testingData[i][16], (Double) testingData[i][17], (Double) testingData[i][18], (Class<?>) testingData[i][19]);
	}
	/**
	 * This driver checks several tests regarding functional requirement number 19.9: A user who is authenticated as designer must
	 * be able to list the room designs set to final mode
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverListFinalModeRoomDesigns() {
		final Object testingData[][] = {
			{
				// This test checks that designers can list final mode room designs
				"Designer1", null
			}, {
				// This test checks that designers that do not exist cannot list final mode room designs
				"Designer100", IllegalArgumentException.class
			}, {
				// This test checks that players that do not exist cannot list final mode room designs
				"Player100", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListFinalModeRoomDesigns((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number 19.9: An authenticated designer must be able to list draft mode room designs
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverListDraftModeRoomDesigns() {
		final Object testingData[][] = {
			{
				// This test checks that authenticated designers can list draft mode room designs
				"Designer1", null
			}, {
				// This test checks that designers that do not exist cannot list draft mode room designs
				"Designer100", IllegalArgumentException.class
			}, {
				// This test checks that players that do not exist cannot list draft mode room designs
				"Player100", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListDraftModeRoomDesigns((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number 19.6: An actor who is authenticated as a designer must be able to
	 * edit draft mode room designs, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverEditRoomDesigns() {
		final Object testingData[][] = {

			{
				// This test checks that authenticated designers can edit a draft mode barrack
				"RoomDesign7" /* Barrack */, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a barrack with maxResistance set to the minimum
				"RoomDesign7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */0, /* costWood */2.,
				/* costMetal */3.0,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a barrack with costWood set to the minimum
				"RoomDesign7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */0.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a barrack with costMetal set to the minimum
				"RoomDesign7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */0.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a barrack with maxCapacityCharacters set to the minimum
				"RoomDesign7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */1, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a barrack with characterCapacityBarrack set to the minimum
				"RoomDesign7", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */1, /* Integer characterCapacityBarrack */1, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a warehouse
				"RoomDesign8", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */3,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a warehouse with itemCapacityWarehouse set to minimum
				"RoomDesign8", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */1,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a restoration room
				"RoomDesign9", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a restoration room with healthRestorationRoom set to minimum
				"RoomDesign9", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */0., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a restoration room with foodRestorationRoom set to minimum
				"RoomDesign9", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */0., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a restoration room with waterRestorationRoom set to minimum
				"RoomDesign9", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */0.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a resource room
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a resource room with waterResourceRoom set to minimum
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */0., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a resource room with foodResourceRoom set to minimum
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */0., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a resource room with metalResourceRoom set to minimum
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */0., /* Double woodResourceRoom */2.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers can edit a resource room with woodResourceRoom set to minimum
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */0.,
				/* Class<?> expected */null
			}, {
				// This test checks that authenticated designers cannot edit a resource room with a negative waterResourceRoom
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */-2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a resource room with a negative foodResourceRoom
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */-2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a resource room with a negative metalResourceRoom
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */-2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a resource room with a negative woodResourceRoom
				"RoomDesign10", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */-2.,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a restoration room with a negative healthRestorationRoom
				"RoomDesign9", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */-2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a restoration room with a negative foodRestorationRoom
				"RoomDesign9", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */-2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a restoration room with a negative waterRestorationRoom
				"RoomDesign9", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */-2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a warehouse with itemCapacityWarehouse set to 0
				"RoomDesign8", "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */0,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a barrack with a blank English name
				"RoomDesign7", "Designer1", "", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a barrack with a blank Spanish name
				"RoomDesign7", "Designer1", "TestName", "", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a room design with a blank English description
				"RoomDesign7", "Designer1", "TestName", "Nombre prueba", "", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a room design with a blank Spanish description
				"RoomDesign7", "Designer1", "TestName", "Nombre prueba", "Test description", "", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a room design with a negative maxResistance
				"RoomDesign7", "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */-3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a room design with a negative costWood
				"RoomDesign7", "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */-2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a room design with a negative costMetal
				"RoomDesign7", "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */-3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a room design with a maxCapacityCharacters equals to 0
				"RoomDesign7", "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */0, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated designers cannot edit a room design with a characterCapacityBarrack equals to 0
				"RoomDesign7", "Designer1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}, {
				// This test checks that authenticated players cannot edit a barrack
				"RoomDesign7", "Player1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot edit a warehouse
				"RoomDesign8", "Player1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */2,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot edit a restoration room
				"RoomDesign9", "Player1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot edit a resource room
				"RoomDesign10", "Player1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot edit a barrack
				"RoomDesign7", "Moderator1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot edit a warehouse
				"RoomDesign8", "Moderator1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */2,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot edit a restoration room
				"RoomDesign9", "Moderator1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot edit a resource room
				"RoomDesign10", "Moderator1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot edit a barrack
				"RoomDesign7", "Manager1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot edit a warehouse
				"RoomDesign8", "Manager1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */2,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot edit a restoration room
				"RoomDesign9", "Manager1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot edit a resource room
				"RoomDesign10", "Manager1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot edit a barrack
				"RoomDesign7", "Admin1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */0, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot edit a warehouse
				"RoomDesign8", "Admin1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */2,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot edit a restoration room
				"RoomDesign9", "Admin1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */2., /* Double foodRestorationRoom */2., /* Double waterRestorationRoom */2.,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot edit a resource room
				"RoomDesign10", "Admin1", "TestName", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */3, /* Integer characterCapacityBarrack */null, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */2., /* Double foodResourceRoom */2., /* Double metalResourceRoom */2., /* Double woodResourceRoom */2.,
				/* Class<?> expected */IllegalArgumentException.class
			}, {
				// This test checks that authenticated designers cannot edit a final mode barrack
				"RoomDesign6" /* Barrack */, "Designer1", "Test name", "Nombre prueba", "Test description", "Descripción prueba", /* maxResistance */3, /* costWood */2.,
				/* costMetal */3.,/* maxCapacityCharacters */4, /* Integer characterCapacityBarrack */3, /* Integer itemCapacityWarehouse */null,
				/* Double healthRestorationRoom */null, /* Double foodRestorationRoom */null, /* Double waterRestorationRoom */null,
				/* Double waterResourceRoom */null, /* Double foodResourceRoom */null, /* Double metalResourceRoom */null, /* Double woodResourceRoom */null,
				/* Class<?> expected */ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditRoomDesigns((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Double) testingData[i][7], (Double) testingData[i][8], (Integer) testingData[i][9], (Integer) testingData[i][10], (Integer) testingData[i][11], (Double) testingData[i][12], (Double) testingData[i][13], (Double) testingData[i][14],
				(Double) testingData[i][15], (Double) testingData[i][16], (Double) testingData[i][17], (Double) testingData[i][18], (Class<?>) testingData[i][19]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number 19.6: An actor who is authenticated as a designer must be able to
	 * delete draft mode room designs, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverDeleteRoomDesigns() {
		final Object testingData[][] = {
			{
				// This test checks that designers can delete a draft mode room design
				"Designer1", "RoomDesign7", null
			}, {
				// This test checks that managers cannot delete a final mode room design
				"Designer1", "RoomDesign1", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot delete a draft mode room design
				null, "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot delete a draft mode room design
				"Player1", "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot delete a draft mode room design
				"Manager1", "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot delete a draft mode room design
				"Moderator1", "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot delete a draft mode room design
				"Admin1", "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot delete a final mode room design
				null, "RoomDesign1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot delete a final mode room design
				"Player1", "RoomDesign1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot delete a final mode room design
				"Manager1", "RoomDesign1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot delete a final mode room design
				"Moderator1", "RoomDesign1", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot delete a final mode room design
				"Admin1", "RoomDesign1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteRoomDesigns((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * This driver checks several tests regarding functional requirement number 19.8: An actor who is authenticated as a designer must be able to
	 * set draft mode room designs as final mode, every test is explained inside.
	 * 
	 * @author Juanmi
	 */
	@Test
	public void driverSetFinalModeRoomDesigns() {
		final Object testingData[][] = {
			{
				// This test checks that designers can set draft mode room designs as final mode
				"Designer1", "RoomDesign7", null
			}, {
				// This test checks that designers cannot set final mode room designs as final mode
				"Designer1", "RoomDesign1", IllegalArgumentException.class
			}, {
				// This test checks that unauthenticated users cannot set draft mode room designs as final mode
				null, "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that authenticated players cannot set draft mode room designs as final mode
				"Player1", "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that authenticated moderators cannot set draft mode room designs as final mode
				"Moderator1", "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that authenticated managers cannot set draft mode room designs as final mode
				"Manager1", "RoomDesign7", IllegalArgumentException.class
			}, {
				// This test checks that authenticated admins cannot set draft mode room designs as final mode
				"Admin1", "RoomDesign7", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSetFinalModeRoomDesigns((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ---------------------------------------------------------------------------------------

	protected void templateCreateRoomDesigns(final RoomType roomType, final String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final Integer maxResistance, final Double costWood,
		final Double costMetal, final Integer maxCapacityCharacters, final Integer characterCapacity, final Integer itemCapacity, final Double healthRestorationRoom, final Double foodRestorationRoom, final Double waterRestorationRoom,
		final Double waterResourceRoom, final Double foodResourceRoom, final Double metalResourceRoom, final Double woodResourceRoom, final Class<?> expected) {
		Class<?> caught;
		Barrack resultBarrack = null;
		Warehouse resultWarehouse = null;
		RestorationRoom resultRestorationRoom = null;
		ResourceRoom resultResourceRoom = null;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			if (roomType.equals(RoomType.BARRACK)) {
				resultBarrack = this.barrackService.create();

				resultBarrack.setName(nameMap);
				resultBarrack.setDescription(descriptionMap);
				//resultBarrack.setMaxResistance(maxResistance);
				resultBarrack.setCostWood(costWood);
				resultBarrack.setCostMetal(costMetal);
				resultBarrack.setMaxCapacityCharacters(maxCapacityCharacters);

				//private Integer	characterCapacity;

				resultBarrack.setCharacterCapacity(characterCapacity);

				this.roomDesignService.save(resultBarrack);
			} else if (roomType.equals(RoomType.WAREHOUSE)) {

				resultWarehouse = this.warehouseService.create();

				resultWarehouse.setName(nameMap);
				resultWarehouse.setDescription(descriptionMap);
				//resultWarehouse.setMaxResistance(maxResistance);
				resultWarehouse.setCostWood(costWood);
				resultWarehouse.setCostMetal(costMetal);
				resultWarehouse.setMaxCapacityCharacters(maxCapacityCharacters);

				//private Integer	itemCapacity;
				resultWarehouse.setItemCapacity(itemCapacity);

				this.roomDesignService.save(resultWarehouse);

			} else if (roomType.equals(RoomType.RESTORATION_ROOM)) {
				resultRestorationRoom = this.restorationRoomService.create();

				resultRestorationRoom.setName(nameMap);
				resultRestorationRoom.setDescription(descriptionMap);
				//resultRestorationRoom.setMaxResistance(maxResistance);
				resultRestorationRoom.setCostWood(costWood);
				resultRestorationRoom.setCostMetal(costMetal);
				resultRestorationRoom.setMaxCapacityCharacters(maxCapacityCharacters);

				//				private Double	health;
				//				private Double	food;
				//				private Double	water;

				resultRestorationRoom.setHealth(healthRestorationRoom);
				resultRestorationRoom.setFood(foodRestorationRoom);
				resultRestorationRoom.setWater(waterRestorationRoom);

				this.roomDesignService.save(resultRestorationRoom);

			} else if (roomType.equals(RoomType.RESOURCE_ROOM)) {
				resultResourceRoom = this.resourceRoomService.create();

				resultResourceRoom.setName(nameMap);
				resultResourceRoom.setDescription(descriptionMap);
				//resultResourceRoom.setMaxResistance(maxResistance);
				resultResourceRoom.setCostWood(costWood);
				resultResourceRoom.setCostMetal(costMetal);
				resultResourceRoom.setMaxCapacityCharacters(maxCapacityCharacters);

				//				private Double	water;
				//				private Double	food;
				//				private Double	metal;
				//				private Double	wood;

				resultResourceRoom.setWater(waterResourceRoom);
				resultResourceRoom.setFood(foodResourceRoom);
				resultResourceRoom.setMetal(metalResourceRoom);
				resultResourceRoom.setWood(woodResourceRoom);

				this.roomDesignService.save(resultResourceRoom);
			}

			//			private Map<String, String>	name;
			//			private Map<String, String>	description;
			//			private Integer				maxResistance;
			//			private Double				costWood;
			//			private Double				costMetal;
			//			private boolean				finalMode;
			//			private Integer				maxCapacityCharacters;

			this.roomDesignService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	protected void templateListFinalModeRoomDesigns(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<RoomDesign> roomDesign;
		Page<RoomDesign> pageRoomDesigns;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageRoomDesigns = this.roomDesignService.findFinalRoomDesigns(pageable);
			roomDesign = pageRoomDesigns.getContent();

			Assert.notNull(roomDesign);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateListDraftModeRoomDesigns(final String username, final Class<?> expected) {
		Class<?> caught;
		Collection<RoomDesign> roomDesigns;
		Page<RoomDesign> pageRoomDesigns;
		Pageable pageable;

		caught = null;

		try {
			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());

			super.authenticate(username);
			pageRoomDesigns = this.roomDesignService.findFinalRoomDesigns(pageable);
			roomDesigns = pageRoomDesigns.getContent();

			Assert.notNull(roomDesigns);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateEditRoomDesigns(final String roomDesignPopulateName, final String username, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final Integer maxResistance, final Double costWood,
		final Double costMetal, final Integer maxCapacityCharacters, final Integer characterCapacity, final Integer itemCapacity, final Double healthRestorationRoom, final Double foodRestorationRoom, final Double waterRestorationRoom,
		final Double waterResourceRoom, final Double foodResourceRoom, final Double metalResourceRoom, final Double woodResourceRoom, final Class<?> expected) {
		Class<?> caught;
		int roomDesignId;
		RoomDesign roomDesign;
		final Map<String, String> nameMap = new HashMap<String, String>();
		final Map<String, String> descriptionMap = new HashMap<String, String>();

		caught = null;

		try {
			super.authenticate(username);

			roomDesignId = super.getEntityId(roomDesignPopulateName);

			roomDesign = this.roomDesignService.findOne(roomDesignId);

			super.authenticate(username);

			nameMap.put("en", nameEn);
			nameMap.put("es", nameEs);

			descriptionMap.put("en", descriptionEn);
			descriptionMap.put("es", descriptionEs);

			if (roomDesign instanceof Barrack) {

				roomDesign.setName(nameMap);
				roomDesign.setDescription(descriptionMap);
				//roomDesign.setMaxResistance(maxResistance);
				roomDesign.setCostWood(costWood);
				roomDesign.setCostMetal(costMetal);
				roomDesign.setMaxCapacityCharacters(maxCapacityCharacters);

				//private Integer	characterCapacity;

				((Barrack) roomDesign).setCharacterCapacity(characterCapacity);

			} else if (roomDesign instanceof Warehouse) {

				roomDesign = this.warehouseService.create();

				roomDesign.setName(nameMap);
				roomDesign.setDescription(descriptionMap);
				//roomDesign.setMaxResistance(maxResistance);
				roomDesign.setCostWood(costWood);
				roomDesign.setCostMetal(costMetal);
				roomDesign.setMaxCapacityCharacters(maxCapacityCharacters);

				//private Integer	itemCapacity;
				((Warehouse) roomDesign).setItemCapacity(itemCapacity);

			} else if (roomDesign instanceof RestorationRoom) {
				roomDesign = this.restorationRoomService.create();

				roomDesign.setName(nameMap);
				roomDesign.setDescription(descriptionMap);
				//roomDesign.setMaxResistance(maxResistance);
				roomDesign.setCostWood(costWood);
				roomDesign.setCostMetal(costMetal);
				roomDesign.setMaxCapacityCharacters(maxCapacityCharacters);

				//				private Double	health;
				//				private Double	food;
				//				private Double	water;

				((RestorationRoom) roomDesign).setHealth(healthRestorationRoom);
				((RestorationRoom) roomDesign).setFood(foodRestorationRoom);
				((RestorationRoom) roomDesign).setWater(waterRestorationRoom);

			} else if (roomDesign instanceof ResourceRoom) {
				roomDesign = this.resourceRoomService.create();

				roomDesign.setName(nameMap);
				roomDesign.setDescription(descriptionMap);
				//roomDesign.setMaxResistance(maxResistance);
				roomDesign.setCostWood(costWood);
				roomDesign.setCostMetal(costMetal);
				roomDesign.setMaxCapacityCharacters(maxCapacityCharacters);

				//				private Double	water;
				//				private Double	food;
				//				private Double	metal;
				//				private Double	wood;

				((ResourceRoom) roomDesign).setWater(waterResourceRoom);
				((ResourceRoom) roomDesign).setFood(foodResourceRoom);
				((ResourceRoom) roomDesign).setMetal(metalResourceRoom);
				((ResourceRoom) roomDesign).setWood(woodResourceRoom);
			}

			//			private Map<String, String>	name;
			//			private Map<String, String>	description;
			//			private Integer				maxResistance;
			//			private Double				costWood;
			//			private Double				costMetal;
			//			private boolean				finalMode;
			//			private Integer				maxCapacityCharacters;

			this.roomDesignService.save(roomDesign);
			this.roomDesignService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateDeleteRoomDesigns(final String username, final String roomDesignPopulateName, final Class<?> expected) {
		Class<?> caught;
		int roomDesignId;
		RoomDesign roomDesign;

		caught = null;

		try {
			super.authenticate(username);

			roomDesignId = super.getEntityId(roomDesignPopulateName);

			roomDesign = this.roomDesignService.findOne(roomDesignId);

			this.roomDesignService.delete(roomDesign);
			this.roomDesignService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateSetFinalModeRoomDesigns(final String username, final String roomDesignPopulateName, final Class<?> expected) {
		Class<?> caught;
		int roomDesignId;
		RoomDesign roomDesign;

		caught = null;

		try {
			super.authenticate(username);

			roomDesignId = super.getEntityId(roomDesignPopulateName);

			roomDesign = this.roomDesignService.findOne(roomDesignId);

			this.roomDesignService.setFinalModeRoomDesign(roomDesign);
			this.roomDesignService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
