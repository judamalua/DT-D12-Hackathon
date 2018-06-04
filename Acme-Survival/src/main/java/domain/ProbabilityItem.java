
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class ProbabilityItem extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private double	value;


	@Range(min = 0, max = 1)
	public double getValue() {
		return this.value;
	}

	public void setValue(final double value) {
		this.value = value;
	}


	// Relationships ----------------------------------------------------------
	private ItemDesign	itemDesign;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public ItemDesign getItemDesign() {
		return this.itemDesign;
	}

	public void setItemDesign(final ItemDesign itemDesign) {
		this.itemDesign = itemDesign;

	}
}
