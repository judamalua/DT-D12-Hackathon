
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PlayerRepository;
import domain.Player;

@Component
@Transactional
public class StringToPlayerConverter implements Converter<String, Player> {

	@Autowired
	PlayerRepository	playerRepository;


	@Override
	public Player convert(final String text) {
		Player result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.playerRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
