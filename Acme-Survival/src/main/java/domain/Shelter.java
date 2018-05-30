
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Shelter extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	code;
	private String	name;
	private Date	momentOfCreation;
	private String	gpsCoordinates;
	private Date	lastView;
	private Date	lastAttackReceived;


	@Pattern(regexp = "^\\w{10}$")
	@Column(unique = true)
	@SafeHtml
	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	@Length(min = 5, max = 50)
	@Column(unique = true)
	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	public Date getMomentOfCreation() {
		return this.momentOfCreation;
	}

	public void setMomentOfCreation(final Date momentOfCreation) {
		this.momentOfCreation = momentOfCreation;
	}

	@Pattern(regexp = "^(\\-?\\d+(\\.\\d+)?),\\w*(\\-?\\d+(\\.\\d+)?)$")
	@SafeHtml
	@NotBlank
	public String getGpsCoordinates() {
		return this.gpsCoordinates;
	}

	public void setGpsCoordinates(final String gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	public Date getLastView() {
		return this.lastView;
	}

	public void setLastView(final Date lastView) {
		this.lastView = lastView;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	public Date getLastAttackReceived() {
		return this.lastAttackReceived;
	}

	public void setLastAttackReceived(final Date lastAttackReceived) {
		this.lastAttackReceived = lastAttackReceived;
	}


	// Relationships ----------------------------------------------------------
	private Player		player;
	private Location	location;


	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(final Player player) {
		this.player = player;

	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;

	}
}
