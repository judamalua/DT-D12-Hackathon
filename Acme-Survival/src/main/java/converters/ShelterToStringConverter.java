
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Shelter;

@Component
@Transactional
public class ShelterToStringConverter implements Converter<Shelter, String> {

	@Override
	public String convert(final Shelter shelter) {
		String result;

		if (shelter == null)
			result = null;
		else
			result = String.valueOf(shelter.getId());

		return result;
	}

}
