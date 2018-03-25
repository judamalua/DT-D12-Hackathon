
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Slider;

@Component
@Transactional
public class SliderToStringConverter implements Converter<Slider, String> {

	@Override
	public String convert(final Slider slider) {
		String result;

		if (slider == null)
			result = null;
		else
			result = String.valueOf(slider.getId());

		return result;
	}

}

