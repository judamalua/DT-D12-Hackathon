
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BarrackRepository;
import domain.Barrack;

@Component
@Transactional
public class StringToBarrackConverter implements Converter<String, Barrack> {

	@Autowired
	BarrackRepository	barrackRepository;


	@Override
	public Barrack convert(final String text) {
		Barrack result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.barrackRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
