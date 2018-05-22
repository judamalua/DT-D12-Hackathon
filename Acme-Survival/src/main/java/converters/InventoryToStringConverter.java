
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Inventory;

@Component
@Transactional
public class InventoryToStringConverter implements Converter<Inventory, String> {

	@Override
	public String convert(final Inventory event) {
		String result;

		if (event == null)
			result = null;
		else
			result = String.valueOf(event.getId());

		return result;
	}

}
