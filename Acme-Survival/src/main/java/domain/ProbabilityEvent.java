
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

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
	private Collection<Event>	events;


	@Valid
	@OneToMany
	public Collection<Event> getEvents() {
		return this.events;
	}

	public void setEvents(final Collection<Event> events) {
		this.events = events;

	}
}
