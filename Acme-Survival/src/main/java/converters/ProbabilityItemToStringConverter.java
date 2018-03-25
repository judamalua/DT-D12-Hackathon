
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProbabilityItem;

@Component
@Transactional
public class ProbabilityItemToStringConverter implements Converter<ProbabilityItem, String> {

	@Override
	public String convert(final ProbabilityItem probabilityItem) {
		String result;

		if (probabilityItem == null)
			result = null;
		else
			result = String.valueOf(probabilityItem.getId());

		return result;
	}

}

