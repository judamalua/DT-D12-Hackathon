
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Werehouse;

@Component
@Transactional
public class WerehouseToStringConverter implements Converter<Werehouse, String> {

	@Override
	public String convert(final Werehouse werehouse) {
		String result;

		if (werehouse == null)
			result = null;
		else
			result = String.valueOf(werehouse.getId());

		return result;
	}

}

