
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Inventory extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Double	water;
	private Double	food;
	private Double	wood;
	private Double	metal;
	private Double	capacity;


	@NotNull
	public Double getWater() {
		return this.water;
	}

	public void setWater(final Double water) {
		this.water = water;
	}

	@NotNull
	public Double getFood() {
		return this.food;
	}

	public void setFood(final Double food) {
		this.food = food;
	}

	@NotNull
	public Double getWood() {
		return this.wood;
	}

	public void setWood(final Double wood) {
		this.wood = wood;
	}

	@NotNull
	public Double getMetal() {
		return this.metal;
	}

	public void setMetal(final Double metal) {
		this.metal = metal;
	}

	@NotNull
	public Double getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final Double capacity) {
		this.capacity = capacity;
	}


	// Relationships ----------------------------------------------------------
	private Refuge	refuge;


	@NotNull
	@Valid
	@OneToOne
	public Refuge getRefuge() {
		return this.refuge;
	}

	public void setRefuge(final Refuge refuge) {
		this.refuge = refuge;
	}

}
