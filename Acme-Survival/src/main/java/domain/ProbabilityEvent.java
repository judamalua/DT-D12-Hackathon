
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class ProbabilityEvent extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private double	value;


	@Range(min = 0, max = 1)
	public double getValue() {
		return this.value;
	}

	public void setValue(final double value) {
		this.value = value;
	}


	// Relationships ----------------------------------------------------------
	private Event	event;


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
