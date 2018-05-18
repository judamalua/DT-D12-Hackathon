
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
	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a designer must be able to
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
	 * This driver checks several tests regarding functional requirement number TODO: X.X: A user who is authenticated as designer must
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
	//
	//	/**
	//	 * This driver checks several tests regarding functional requirement number TODO: X.X: An authenticated manager must be able to list draft mode products
	//	 * 
	//	 * @author Juanmi
	//	 */
	//	@Test
	//	public void driverListDraftModeProducts() {
	//		final Object testingData[][] = {
	//			{
	//				// This test checks that authenticated managers can list draft mode products
	//				"Manager1", null
	//			}, {
	//				// This test checks that unauthenticated users cannot list draft mode products
	//				null, IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated players cannot list draft mode products
	//				"Player1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated designers cannot list draft mode products
	//				"Designer1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated users moderators list draft mode products
	//				"Moderator1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated admins cannot list draft mode products
	//				"Admin1", IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateListDraftModeProducts((String) testingData[i][0], (Class<?>) testingData[i][1]);
	//	}
	//
	//	/**
	//	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	//	 * edit draft mode products, every test is explained inside.
	//	 * 
	//	 * @author Juanmi
	//	 */
	//	@Test
	//	public void driverEditProducts() {
	//		final String pictureUrl = "https://www.estilosdeaprendizaje.org/testestilosdeaprendizaje.png";
	//
	//		final Object testingData[][] = {
	//
	//			{
	//				// This test checks that authenticated managers can edit a draft mode product
	//				"Manager1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, null
	//			}, {
	//				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank English name
	//				"Manager1", "Product3", "", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, javax.validation.ConstraintViolationException.class
	//			}, {
	//				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank Spanish name
	//				"Manager1", "Product3", "Test name", "", "Test description", "Descripción prueba", pictureUrl, 12.0, javax.validation.ConstraintViolationException.class
	//			}, {
	//				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank English description
	//				"Manager1", "Product3", "Test name", "Nombre prueba", "", "Descripción prueba", pictureUrl, 12.0, javax.validation.ConstraintViolationException.class
	//			}, {
	//				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank Spanish description
	//				"Manager1", "Product3", "Test name", "Nombre prueba", "Test description", "", pictureUrl, 12.0, javax.validation.ConstraintViolationException.class
	//			}, {
	//				// This test checks that authenticated managers cannot edit a draft mode product inserting a blank picture URL
	//				"Manager1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", "", 12.0, javax.validation.ConstraintViolationException.class
	//			}, {
	//				// This test checks that authenticated managers cannot edit a draft mode product inserting a negative price
	//				"Manager1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, -12.0, javax.validation.ConstraintViolationException.class
	//			}, {
	//				// This test checks that unauthenticated users cannot edit a draft mode product
	//				null, "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated players cannot edit a draft mode product
	//				"Player1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated designers cannot edit a draft mode product
	//				"Designer1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated moderators cannot edit a draft mode product
	//				"Moderator1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated admins cannot edit a draft mode product
	//				"Admin1", "Product3", "Test name", "Nombre prueba", "Test description", "Descripción prueba", pictureUrl, 12.0, IllegalArgumentException.class
	//			}
	//
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateEditProducts((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
	//				(Double) testingData[i][7], (Class<?>) testingData[i][8]);
	//	}
	//
	//	/**
	//	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	//	 * delete draft mode products, every test is explained inside.
	//	 * 
	//	 * @author Juanmi
	//	 */
	//	@Test
	//	public void driverDeleteProduct() {
	//		final Object testingData[][] = {
	//			{
	//				// This test checks that managers can delete a draft mode product
	//				"Manager1", "Product3", null
	//			}, {
	//				// This test checks that managers cannot delete a final mode product
	//				"Manager1", "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that unauthenticated users cannot delete a draft mode product
	//				null, "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated players cannot delete a draft mode product
	//				"Player1", "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated designers cannot delete a draft mode product
	//				"Designer1", "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated moderators cannot delete a draft mode product
	//				"Moderator1", "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated admins cannot delete a draft mode product
	//				"Admin1", "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that unauthenticated users cannot delete a final mode product
	//				null, "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated players cannot delete a final mode product
	//				"Player1", "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated designers cannot delete a final mode product
	//				"Designer1", "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated moderators cannot delete a final mode product
	//				"Moderator1", "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated admins cannot delete a final mode product
	//				"Admin1", "Product1", IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateDeleteProducts((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}
	//
	//	/**
	//	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	//	 * set final mode products as discontinued, every test is explained inside.
	//	 * 
	//	 * @author Juanmi
	//	 */
	//	@Test
	//	public void driverDiscontinueProduct() {
	//		final Object testingData[][] = {
	//			{
	//				// This test checks that managers can set final mode products as discontinued
	//				"Manager1", "Product1", null
	//			}, {
	//				// This test checks that managers cannot set draft mode products as discontinued
	//				"Manager1", "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that unauthenticated users cannot set final mode products as discontinued
	//				null, "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated players cannot set final mode products as discontinued
	//				"Player1", "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated moderators cannot set final mode products as discontinued
	//				"Moderator1", "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated designers cannot set final mode products as discontinued
	//				"Designer1", "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated admins cannot set final mode products as discontinued
	//				"Admin1", "Product1", IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateDiscontinueProducts((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}
	//
	//	/**
	//	 * This driver checks several tests regarding functional requirement number TODO: X.X: An actor who is authenticated as a manager must be able to
	//	 * set draft mode products as final mode, every test is explained inside.
	//	 * 
	//	 * @author Juanmi
	//	 */
	//	@Test
	//	public void driverSetFinalModeProduct() {
	//		final Object testingData[][] = {
	//			{
	//				// This test checks that managers can set draft mode products as final mode
	//				"Manager1", "Product3", null
	//			}, {
	//				// This test checks that managers cannot set final mode products as final mode
	//				"Manager1", "Product1", IllegalArgumentException.class
	//			}, {
	//				// This test checks that unauthenticated users cannot set draft mode products as final mode
	//				null, "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated players cannot set draft mode products as final mode
	//				"Player1", "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated moderators cannot set draft mode products as final mode
	//				"Moderator1", "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated designers cannot set draft mode products as final mode
	//				"Designer1", "Product3", IllegalArgumentException.class
	//			}, {
	//				// This test checks that authenticated admins cannot set draft mode products as final mode
	//				"Admin1", "Product3", IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateSetFinalModeProducts((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}

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
				resultBarrack.setMaxResistance(maxResistance);
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
				resultWarehouse.setMaxResistance(maxResistance);
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
				resultRestorationRoom.setMaxResistance(maxResistance);
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
				resultResourceRoom.setMaxResistance(maxResistance);
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
	//
	//	protected void templateListDraftModeProducts(final String username, final Class<?> expected) {
	//		Class<?> caught;
	//		Collection<Product> products;
	//		Page<Product> pageProducts;
	//		Pageable pageable;
	//
	//		caught = null;
	//
	//		try {
	//			pageable = new PageRequest(0, this.configurationService.findConfiguration().getPageSize());
	//
	//			super.authenticate(username);
	//			pageProducts = this.roomDesignService.getDraftModeProducts(pageable);
	//			products = pageProducts.getContent();
	//
	//			Assert.notNull(products);
	//
	//			super.unauthenticate();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//	}
	//
	//	protected void templateEditProducts(final String username, final String productPopulateName, final String nameEn, final String nameEs, final String descriptionEn, final String descriptionEs, final String pictureUrl, final Double price,
	//		final Class<?> expected) {
	//		Class<?> caught;
	//		int productId;
	//		Product product;
	//		final Map<String, String> nameMap = new HashMap<String, String>();
	//		final Map<String, String> descriptionMap = new HashMap<String, String>();
	//
	//		caught = null;
	//
	//		try {
	//			super.authenticate(username);
	//
	//			productId = super.getEntityId(productPopulateName);
	//
	//			product = this.roomDesignService.findOne(productId);
	//
	//			nameMap.put("en", nameEn);
	//			nameMap.put("es", nameEs);
	//
	//			descriptionMap.put("en", descriptionEn);
	//			descriptionMap.put("es", descriptionEs);
	//
	//			product.setName(nameMap);
	//			product.setDescription(descriptionMap);
	//			product.setPictureUrl(pictureUrl);
	//			product.setPrice(price);
	//
	//			this.roomDesignService.save(product);
	//			this.roomDesignService.flush();
	//
	//			super.unauthenticate();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//	}
	//
	//	protected void templateDeleteProducts(final String username, final String productPopulateName, final Class<?> expected) {
	//		Class<?> caught;
	//		int productId;
	//		Product product;
	//
	//		caught = null;
	//
	//		try {
	//			super.authenticate(username);
	//
	//			productId = super.getEntityId(productPopulateName);
	//
	//			product = this.roomDesignService.findOne(productId);
	//
	//			this.roomDesignService.delete(product);
	//			this.roomDesignService.flush();
	//
	//			super.unauthenticate();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//	}
	//
	//	protected void templateDiscontinueProducts(final String username, final String productPopulateName, final Class<?> expected) {
	//		Class<?> caught;
	//		int productId;
	//		Product product;
	//
	//		caught = null;
	//
	//		try {
	//			super.authenticate(username);
	//
	//			productId = super.getEntityId(productPopulateName);
	//
	//			product = this.roomDesignService.findOne(productId);
	//
	//			this.roomDesignService.discontinueProduct(product);
	//			this.roomDesignService.flush();
	//
	//			super.unauthenticate();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//	}
	//
	//	protected void templateSetFinalModeProducts(final String username, final String productPopulateName, final Class<?> expected) {
	//		Class<?> caught;
	//		int productId;
	//		Product product;
	//
	//		caught = null;
	//
	//		try {
	//			super.authenticate(username);
	//
	//			productId = super.getEntityId(productPopulateName);
	//
	//			product = this.roomDesignService.findOne(productId);
	//
	//			this.roomDesignService.setFinalModeProduct(product);
	//			this.roomDesignService.flush();
	//
	//			super.unauthenticate();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//	}
}
