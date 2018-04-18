
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class LootTable extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	name;
	private boolean	finalMode;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}


	// Relationships ----------------------------------------------------------
	private Collection<ProbabilityEvent>	probabilityEvents;
	private Collection<ProbabilityItem>		probabilityItems;
	private Collection<Location>			locations;


	@Valid
	@OneToMany
	public Collection<ProbabilityEvent> getProbabilityEvents() {
		return this.probabilityEvents;
	}

	public void setProbabilityEvents(final Collection<ProbabilityEvent> probabilityEvents) {
		this.probabilityEvents = probabilityEvents;

	}
	@Valid
	@OneToMany
	public Collection<ProbabilityItem> getProbabilityItems() {
		return this.probabilityItems;
	}

	public void setProbabilityItems(final Collection<ProbabilityItem> probabilityItems) {
		this.probabilityItems = probabilityItems;

	}
	@Valid
	@OneToMany
	public Collection<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(final Collection<Location> locations) {
		this.locations = locations;

	}
}
