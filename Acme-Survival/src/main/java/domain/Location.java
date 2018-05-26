
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import annotations.MapNotBlank;
import annotations.MapSafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Location extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Map<String, String>	name;
	private String				point_a;
	private String				point_b;
	private String				point_c;
	private String				point_d;
	private boolean				finalMode;


	@Length(min = 5, max = 50)
	@NotNull
	@MapNotBlank
	@MapSafeHtml
	@ElementCollection
	public Map<String, String> getName() {
		return this.name;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
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
	private LootTable	lootTable;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public LootTable getLootTable() {
		return this.lootTable;
	}

	public void setLootTable(final LootTable lootTable) {
		this.lootTable = lootTable;

	}
}
