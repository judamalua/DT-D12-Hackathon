
package annotations;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.SafeHtml;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class MapSafeHtmlValidator implements ConstraintValidator<MapSafeHtml, Map<String, String>> {

	private Whitelist	whitelist;


	public void initializeSafe(final SafeHtml constraintAnn) {
		switch ((constraintAnn).whitelistType()) {
		case BASIC:
			this.whitelist = Whitelist.basic();
			break;
		case BASIC_WITH_IMAGES:
			this.whitelist = Whitelist.basicWithImages();
			break;
		case NONE:
			this.whitelist = Whitelist.none();
			break;
		case RELAXED:
			this.whitelist = Whitelist.relaxed();
			break;
		case SIMPLE_TEXT:
			this.whitelist = Whitelist.simpleText();
			break;
		}
		this.whitelist.addTags(constraintAnn.additionalTags());
	}

	public boolean isValidSafe(final CharSequence value, final ConstraintValidatorContext context) {
		if (value == null)
			return true;
		return Jsoup.isValid(value.toString(), this.whitelist);
	}

	@Override
	public void initialize(final MapSafeHtml constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(final Map<String, String> value, final ConstraintValidatorContext context) {
		boolean res = true;
		for (final String s : value.values())
			if (!this.isValidSafe(s, context)) {
				res = false;
				break;
			}
		return res;
	}

}
