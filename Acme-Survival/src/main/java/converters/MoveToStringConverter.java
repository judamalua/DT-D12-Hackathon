
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Move;

@Component
@Transactional
public class MoveToStringConverter implements Converter<Move, String> {

	@Override
	public String convert(final Move move) {
		String result;

		if (move == null)
			result = null;
		else
			result = String.valueOf(move.getId());

		return result;
	}

}

