
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Inventory extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Double	water;
	private Double	food;
	private Double	wood;
	private Double	metal;
	private Double	waterCapacity;
	private Double	foodCapacity;
	private Double	woodCapacity;
	private Double	metalCapacity;


	@Min(0)
	@NotNull
	public Double getWater() {
		return this.water;
	}

	public void setWater(final Double water) {
		this.water = water;
	}

	@Min(0)
	@NotNull
	public Double getFood() {
		return this.food;
	}

	public void setFood(final Double food) {
		this.food = food;
	}

	@Min(0)
	@NotNull
	public Double getWood() {
		return this.wood;
	}

	public void setWood(final Double wood) {
		this.wood = wood;
	}

	@Min(0)
	@NotNull
	public Double getMetal() {
		return this.metal;
	}

	public void setMetal(final Double metal) {
		this.metal = metal;
	}

	@Min(1)
	@NotNull
	public Double getWaterCapacity() {
		return this.waterCapacity;
	}

	public void setWaterCapacity(final Double waterCapacity) {
		this.waterCapacity = waterCapacity;
	}

	@Min(1)
	@NotNull
	public Double getFoodCapacity() {
		return this.foodCapacity;
	}

	public void setFoodCapacity(final Double foodCapacity) {
		this.foodCapacity = foodCapacity;
	}

	@Min(1)
	@NotNull
	public Double getWoodCapacity() {
		return this.woodCapacity;
	}

	public void setWoodCapacity(final Double woodCapacity) {
		this.woodCapacity = woodCapacity;
	}

	@Min(1)
	@NotNull
	public Double getMetalCapacity() {
		return this.metalCapacity;
	}

	public void setMetalCapacity(final Double metalCapacity) {
		this.metalCapacity = metalCapacity;
	}


	// Relationships ----------------------------------------------------------
	private Shelter	shelter;


	@NotNull
	@Valid
	@OneToOne
	public Shelter getShelter() {
		return this.shelter;
	}

	public void setShelter(final Shelter shelter) {
		this.shelter = shelter;
	}

}
