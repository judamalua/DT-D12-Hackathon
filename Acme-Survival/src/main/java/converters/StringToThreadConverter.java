
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ThreadRepository;
import domain.Thread;

@Component
@Transactional
public class StringToThreadConverter implements Converter<String, Thread> {

	@Autowired
	ThreadRepository	threadRepository;


	@Override
	public Thread convert(final String text) {
		Thread result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.threadRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
