
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@javax.persistence.Index(columnList = "finalMode")
})
public class Resource extends ItemDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private double	water;
	private double	food;
	private double	wood;
	private double	metal;


	@Min(0)
	public double getWater() {
		return this.water;
	}

	public void setWater(final double water) {
		this.water = water;
	}

	@Min(0)
	public double getFood() {
		return this.food;
	}

	public void setFood(final double food) {
		this.food = food;
	}

	@Min(0)
	public double getWood() {
		return this.wood;
	}

	public void setWood(final double wood) {
		this.wood = wood;
	}

	@Min(0)
	public double getMetal() {
		return this.metal;
	}

	public void setMetal(final double metal) {
		this.metal = metal;
	}

	// Relationships ----------------------------------------------------------

}
