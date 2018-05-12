
package annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({
	METHOD, FIELD, ANNOTATION_TYPE
})
@Retention(RUNTIME)
@Constraint(validatedBy = MapSupportedLanguagesValidator.class)
@Documented
public @interface MapSupportedLanguages {

	String message() default "{master.page.MapSupportedLanguage.validation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
