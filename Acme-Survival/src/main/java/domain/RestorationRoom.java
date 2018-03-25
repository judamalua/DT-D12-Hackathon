
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class RestorationRoom extends RoomDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private double	health;
	private double	food;
	private double	water;


	public double getHealth() {
		return this.health;
	}

	public void setHealth(final double health) {
		this.health = health;
	}

	public double getFood() {
		return this.food;
	}

	public void setFood(final double food) {
		this.food = food;
	}

	public double getWater() {
		return this.water;
	}

	public void setWater(final double water) {
		this.water = water;
	}

	// Relationships ----------------------------------------------------------

}
