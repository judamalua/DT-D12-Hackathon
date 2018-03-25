
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OrderLineRepository;
import domain.OrderLine;

@Component
@Transactional
public class StringToOrderLineConverter implements Converter<String, OrderLine> {

	@Autowired
	OrderLineRepository	orderLineRepository;


	@Override
	public OrderLine convert(final String text) {
		OrderLine result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.orderLineRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
