
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ShelterRepository;
import domain.Shelter;

@Component
@Transactional
public class StringToShelterConverter implements Converter<String, Shelter> {

	@Autowired
	ShelterRepository	shelterRepository;


	@Override
	public Shelter convert(final String text) {
		Shelter result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.shelterRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
