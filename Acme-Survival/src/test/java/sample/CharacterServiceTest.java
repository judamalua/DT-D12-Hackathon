/*
 * SampleTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package sample;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.ajbrown.namemachine.Gender;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CharacterService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CharacterServiceTest extends AbstractTest {

	@Autowired
	public CharacterService	characterService;


	@Test
	public void testGenerateCharacter() throws FileNotFoundException {

		for (int i = 1; i < 15; i++) {
			final domain.Character character = this.characterService.generateCharacter();
			System.out.println(character.getFullName() + ",capacity=" + character.getCapacity() + ",luck=" + character.getLuck() + ",strenght=" + character.getStrength());
		}

	}

	@Test
	public void testLevelSelector() throws FileNotFoundException {
		final Random r = new Random();

		for (int i = 1; i < 50; i++) {
			final domain.Character character = this.characterService.generateCharacter();
			character.setExperience(r.nextInt(1000000));
			this.characterService.calculateLevel(character);

			System.out.println(character.getFullName() + ",capacity=" + character.getCapacity() + ",luck=" + character.getLuck() + ",strenght=" + character.getStrength() + "experience=" + character.getExperience() + ",level=" + character.getLevel());

		}

	}
	@Test
	public void testNameSelector() {
		final NameGenerator generator = new NameGenerator();

		// generate 1000 female names
		final List<Name> names = generator.generateNames(1000, Gender.FEMALE);

		System.out.println(names.get(0));

	}

}
