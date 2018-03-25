
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	name_en;
	private String	name_es;
	private String	description_en;
	private String	description_es;
	private double	health;
	private double	water;
	private double	food;
	private boolean	finalMode;
	private boolean	findCharacter;


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

	public double getHealth() {
		return this.health;
	}

	public void setHealth(final double health) {
		this.health = health;
	}

	public double getWater() {
		return this.water;
	}

	public void setWater(final double water) {
		this.water = water;
	}

	public double getFood() {
		return this.food;
	}

	public void setFood(final double food) {
		this.food = food;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	public boolean getFindCharacter() {
		return this.findCharacter;
	}

	public void setFindCharacter(final boolean findCharacter) {
		this.findCharacter = findCharacter;
	}


	// Relationships ----------------------------------------------------------
	private ItemDesign	itemDesign;


	@Valid
	@ManyToOne(optional = true)
	public ItemDesign getitemDesign() {
		return this.itemDesign;
	}

	public void setitemDesign(final ItemDesign itemDesign) {
		this.itemDesign = itemDesign;

	}
}
