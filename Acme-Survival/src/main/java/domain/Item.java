
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Item extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private boolean	equipped;


	public boolean getEquipped() {
		return this.equipped;
	}

	public void setEquipped(final boolean equipped) {
		this.equipped = equipped;
	}


	// Relationships ----------------------------------------------------------
	private Tool	tool;
	private Shelter	shelter;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Tool getTool() {
		return this.tool;
	}

	public void setTool(final Tool tool) {
		this.tool = tool;

	}

	@Valid
	@ManyToOne(optional = true)
	public Shelter getShelter() {
		return this.shelter;
	}

	public void setShelter(final Shelter shelter) {
		this.shelter = shelter;
	}

}
