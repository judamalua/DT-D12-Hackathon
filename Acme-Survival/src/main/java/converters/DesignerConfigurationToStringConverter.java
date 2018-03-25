
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.DesignerConfiguration;

@Component
@Transactional
public class DesignerConfigurationToStringConverter implements Converter<DesignerConfiguration, String> {

	@Override
	public String convert(final DesignerConfiguration designerConfiguration) {
		String result;

		if (designerConfiguration == null)
			result = null;
		else
			result = String.valueOf(designerConfiguration.getId());

		return result;
	}

}

