
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RefugeRepository;
import domain.Refuge;

@Component
@Transactional
public class StringToRefugeConverter implements Converter<String, Refuge> {

	@Autowired
	RefugeRepository	refugeRepository;


	@Override
	public Refuge convert(final String text) {
		Refuge result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.refugeRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
