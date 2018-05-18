
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "`character`")
public class Character extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	fullName;
	private int		currentHealth;
	private int		currentWater;
	private int		currentFood;
	private int		strength;
	private int		luck;
	private int		capacity;
	private int		level;
	private int		experience;
	private boolean	male;
	private Date	roomEntrance;


	//private boolean	male;

	public boolean getMale() {
		return this.male;
	}

	public void setMale(final boolean male) {
		this.male = male;
	}

	@NotBlank
	@SafeHtml
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	//	public boolean getMale() {
	//		return this.male;
	//	}
	//
	//	public void setMale(final boolean male) {
	//		this.male = male;
	//	}

	@Min(value = 0)
	public int getCurrentHealth() {
		return this.currentHealth;
	}

	public void setCurrentHealth(final int currentHealth) {
		this.currentHealth = currentHealth;
	}

	@Min(value = 0)
	public int getCurrentWater() {
		return this.currentWater;
	}

	public void setCurrentWater(final int currentWater) {
		this.currentWater = currentWater;
	}

	@Min(value = 0)
	public int getCurrentFood() {
		return this.currentFood;
	}

	public void setCurrentFood(final int currentFood) {
		this.currentFood = currentFood;
	}

	@Min(value = 1)
	public int getStrength() {
		return this.strength;
	}

	public void setStrength(final int strength) {
		this.strength = strength;
	}

	@Min(value = 1)
	public int getLuck() {
		return this.luck;
	}

	public void setLuck(final int luck) {
		this.luck = luck;
	}

	@Min(value = 1)
	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final int capacity) {
		this.capacity = capacity;
	}

	@Min(value = 1)
	public int getLevel() {
		return this.level;
	}

	public void setLevel(final int level) {
		this.level = level;
	}

	@Min(value = 0)
	public int getExperience() {
		return this.experience;
	}

	public void setExperience(final int experience) {
		this.experience = experience;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	public Date getRoomEntrance() {
		return this.roomEntrance;
	}

	public void setRoomEntrance(final Date roomEntrance) {
		this.roomEntrance = roomEntrance;
	}


	// Relationships ----------------------------------------------------------
	private Refuge	refuge;
	private Item	item;
	private Room	room;


	@Valid
	@NotNull
	@ManyToOne(optional = true)
	public Refuge getRefuge() {
		return this.refuge;
	}

	public void setRefuge(final Refuge refuge) {
		this.refuge = refuge;

	}
	@Valid
	@OneToOne(optional = true)
	public Item getItem() {
		return this.item;
	}

	public void setItem(final Item item) {
		this.item = item;

	}

	@Valid
	@ManyToOne
	public Room getRoom() {
		return this.room;
	}

	public void setRoom(final Room room) {
		this.room = room;

	}
}
