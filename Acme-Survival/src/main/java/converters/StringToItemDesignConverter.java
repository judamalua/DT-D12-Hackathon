
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ItemDesignRepository;
import domain.ItemDesign;

@Component
@Transactional
public class StringToItemDesignConverter implements Converter<String, ItemDesign> {

	@Autowired
	ItemDesignRepository	itemDesignRepository;


	@Override
	public ItemDesign convert(final String text) {
		ItemDesign result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.itemDesignRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
