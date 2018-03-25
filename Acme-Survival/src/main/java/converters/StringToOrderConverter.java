
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OrderRepository;
import domain.Order;

@Component
@Transactional
public class StringToOrderConverter implements Converter<String, Order> {

	@Autowired
	OrderRepository	orderRepository;


	@Override
	public Order convert(final String text) {
		Order result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.orderRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
