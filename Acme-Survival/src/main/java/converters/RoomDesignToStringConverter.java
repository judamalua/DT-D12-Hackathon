
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.RoomDesign;

@Component
@Transactional
public class RoomDesignToStringConverter implements Converter<RoomDesign, String> {

	@Override
	public String convert(final RoomDesign roomDesign) {
		String result;

		if (roomDesign == null)
			result = null;
		else
			result = String.valueOf(roomDesign.getId());

		return result;
	}

}

