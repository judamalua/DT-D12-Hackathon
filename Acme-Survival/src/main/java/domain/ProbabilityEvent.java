
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class ProbabilityEvent extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private double	value;


	public double getValue() {
		return this.value;
	}

	public void setValue(final double value) {
		this.value = value;
	}


	// Relationships ----------------------------------------------------------
	private Event event;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Event getEvent() {
		return this.event;
	}

	public void setEvent(final Event event) {
		this.event = event;

	}
}
