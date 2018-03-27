
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Warehouse;

@Component
@Transactional
public class WerehouseToStringConverter implements Converter<Warehouse, String> {

	@Override
	public String convert(final Warehouse werehouse) {
		String result;

		if (werehouse == null)
			result = null;
		else
			result = String.valueOf(werehouse.getId());

		return result;
	}

}

