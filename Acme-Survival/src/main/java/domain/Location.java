
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Location extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	name_en;
	private String	name_es;
	private String	point_a;
	private String	point_b;
	private String	point_c;
	private String	point_d;
	private boolean	finalMode;


	@NotBlank
	@SafeHtml
	public String getName_en() {
		return this.name_en;
	}

	public void setName_en(final String name_en) {
		this.name_en = name_en;
	}

	@NotBlank
	@SafeHtml
	public String getName_es() {
		return this.name_es;
	}

	public void setName_es(final String name_es) {
		this.name_es = name_es;
	}

	@Pattern(regexp = "^(\\-?\\d+(\\.\\d+)?),\\w*(\\-?\\d+(\\.\\d+)?)$")
	@NotBlank
	@SafeHtml
	public String getPoint_a() {
		return this.point_a;
	}

	public void setPoint_a(final String point_a) {
		this.point_a = point_a;
	}

	@Pattern(regexp = "^(\\-?\\d+(\\.\\d+)?),\\w*(\\-?\\d+(\\.\\d+)?)$")
	@NotBlank
	@SafeHtml
	public String getPoint_b() {
		return this.point_b;
	}

	public void setPoint_b(final String point_b) {
		this.point_b = point_b;
	}

	@Pattern(regexp = "^(\\-?\\d+(\\.\\d+)?),\\w*(\\-?\\d+(\\.\\d+)?)$")
	@NotBlank
	@SafeHtml
	public String getPoint_c() {
		return this.point_c;
	}

	public void setPoint_c(final String point_c) {
		this.point_c = point_c;
	}

	@Pattern(regexp = "^(\\-?\\d+(\\.\\d+)?),\\w*(\\-?\\d+(\\.\\d+)?)$")
	@NotBlank
	@SafeHtml
	public String getPoint_d() {
		return this.point_d;
	}

	public void setPoint_d(final String point_d) {
		this.point_d = point_d;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	// Relationships ----------------------------------------------------------

}
