
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RoomDesignRepository;
import domain.RoomDesign;

@Component
@Transactional
public class StringToRoomDesignConverter implements Converter<String, RoomDesign> {

	@Autowired
	RoomDesignRepository	roomDesignRepository;


	@Override
	public RoomDesign convert(final String text) {
		RoomDesign result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.roomDesignRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
