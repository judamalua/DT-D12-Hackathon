
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

	// Relationships ----------------------------------------------------------
	private Tool	tool;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Tool gettool() {
		return this.tool;
	}

	public void settool(final Tool tool) {
		this.tool = tool;

	}
}
