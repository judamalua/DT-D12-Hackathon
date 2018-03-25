
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
	private double	water;
	private double	food;
	private double	metal;
	private double	wood;


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

	public double getWater() {
		return this.water;
	}

	public void setWater(final double water) {
		this.water = water;
	}

	public double getFood() {
		return this.food;
	}

	public void setFood(final double food) {
		this.food = food;
	}

	public double getMetal() {
		return this.metal;
	}

	public void setMetal(final double metal) {
		this.metal = metal;
	}

	public double getWood() {
		return this.wood;
	}

	public void setWood(final double wood) {
		this.wood = wood;
	}


	// Relationships ----------------------------------------------------------
	private Player				player;
	private Collection<Item>	items;
	private Location			location;


	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Player getplayer() {
		return this.player;
	}

	public void setplayer(final Player player) {
		this.player = player;

	}
	@Valid
	@OneToMany
	public Collection<Item> getitems() {
		return this.items;
	}

	public void setitems(final Collection<Item> items) {
		this.items = items;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Location getlocation() {
		return this.location;
	}

	public void setlocation(final Location location) {
		this.location = location;

	}
}
