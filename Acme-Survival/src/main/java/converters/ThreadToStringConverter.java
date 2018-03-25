
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Thread;

@Component
@Transactional
public class ThreadToStringConverter implements Converter<Thread, String> {

	@Override
	public String convert(final Thread thread) {
		String result;

		if (thread == null)
			result = null;
		else
			result = String.valueOf(thread.getId());

		return result;
	}

}

