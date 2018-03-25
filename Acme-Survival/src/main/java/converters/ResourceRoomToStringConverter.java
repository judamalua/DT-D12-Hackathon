
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ResourceRoom;

@Component
@Transactional
public class ResourceRoomToStringConverter implements Converter<ResourceRoom, String> {

	@Override
	public String convert(final ResourceRoom resourceRoom) {
		String result;

		if (resourceRoom == null)
			result = null;
		else
			result = String.valueOf(resourceRoom.getId());

		return result;
	}

}

