
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Barrack extends RoomDesign {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Integer	characterCapacity;


	@NotNull
	public Integer getCharacterCapacity() {
		return this.characterCapacity;
	}

	public void setCharacterCapacity(final Integer characterCapacity) {
		this.characterCapacity = characterCapacity;
	}

	// Relationships ----------------------------------------------------------

}
