
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ItemDesign;

@Component
@Transactional
public class ItemDesignToStringConverter implements Converter<ItemDesign, String> {

	@Override
	public String convert(final ItemDesign itemDesign) {
		String result;

		if (itemDesign == null)
			result = null;
		else
			result = String.valueOf(itemDesign.getId());

		return result;
	}

}

