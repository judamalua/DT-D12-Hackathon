
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.DesignerRepository;
import domain.Designer;

@Component
@Transactional
public class StringToDesignerConverter implements Converter<String, Designer> {

	@Autowired
	DesignerRepository	designerRepository;


	@Override
	public Designer convert(final String text) {
		Designer result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.designerRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
