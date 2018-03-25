
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CharacterRepository;
import domain.Character;

@Component
@Transactional
public class StringToCharacterConverter implements Converter<String, Character> {

	@Autowired
	CharacterRepository	characterRepository;


	@Override
	public Character convert(final String text) {
		Character result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.characterRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
