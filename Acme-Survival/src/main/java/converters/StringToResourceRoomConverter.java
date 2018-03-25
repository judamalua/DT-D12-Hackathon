
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ResourceRoomRepository;
import domain.ResourceRoom;

@Component
@Transactional
public class StringToResourceRoomConverter implements Converter<String, ResourceRoom> {

	@Autowired
	ResourceRoomRepository	resourceRoomRepository;


	@Override
	public ResourceRoom convert(final String text) {
		ResourceRoom result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.resourceRoomRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
