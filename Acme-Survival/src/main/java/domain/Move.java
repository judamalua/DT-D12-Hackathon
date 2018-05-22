
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Move extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Date	startDate;
	private Date	endDate;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
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
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Refuge getRefuge() {
		return this.refuge;
	}

	public void setRefuge(final Refuge refuge) {
		this.refuge = refuge;

	}
}
