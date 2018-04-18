
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Warehouse extends RoomDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private int	itemCapacity;


	public int getItemCapacity() {
		return this.itemCapacity;
	}

	public void setItemCapacity(final int itemCapacity) {
		this.itemCapacity = itemCapacity;
	}

	// Relationships ----------------------------------------------------------

}
