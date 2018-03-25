
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.RestorationRoom;

@Component
@Transactional
public class RestorationRoomToStringConverter implements Converter<RestorationRoom, String> {

	@Override
	public String convert(final RestorationRoom restorationRoom) {
		String result;

		if (restorationRoom == null)
			result = null;
		else
			result = String.valueOf(restorationRoom.getId());

		return result;
	}

}

