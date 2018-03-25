
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Recolection;

@Component
@Transactional
public class RecolectionToStringConverter implements Converter<Recolection, String> {

	@Override
	public String convert(final Recolection recolection) {
		String result;

		if (recolection == null)
			result = null;
		else
			result = String.valueOf(recolection.getId());

		return result;
	}

}

