
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public abstract class RoomDesign extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	name_en;
	private String	name_es;
	private String	description_en;
	private String	description_es;
	private int		maxResistance;
	private double	costWood;
	private double	costMetal;
	private boolean	finalMode;
	private int		maxCapacityCharacters;


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

	@NotBlank
	@SafeHtml
	public String getDescription_en() {
		return this.description_en;
	}

	public void setDescription_en(final String description_en) {
		this.description_en = description_en;
	}

	@NotBlank
	@SafeHtml
	public String getDescription_es() {
		return this.description_es;
	}

	public void setDescription_es(final String description_es) {
		this.description_es = description_es;
	}

	public int getMaxResistance() {
		return this.maxResistance;
	}

	public void setMaxResistance(final int maxResistance) {
		this.maxResistance = maxResistance;
	}

	public double getCostWood() {
		return this.costWood;
	}

	public void setCostWood(final double costWood) {
		this.costWood = costWood;
	}

	public double getCostMetal() {
		return this.costMetal;
	}

	public void setCostMetal(final double costMetal) {
		this.costMetal = costMetal;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	public int getMaxCapacityCharacters() {
		return this.maxCapacityCharacters;
	}

	public void setMaxCapacityCharacters(final int maxCapacityCharacters) {
		this.maxCapacityCharacters = maxCapacityCharacters;
	}

	// Relationships ----------------------------------------------------------

}
