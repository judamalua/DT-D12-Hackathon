
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Attack;

@Component
@Transactional
public class AttackToStringConverter implements Converter<Attack, String> {

	@Override
	public String convert(final Attack attack) {
		String result;

		if (attack == null)
			result = null;
		else
			result = String.valueOf(attack.getId());

		return result;
	}

}

