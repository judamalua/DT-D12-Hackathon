
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.DesignerConfigurationRepository;
import domain.DesignerConfiguration;

@Component
@Transactional
public class StringToDesignerConfigurationConverter implements Converter<String, DesignerConfiguration> {

	@Autowired
	DesignerConfigurationRepository	designerConfigurationRepository;


	@Override
	public DesignerConfiguration convert(final String text) {
		DesignerConfiguration result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.designerConfigurationRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
