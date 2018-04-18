
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Room extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private int	resistance;


	public int getResistance() {
		return this.resistance;
	}

	public void setResistance(final int resistance) {
		this.resistance = resistance;
	}


	// Relationships ----------------------------------------------------------
	private Refuge		refuge;
	private RoomDesign	roomDesign;


	@Valid
	@NotNull
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	public Refuge getRefuge() {
		return this.refuge;
	}

	public void setRefuge(final Refuge refuge) {
		this.refuge = refuge;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public RoomDesign getRoomDesign() {
		return this.roomDesign;
	}

	public void setRoomDesign(final RoomDesign roomDesign) {
		this.roomDesign = roomDesign;

	}
}
