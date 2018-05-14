
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class ResourceRoom extends RoomDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Double	water;
	private Double	food;
	private Double	metal;
	private Double	wood;


	@Min(0)
	@NotNull
	public Double getWater() {
		return this.water;
	}

	public void setWater(final Double water) {
		this.water = water;
	}

	@Min(0)
	@NotNull
	public Double getFood() {
		return this.food;
	}

	public void setFood(final Double food) {
		this.food = food;
	}

	@Min(0)
	@NotNull
	public Double getMetal() {
		return this.metal;
	}

	public void setMetal(final Double metal) {
		this.metal = metal;
	}

	@Min(0)
	@NotNull
	public Double getWood() {
		return this.wood;
	}

	public void setWood(final Double wood) {
		this.wood = wood;
	}

	// Relationships ----------------------------------------------------------

}
