
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SliderRepository;
import domain.Slider;

@Component
@Transactional
public class StringToSliderConverter implements Converter<String, Slider> {

	@Autowired
	SliderRepository	sliderRepository;


	@Override
	public Slider convert(final String text) {
		Slider result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.sliderRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
