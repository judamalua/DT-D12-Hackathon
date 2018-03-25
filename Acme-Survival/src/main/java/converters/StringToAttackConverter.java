
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AttackRepository;
import domain.Attack;

@Component
@Transactional
public class StringToAttackConverter implements Converter<String, Attack> {

	@Autowired
	AttackRepository	attackRepository;


	@Override
	public Attack convert(final String text) {
		Attack result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.attackRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
