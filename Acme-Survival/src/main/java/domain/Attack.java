
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
	private Refuge	attacker;
	private Refuge	defendant;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Refuge getAttacker() {
		return this.attacker;
	}

	public void setAttacker(final Refuge attacker) {
		this.attacker = attacker;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Refuge getDefendant() {
		return this.defendant;
	}

	public void setDefendant(final Refuge defendant) {
		this.defendant = defendant;

	}
}
