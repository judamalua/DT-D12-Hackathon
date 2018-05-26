
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

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
	private double	refugeRecoverTime;
	private double	waterFactorSteal;
	private double	foodFactorSteal;
	private double	metalFactorSteal;
	private double	woodFactorSteal;
	private Integer	foodLostGatherFactor;
	private Integer	waterLostGatherFactor;
	private Integer	experiencePerMinute;
	private Integer	refugeDefaultCapacity;
	private Double	initialFood;
	private Double	initialWater;
	private Double	initialWood;
	private Double	initialMetal;
	private Double	maxInventoryFood;
	private Double	maxInventoryWater;
	private Double	maxInventoryWood;
	private Double	maxInventoryMetal;
	private Integer	numInitialCharacters;
	private Double	refugeFindingProbability;
	private Integer	refugeFindingMinuteAugmentProbability;


	@Min(0)
	public double getMovingWood() {
		return this.movingWood;
	}

	public void setMovingWood(final double movingWood) {
		this.movingWood = movingWood;
	}

	@Min(0)
	public double getMovingMetal() {
		return this.movingMetal;
	}

	public void setMovingMetal(final double movingMetal) {
		this.movingMetal = movingMetal;
	}

	@Min(0)
	public double getMovingFood() {
		return this.movingFood;
	}

	public void setMovingFood(final double movingFood) {
		this.movingFood = movingFood;
	}

	@Min(0)
	public double getMovingWater() {
		return this.movingWater;
	}

	public void setMovingWater(final double movingWater) {
		this.movingWater = movingWater;
	}

	@Min(0)
	public double getKmPerSecond() {
		return this.kmPerSecond;
	}

	public void setKmPerSecond(final double kmPerSecond) {
		this.kmPerSecond = kmPerSecond;
	}

	@Min(0)
	public double getRefugeRecoverTime() {
		return this.refugeRecoverTime;
	}

	public void setRefugeRecoverTime(final double refugeRecoverTime) {
		this.refugeRecoverTime = refugeRecoverTime;
	}

	@Min(0)
	public double getWaterFactorSteal() {
		return this.waterFactorSteal;
	}

	public void setWaterFactorSteal(final double waterFactorSteal) {
		this.waterFactorSteal = waterFactorSteal;
	}

	@Min(0)
	public double getFoodFactorSteal() {
		return this.foodFactorSteal;
	}

	public void setFoodFactorSteal(final double foodFactorSteal) {
		this.foodFactorSteal = foodFactorSteal;
	}

	@Min(0)
	public double getMetalFactorSteal() {
		return this.metalFactorSteal;
	}

	public void setMetalFactorSteal(final double metalFactorSteal) {
		this.metalFactorSteal = metalFactorSteal;
	}

	@Min(0)
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

	@Range(min = 1, max = 100)
	@NotNull
	public Integer getExperiencePerMinute() {
		return this.experiencePerMinute;
	}

	public void setExperiencePerMinute(final Integer experiencePerMinute) {
		this.experiencePerMinute = experiencePerMinute;
	}

	@NotNull
	@Min(1)
	public Integer getRefugeDefaultCapacity() {
		return this.refugeDefaultCapacity;
	}

	public void setRefugeDefaultCapacity(final Integer refugeDefaultCapacity) {
		this.refugeDefaultCapacity = refugeDefaultCapacity;
	}

	@NotNull
	@Min(0)
	public Double getInitialFood() {
		return this.initialFood;
	}

	public void setInitialFood(final Double initialFood) {
		this.initialFood = initialFood;
	}

	@NotNull
	@Min(0)
	public Double getInitialWater() {
		return this.initialWater;
	}

	public void setInitialWater(final Double initialWater) {
		this.initialWater = initialWater;
	}

	@NotNull
	@Min(0)
	public Double getInitialWood() {
		return this.initialWood;
	}

	public void setInitialWood(final Double initialWood) {
		this.initialWood = initialWood;
	}

	@NotNull
	@Min(0)
	public Double getInitialMetal() {
		return this.initialMetal;
	}

	public void setInitialMetal(final Double initialMetal) {
		this.initialMetal = initialMetal;
	}

	@NotNull
	@Min(1)
	public Double getMaxInventoryFood() {
		return this.maxInventoryFood;
	}

	public void setMaxInventoryFood(final Double maxInventoryFood) {
		this.maxInventoryFood = maxInventoryFood;
	}

	@NotNull
	@Min(1)
	public Double getMaxInventoryWater() {
		return this.maxInventoryWater;
	}

	public void setMaxInventoryWater(final Double maxInventoryWater) {
		this.maxInventoryWater = maxInventoryWater;
	}

	@NotNull
	@Min(1)
	public Double getMaxInventoryWood() {
		return this.maxInventoryWood;
	}

	public void setMaxInventoryWood(final Double maxInventoryWood) {
		this.maxInventoryWood = maxInventoryWood;
	}

	@NotNull
	@Min(1)
	public Double getMaxInventoryMetal() {
		return this.maxInventoryMetal;
	}

	public void setMaxInventoryMetal(final Double maxInventoryMetal) {
		this.maxInventoryMetal = maxInventoryMetal;
	}

	@NotNull
	@Min(1)
	public Integer getNumInitialCharacters() {
		return this.numInitialCharacters;
	}

	public void setNumInitialCharacters(final Integer numInitialCharacters) {
		this.numInitialCharacters = numInitialCharacters;
	}

	@Range(min = 0L, max = 1L)
	@NotNull
	public Double getRefugeFindingProbability() {
		return this.refugeFindingProbability;
	}

	public void setRefugeFindingProbability(final Double refugeFindingProbability) {
		this.refugeFindingProbability = refugeFindingProbability;
	}

	@Min(1)
	@NotNull
	public Integer getRefugeFindingMinuteAugmentProbability() {
		return this.refugeFindingMinuteAugmentProbability;
	}

	public void setRefugeFindingMinuteAugmentProbability(final Integer refugeFindingMinuteAugmentProbability) {
		this.refugeFindingMinuteAugmentProbability = refugeFindingMinuteAugmentProbability;
	}

	// Relationships ----------------------------------------------------------

}
