
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Barrack extends RoomDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private int	characterCapacity;


	public int getCharacterCapacity() {
		return this.characterCapacity;
	}

	public void setCharacterCapacity(final int characterCapacity) {
		this.characterCapacity = characterCapacity;
	}

	// Relationships ----------------------------------------------------------

}
