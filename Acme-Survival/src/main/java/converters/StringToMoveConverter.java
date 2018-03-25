
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MoveRepository;
import domain.Move;

@Component
@Transactional
public class StringToMoveConverter implements Converter<String, Move> {

	@Autowired
	MoveRepository	moveRepository;


	@Override
	public Move convert(final String text) {
		Move result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.moveRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
