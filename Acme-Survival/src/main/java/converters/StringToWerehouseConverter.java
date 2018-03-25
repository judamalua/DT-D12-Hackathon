
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.WerehouseRepository;
import domain.Werehouse;

@Component
@Transactional
public class StringToWerehouseConverter implements Converter<String, Werehouse> {

	@Autowired
	WerehouseRepository	werehouseRepository;


	@Override
	public Werehouse convert(final String text) {
		Werehouse result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.werehouseRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
