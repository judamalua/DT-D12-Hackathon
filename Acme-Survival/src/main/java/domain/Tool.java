
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
	private int	strength;
	private int	luck;
	private int	capacity;


	@Min(0)
	public int getStrength() {
		return this.strength;
	}

	public void setStrength(final int strength) {
		this.strength = strength;
	}

	@Min(0)
	public int getLuck() {
		return this.luck;
	}

	public void setLuck(final int luck) {
		this.luck = luck;
	}

	@Min(0)
	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final int capacity) {
		this.capacity = capacity;
	}

	// Relationships ----------------------------------------------------------

}
