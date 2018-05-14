
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class RestorationRoom extends RoomDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Double	health;
	private Double	food;
	private Double	water;


	@Min(0)
	@NotNull
	public Double getHealth() {
		return this.health;
	}

	public void setHealth(final Double health) {
		this.health = health;
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
	public Double getWater() {
		return this.water;
	}

	public void setWater(final Double water) {
		this.water = water;
	}

	// Relationships ----------------------------------------------------------

}
