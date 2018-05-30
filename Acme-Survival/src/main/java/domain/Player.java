
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Player extends Actor {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Shelter>	shelters;


	@NotNull
	@Valid
	@ManyToMany
	public Collection<Shelter> getShelters() {
		return this.shelters;
	}

	public void setShelters(final Collection<Shelter> shelters) {
		this.shelters = shelters;

	}
}
