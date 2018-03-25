
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RecolectionRepository;
import domain.Recolection;

@Component
@Transactional
public class StringToRecolectionConverter implements Converter<String, Recolection> {

	@Autowired
	RecolectionRepository	recolectionRepository;


	@Override
	public Recolection convert(final String text) {
		Recolection result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.recolectionRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
