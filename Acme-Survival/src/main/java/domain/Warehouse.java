
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Warehouse extends RoomDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Integer	itemCapacity;


	@NotNull
	public Integer getItemCapacity() {
		return this.itemCapacity;
	}

	public void setItemCapacity(final Integer itemCapacity) {
		this.itemCapacity = itemCapacity;
	}

	// Relationships ----------------------------------------------------------

}
