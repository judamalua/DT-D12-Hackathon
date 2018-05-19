/*
 * SampleTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Item;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemServiceTest extends AbstractTest {

	@Autowired
	public CharacterService	characterService;

	@Autowired
	public ActorService		actorService;

	@Autowired
	public RefugeService	refugeService;

	@Autowired
	public ItemService		itemService;


	//*****************************Positive Methods*************************************

	/**
	 * This method test the correct equip of a item
	 * 
	 * @author Luis
	 */
	@Test
	public void testEquipItem() {
		super.authenticate("Player1");
		int itemId;
		int characterId;
		Item item;
		domain.Character character;

		itemId = super.getEntityId("Item1");
		characterId = super.getEntityId("Character1");
		item = this.itemService.findOne(itemId);
		character = this.characterService.findOne(characterId);

		this.itemService.UpdateEquipped(item, character.getId());

	}

	/**
	 * This method test the correct discard of a item
	 * 
	 * @author Luis
	 */
	@Test
	public void testDiscardItem() {
		super.authenticate("Player1");
		int itemId;
		int characterId;
		Item item;
		domain.Character character;

		itemId = super.getEntityId("Item1");
		characterId = super.getEntityId("Character1");
		item = this.itemService.findOne(itemId);
		character = this.characterService.findOne(characterId);

		this.itemService.UpdateEquipped(item, character.getId());

		this.itemService.UpdateDiscard(character);

	}

	@Test
	public void testDeleteItem() {
		super.authenticate("Player1");
		int itemId;
		Item item;

		itemId = super.getEntityId("Item2");

		item = this.itemService.findOne(itemId);

		this.itemService.delete(item);

	}

	//*****************************Negative Methods*************************************

}