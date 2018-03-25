
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LootTable;

@Component
@Transactional
public class LootTableToStringConverter implements Converter<LootTable, String> {

	@Override
	public String convert(final LootTable lootTable) {
		String result;

		if (lootTable == null)
			result = null;
		else
			result = String.valueOf(lootTable.getId());

		return result;
	}

}

