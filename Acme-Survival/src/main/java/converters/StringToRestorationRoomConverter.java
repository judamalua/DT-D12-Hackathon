
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RestorationRoomRepository;
import domain.RestorationRoom;

@Component
@Transactional
public class StringToRestorationRoomConverter implements Converter<String, RestorationRoom> {

	@Autowired
	RestorationRoomRepository	restorationRoomRepository;


	@Override
	public RestorationRoom convert(final String text) {
		RestorationRoom result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.restorationRoomRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
