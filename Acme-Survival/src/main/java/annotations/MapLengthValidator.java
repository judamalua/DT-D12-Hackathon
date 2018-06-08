
package annotations;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MapLengthValidator implements ConstraintValidator<MapLength, Map<String, String>> {

	private int	max;
	private int	min;


	@Override
	public void initialize(final MapLength constraintAnnotation) {
		this.max = constraintAnnotation.max();
		this.min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(final Map<String, String> value, final ConstraintValidatorContext context) {
		boolean res = true;

		if (value == null) {
			res = false;
			return res;
		}

		for (final String s : value.values()) {
			if (s.length() < this.min || s.length() > this.max) {
				res = false;
				break;
			}
		}

		return res;
	}
}
