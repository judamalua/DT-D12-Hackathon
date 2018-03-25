
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tool;

@Component
@Transactional
public class ToolToStringConverter implements Converter<Tool, String> {

	@Override
	public String convert(final Tool tool) {
		String result;

		if (tool == null)
			result = null;
		else
			result = String.valueOf(tool.getId());

		return result;
	}

}

