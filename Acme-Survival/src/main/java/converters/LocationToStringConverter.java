
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Location;

@Component
@Transactional
public class LocationToStringConverter implements Converter<Location, String> {

	@Override
	public String convert(final Location location) {
		String result;

		if (location == null)
			result = null;
		else
			result = String.valueOf(location.getId());

		return result;
	}

}

