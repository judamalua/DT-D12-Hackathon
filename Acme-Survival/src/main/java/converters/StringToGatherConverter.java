
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GatherRepository;
import domain.Gather;

@Component
@Transactional
public class StringToGatherConverter implements Converter<String, Gather> {

	@Autowired
	GatherRepository	gatherRepository;


	@Override
	public Gather convert(final String text) {
		Gather result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.gatherRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
