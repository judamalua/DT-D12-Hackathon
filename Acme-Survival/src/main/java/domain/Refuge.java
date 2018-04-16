
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Refuge extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	code;
	private String	name;
	private Date	momentOfCreation;


	@Pattern(regexp = "^\\w{10}$")
	@Column(unique = true)
	@SafeHtml
	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Past
	public Date getMomentOfCreation() {
		return this.momentOfCreation;
	}

	public void setMomentOfCreation(final Date momentOfCreation) {
		this.momentOfCreation = momentOfCreation;
	}


	// Relationships ----------------------------------------------------------
	private Player				player;
	private Collection<Item>	items;
	private Location			location;


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
	@OneToMany
	public Collection<Item> getItems() {
		return this.items;
	}

	public void setItems(final Collection<Item> items) {
		this.items = items;

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
