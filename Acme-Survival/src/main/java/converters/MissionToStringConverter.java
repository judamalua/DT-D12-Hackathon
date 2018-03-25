
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Mission;

@Component
@Transactional
public class MissionToStringConverter implements Converter<Mission, String> {

	@Override
	public String convert(final Mission mission) {
		String result;

		if (mission == null)
			result = null;
		else
			result = String.valueOf(mission.getId());

		return result;
	}

}

