
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Tool extends ItemDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private double	strength;
	private double	luck;
	private double	capacity;


	@Min(0)
	public double getStrength() {
		return this.strength;
	}

	public void setStrength(final double strength) {
		this.strength = strength;
	}

	@Min(0)
	public double getLuck() {
		return this.luck;
	}

	public void setLuck(final double luck) {
		this.luck = luck;
	}

	@Min(0)
	public double getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final double capacity) {
		this.capacity = capacity;
	}

	// Relationships ----------------------------------------------------------

}
