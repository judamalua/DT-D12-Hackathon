
package annotations;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MapNotBlankValidator implements ConstraintValidator<MapNotBlank, Map<String, String>> {

	@Override
	public void initialize(final MapNotBlank constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Map<String, String> value, final ConstraintValidatorContext context) {
		boolean res = true;

		if (value == null) {
			res = false;
			return res;
		}

		for (final String s : value.values())
			if (s == null || s.equals("")) {
				res = false;
				break;
			}

		return res;
	}
}
