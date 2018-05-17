
package annotations;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import services.ConfigurationService;

public class MapSupportedLanguagesValidator implements ConstraintValidator<MapSupportedLanguages, Map<String, String>> {

	@Autowired
	private ConfigurationService	configurationService;


	@Override
	public void initialize(final MapSupportedLanguages constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Map<String, String> value, final ConstraintValidatorContext context) {
		boolean res = true;
		if (value == null) {
			res = false;
			return res;
		}

		res = this.configurationService.findConfiguration().getLanguages().containsAll(value.keySet());

		return res;
	}
}
