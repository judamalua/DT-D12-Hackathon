
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Room extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Shelter		shelter;
	private RoomDesign	roomDesign;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Shelter getShelter() {
		return this.shelter;
	}

	public void setShelter(final Shelter shelter) {
		this.shelter = shelter;

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
