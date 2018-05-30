
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Attack extends Mission {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Shelter	attacker;
	private Shelter	defendant;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Shelter getAttacker() {
		return this.attacker;
	}

	public void setAttacker(final Shelter attacker) {
		this.attacker = attacker;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Shelter getDefendant() {
		return this.defendant;
	}

	public void setDefendant(final Shelter defendant) {
		this.defendant = defendant;

	}
}
