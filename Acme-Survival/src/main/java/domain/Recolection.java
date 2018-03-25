
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Recolection extends Mission {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Character	character;
	private Location	location;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Character getcharacter() {
		return this.character;
	}

	public void setcharacter(final Character character) {
		this.character = character;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Location getlocation() {
		return this.location;
	}

	public void setlocation(final Location location) {
		this.location = location;

	}
}
