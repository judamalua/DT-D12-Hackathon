
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import annotations.MapLength;
import annotations.MapNotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@javax.persistence.Index(columnList = "finalMode")
})
public class Event extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Map<String, String>	name;
	private Map<String, String>	description;
	private Integer				health;
	private Integer				water;
	private Integer				food;
	private boolean				finalMode;
	private boolean				findCharacter;


	@MapLength(min = 5, max = 50)
	@MapNotBlank
	@ElementCollection
	public Map<String, String> getName() {
		return this.name;
	}

	@MapLength(min = 5, max = 1000)
	@MapNotBlank
	@ElementCollection
	public Map<String, String> getDescription() {
		return this.description;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
	}

	public void setDescription(final Map<String, String> description) {
		this.description = description;
	}

	@Range(min = -100, max = 100)
	@NotNull
	public Integer getHealth() {
		return this.health;
	}

	public void setHealth(final Integer health) {
		this.health = health;
	}

	@Range(min = -100, max = 100)
	@NotNull
	public Integer getWater() {
		return this.water;
	}

	public void setWater(final Integer water) {
		this.water = water;
	}

	@Range(min = -100, max = 100)
	@NotNull
	public Integer getFood() {
		return this.food;
	}

	public void setFood(final Integer food) {
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
	public ItemDesign getItemDesign() {
		return this.itemDesign;
	}

	public void setItemDesign(final ItemDesign itemDesign) {
		this.itemDesign = itemDesign;

	}
}
