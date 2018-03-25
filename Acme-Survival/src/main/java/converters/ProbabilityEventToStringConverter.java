
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProbabilityEvent;

@Component
@Transactional
public class ProbabilityEventToStringConverter implements Converter<ProbabilityEvent, String> {

	@Override
	public String convert(final ProbabilityEvent probabilityEvent) {
		String result;

		if (probabilityEvent == null)
			result = null;
		else
			result = String.valueOf(probabilityEvent.getId());

		return result;
	}

}

