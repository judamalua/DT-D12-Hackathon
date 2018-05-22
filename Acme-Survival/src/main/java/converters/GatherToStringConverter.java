
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Gather;

@Component
@Transactional
public class GatherToStringConverter implements Converter<Gather, String> {

	@Override
	public String convert(final Gather gather) {
		String result;

		if (gather == null)
			result = null;
		else
			result = String.valueOf(gather.getId());

		return result;
	}

}

