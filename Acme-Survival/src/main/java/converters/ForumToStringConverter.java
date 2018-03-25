
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Forum;

@Component
@Transactional
public class ForumToStringConverter implements Converter<Forum, String> {

	@Override
	public String convert(final Forum forum) {
		String result;

		if (forum == null)
			result = null;
		else
			result = String.valueOf(forum.getId());

		return result;
	}

}

