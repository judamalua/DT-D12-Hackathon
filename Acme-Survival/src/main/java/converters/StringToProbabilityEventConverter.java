
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProbabilityEventRepository;
import domain.ProbabilityEvent;

@Component
@Transactional
public class StringToProbabilityEventConverter implements Converter<String, ProbabilityEvent> {

	@Autowired
	ProbabilityEventRepository	probabilityEventRepository;


	@Override
	public ProbabilityEvent convert(final String text) {
		ProbabilityEvent result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.probabilityEventRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
