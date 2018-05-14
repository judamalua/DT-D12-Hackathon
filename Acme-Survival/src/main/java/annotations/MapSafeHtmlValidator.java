
package annotations;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class MapSafeHtmlValidator implements ConstraintValidator<MapSafeHtml, Map<String, String>> {

	private Whitelist	whitelist;


	@Override
	public void initialize(final MapSafeHtml arg0) {
		this.whitelist = Whitelist.none();
	}

	public boolean isValidSafe(final CharSequence value) {
		if (value == null)
			return true;
		return Jsoup.isValid(value.toString(), this.whitelist);
	}

	@Override
	public boolean isValid(final Map<String, String> value, final ConstraintValidatorContext context) {
		boolean res = true;
		for (final String s : value.values())
			if (!this.isValidSafe(s)) {
				res = false;
				break;
			}
		return res;
	}

}
