
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Refuge;

@Component
@Transactional
public class RefugeToStringConverter implements Converter<Refuge, String> {

	@Override
	public String convert(final Refuge refuge) {
		String result;

		if (refuge == null)
			result = null;
		else
			result = String.valueOf(refuge.getId());

		return result;
	}

}

