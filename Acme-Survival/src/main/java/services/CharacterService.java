
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CharacterRepository;
import domain.Character;

@Service
@Transactional
public class CharacterService {

	// Managed repository --------------------------------------------------

	@Autowired
	private CharacterRepository	characterRepository;


	// Supporting services --------------------------------------------------

	// Simple CRUD methods --------------------------------------------------

	public Character create() {
		Character result;

		result = new Character();

		return result;
	}

	public Collection<Character> findAll() {

		Collection<Character> result;

		Assert.notNull(this.characterRepository);
		result = this.characterRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public Character findOne(final int characterId) {

		Character result;

		result = this.characterRepository.findOne(characterId);

		return result;

	}

	public Character save(final Character character) {

		assert character != null;

		Character result;

		result = this.characterRepository.save(character);

		return result;

	}

	public void delete(final Character character) {

		assert character != null;
		assert character.getId() != 0;

		Assert.isTrue(this.characterRepository.exists(character.getId()));

		this.characterRepository.delete(character);

	}

	public Collection<Character> findCharactersByRefuge(final int refugeId) {
		Assert.isTrue(refugeId != 0);

		Collection<Character> result;

		result = this.characterRepository.findCharactersByRefuge(refugeId);

		return result;
	}
}
