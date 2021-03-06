
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MissionRepository;
import domain.Mission;

@Component
@Transactional
public class StringToMissionConverter implements Converter<String, Mission> {

	@Autowired
	MissionRepository	missionRepository;


	@Override
	public Mission convert(final String text) {
		Mission result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.missionRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
