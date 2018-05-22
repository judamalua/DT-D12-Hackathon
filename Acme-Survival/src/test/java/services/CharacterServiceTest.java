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

import java.util.Random;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Player;
import domain.Refuge;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CharacterServiceTest extends AbstractTest {

	@Autowired
	public CharacterService	characterService;

	@Autowired
	public ActorService		actorService;

	@Autowired
	public RefugeService	refugeService;


	//*****************************Positive Methods*************************************

	/**
	 * This method test auto-generate character method
	 * 
	 * @author Luis
	 */
	@Test
	public void testGenerateCharacter() {

		for (int i = 1; i < 10; i++)
			this.characterService.generateCharacter();

	}

	/**
	 * This method test Level selector of a character
	 * 
	 * @author Luis
	 */
	@Test
	public void testLevelSelector() {
		final Random r = new Random();

		for (int i = 1; i < 10; i++) {
			final domain.Character character = this.characterService.generateCharacter();
			character.setExperience(r.nextInt(1000000));
			this.characterService.calculateLevel(character);

		}

	}

	/**
	 * This method test that when a character have more than 1000000 of experience his level is always 100
	 * 
	 * @author Luis
	 */
	@Test
	public void testMaxLevel() {
		final domain.Character character = this.characterService.generateCharacter();
		character.setExperience(10004000);
		this.characterService.calculateLevel(character);

		Assert.isTrue(character.getLevel() == 100);

	}
	/**
	 * This method test that a character save in database correctly
	 * 
	 * @author Luis
	 */
	@Test
	public void testSaveCharacter() {

		final domain.Character character = this.createCharacter();

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}
	//*****************************Negative Methods*************************************
	/**
	 * This method test that can´t save a character with a water value lesser than 0
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative1() {
		final domain.Character character = this.createCharacter();
		character.setCurrentWater(-1);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	/**
	 * This method test that can´t save a character with a water value greater than 100
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative2() {
		final domain.Character character = this.createCharacter();
		character.setCurrentWater(101);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	/**
	 * This method test that can´t save a character with a food value lesser than 0
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative3() {
		final domain.Character character = this.createCharacter();
		character.setCurrentFood(-1);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	/**
	 * This method test that can´t save a character with a food value greater than 100
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative4() {
		final domain.Character character = this.createCharacter();
		character.setCurrentFood(101);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	/**
	 * This method test that can´t save a character with a health value lesser than 0
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative5() {
		final domain.Character character = this.createCharacter();
		character.setCurrentHealth(-1);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	/**
	 * This method test that can´t save a character with a health value greater than 100
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative6() {
		final domain.Character character = this.createCharacter();
		character.setCurrentHealth(101);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	/**
	 * This method test that can´t save a character with a level value lesser than 1
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative7() {
		final domain.Character character = this.createCharacter();
		character.setLevel(0);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	/**
	 * This method test that can´t save a character with a level value greater than 100
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative8() {
		final domain.Character character = this.createCharacter();
		character.setLevel(101);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	/**
	 * This method test that can´t save a character with a experience value lesser than 0
	 * 
	 * @author Luis
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSaveCharacterNegative9() {
		final domain.Character character = this.createCharacter();
		character.setExperience(-1);

		this.characterService.save(character);
		this.characterService.flush();

		super.unauthenticate();

	}

	private domain.Character createCharacter() {
		super.authenticate("Player1");
		final Player player = (Player) this.actorService.findActorByPrincipal();
		final Refuge refuge = this.refugeService.findRefugeByPlayer(player.getId());
		final domain.Character result = this.characterService.generateCharacter(refuge.getId());
		return result;

	}

}
