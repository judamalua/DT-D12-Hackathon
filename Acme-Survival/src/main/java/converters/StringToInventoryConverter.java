
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.InventoryRepository;
import domain.Inventory;

@Component
@Transactional
public class StringToInventoryConverter implements Converter<String, Inventory> {

	@Autowired
	InventoryRepository	inventoryRepository;


	@Override
	public Inventory convert(final String text) {
		Inventory result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.inventoryRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
