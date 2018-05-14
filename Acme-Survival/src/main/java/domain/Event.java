
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import annotations.MapNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Map<String, String>  name;
    private Map<String, String>  description;
	private double	health;
	private double	water;
	private double	food;
	private boolean	finalMode;
	private boolean	findCharacter;


	@MapNotBlank
	@ElementCollection
	public Map<String,String> getName(){
		return this.name;
	}

	@MapNotBlank
	@ElementCollection
	public Map<String,String> getDescription(){
		return this.description;
	}
	
	public void setName(final Map<String,String> name) {
		this.name = name;
	}
	
	public void setDescription(final Map<String,String> description) {
		this.description = description;
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
	public ItemDesign getItemDesign() {
		return this.itemDesign;
	}

	public void setItemDesign(final ItemDesign itemDesign) {
		this.itemDesign = itemDesign;

	}
}
