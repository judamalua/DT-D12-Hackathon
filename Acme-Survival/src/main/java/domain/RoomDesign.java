
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import annotations.MapLength;
import annotations.MapNotBlank;
import annotations.MapSafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public abstract class RoomDesign extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Map<String, String>	name;
	private Map<String, String>	description;
	private Double				costWood;
	private Double				costMetal;
	private boolean				finalMode;
	private Integer				maxCapacityCharacters;


	@MapLength(min = 5, max = 50)
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

	@MapLength(min = 5, max = 1000)
	@NotNull
	@MapNotBlank
	@MapSafeHtml
	@ElementCollection
	public Map<String, String> getDescription() {
		return this.description;
	}

	public void setDescription(final Map<String, String> description) {
		this.description = description;
	}

	@Min(0)
	@NotNull
	public Double getCostWood() {
		return this.costWood;
	}

	public void setCostWood(final Double costWood) {
		this.costWood = costWood;
	}

	@Min(0)
	@NotNull
	public Double getCostMetal() {
		return this.costMetal;
	}

	public void setCostMetal(final Double costMetal) {
		this.costMetal = costMetal;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	@Min(1)
	@NotNull
	public Integer getMaxCapacityCharacters() {
		return this.maxCapacityCharacters;
	}

	public void setMaxCapacityCharacters(final Integer maxCapacityCharacters) {
		this.maxCapacityCharacters = maxCapacityCharacters;
	}

	// Relationships ----------------------------------------------------------

}
