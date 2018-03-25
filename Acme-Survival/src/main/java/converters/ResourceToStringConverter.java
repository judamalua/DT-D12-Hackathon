
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Resource;

@Component
@Transactional
public class ResourceToStringConverter implements Converter<Resource, String> {

	@Override
	public String convert(final Resource resource) {
		String result;

		if (resource == null)
			result = null;
		else
			result = String.valueOf(resource.getId());

		return result;
	}

}

