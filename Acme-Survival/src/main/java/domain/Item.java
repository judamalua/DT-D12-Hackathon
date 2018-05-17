
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
	boolean	isEquiped;


	public boolean isEquiped() {
		return this.isEquiped;
	}

	public void setEquiped(final boolean isEquiped) {
		this.isEquiped = isEquiped;
	}


	// Relationships ----------------------------------------------------------
	private Tool	tool;
	private Refuge	refuge;


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
	@ManyToOne(optional = false)
	public Refuge getRefuge() {
		return this.refuge;
	}

	public void setRefuge(final Refuge refuge) {
		this.refuge = refuge;
	}

}
