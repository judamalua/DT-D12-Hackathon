
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Move extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Date	startDate;
	private Date	endDate;


	@Past
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}


	// Relationships ----------------------------------------------------------
	private Location	location;
	private Refuge		refuge;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Location getlocation() {
		return this.location;
	}

	public void setlocation(final Location location) {
		this.location = location;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Refuge getrefuge() {
		return this.refuge;
	}

	public void setrefuge(final Refuge refuge) {
		this.refuge = refuge;

	}
}
