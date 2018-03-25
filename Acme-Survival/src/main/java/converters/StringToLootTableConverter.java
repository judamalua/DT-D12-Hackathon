
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LootTableRepository;
import domain.LootTable;

@Component
@Transactional
public class StringToLootTableConverter implements Converter<String, LootTable> {

	@Autowired
	LootTableRepository	lootTableRepository;


	@Override
	public LootTable convert(final String text) {
		LootTable result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.lootTableRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
