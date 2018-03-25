
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Player extends Actor {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Refuge>	refuges;


	@Valid
	@ManyToMany
	public Collection<Refuge> getrefuges() {
		return this.refuges;
	}

	public void setrefuges(final Collection<Refuge> refuges) {
		this.refuges = refuges;

	}
}
