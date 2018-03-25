
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Barrack;

@Component
@Transactional
public class BarrackToStringConverter implements Converter<Barrack, String> {

	@Override
	public String convert(final Barrack barrack) {
		String result;

		if (barrack == null)
			result = null;
		else
			result = String.valueOf(barrack.getId());

		return result;
	}

}

