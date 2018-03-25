
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Character;

@Component
@Transactional
public class CharacterToStringConverter implements Converter<Character, String> {

	@Override
	public String convert(final Character character) {
		String result;

		if (character == null)
			result = null;
		else
			result = String.valueOf(character.getId());

		return result;
	}

}

