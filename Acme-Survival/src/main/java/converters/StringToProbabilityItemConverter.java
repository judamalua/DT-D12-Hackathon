
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProbabilityItemRepository;
import domain.ProbabilityItem;

@Component
@Transactional
public class StringToProbabilityItemConverter implements Converter<String, ProbabilityItem> {

	@Autowired
	ProbabilityItemRepository	probabilityItemRepository;


	@Override
	public ProbabilityItem convert(final String text) {
		ProbabilityItem result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.probabilityItemRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
