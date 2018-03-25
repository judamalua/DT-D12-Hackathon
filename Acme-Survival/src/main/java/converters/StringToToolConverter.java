
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ToolRepository;
import domain.Tool;

@Component
@Transactional
public class StringToToolConverter implements Converter<String, Tool> {

	@Autowired
	ToolRepository	toolRepository;


	@Override
	public Tool convert(final String text) {
		Tool result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.toolRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
