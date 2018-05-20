
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class DesignerConfiguration extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private double	movingWood;
	private double	movingMetal;
	private double	movingFood;
	private double	movingWater;
	private double	kmPerSecond;
	private double	foodWastedPerSecond;
	private double	waterWastedPerSecond;
	private double	refugeRecoverTime;
	private double	waterFactorSteal;
	private double	foodFactorSteal;
	private double	metalFactorSteal;
	private double	woodFactorSteal;
	private Integer	foodLostGatherFactor;
	private Integer	waterLostGatherFactor;


	public double getMovingWood() {
		return this.movingWood;
	}

	public void setMovingWood(final double movingWood) {
		this.movingWood = movingWood;
	}

	public double getMovingMetal() {
		return this.movingMetal;
	}

	public void setMovingMetal(final double movingMetal) {
		this.movingMetal = movingMetal;
	}

	public double getMovingFood() {
		return this.movingFood;
	}

	public void setMovingFood(final double movingFood) {
		this.movingFood = movingFood;
	}

	public double getMovingWater() {
		return this.movingWater;
	}

	public void setMovingWater(final double movingWater) {
		this.movingWater = movingWater;
	}

	public double getKmPerSecond() {
		return this.kmPerSecond;
	}

	public void setKmPerSecond(final double kmPerSecond) {
		this.kmPerSecond = kmPerSecond;
	}

	public double getFoodWastedPerSecond() {
		return this.foodWastedPerSecond;
	}

	public void setFoodWastedPerSecond(final double foodWastedPerSecond) {
		this.foodWastedPerSecond = foodWastedPerSecond;
	}

	public double getWaterWastedPerSecond() {
		return this.waterWastedPerSecond;
	}

	public void setWaterWastedPerSecond(final double waterWastedPerSecond) {
		this.waterWastedPerSecond = waterWastedPerSecond;
	}

	public double getRefugeRecoverTime() {
		return this.refugeRecoverTime;
	}

	public void setRefugeRecoverTime(final double refugeRecoverTime) {
		this.refugeRecoverTime = refugeRecoverTime;
	}

	public double getWaterFactorSteal() {
		return this.waterFactorSteal;
	}

	public void setWaterFactorSteal(final double waterFactorSteal) {
		this.waterFactorSteal = waterFactorSteal;
	}

	public double getFoodFactorSteal() {
		return this.foodFactorSteal;
	}

	public void setFoodFactorSteal(final double foodFactorSteal) {
		this.foodFactorSteal = foodFactorSteal;
	}

	public double getMetalFactorSteal() {
		return this.metalFactorSteal;
	}

	public void setMetalFactorSteal(final double metalFactorSteal) {
		this.metalFactorSteal = metalFactorSteal;
	}

	public double getWoodFactorSteal() {
		return this.woodFactorSteal;
	}

	public void setWoodFactorSteal(final double woodFactorSteal) {
		this.woodFactorSteal = woodFactorSteal;
	}

	@Min(0)
	@NotNull
	public Integer getFoodLostGatherFactor() {
		return this.foodLostGatherFactor;
	}

	public void setFoodLostGatherFactor(final Integer foodLostGatherFactor) {
		this.foodLostGatherFactor = foodLostGatherFactor;
	}

	@Min(0)
	@NotNull
	public Integer getWaterLostGatherFactor() {
		return this.waterLostGatherFactor;
	}

	public void setWaterLostGatherFactor(final Integer waterLostGatherFactor) {
		this.waterLostGatherFactor = waterLostGatherFactor;
	}

	// Relationships ----------------------------------------------------------

}
