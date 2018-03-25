
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Resource extends ItemDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private double	water;
	private double	food;
	private double	wood;
	private double	metal;


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

	public double getWood() {
		return this.wood;
	}

	public void setWood(final double wood) {
		this.wood = wood;
	}

	public double getMetal() {
		return this.metal;
	}

	public void setMetal(final double metal) {
		this.metal = metal;
	}

	// Relationships ----------------------------------------------------------

}
