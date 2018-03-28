
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "`order`")
public class Order extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Date	moment;


	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships ----------------------------------------------------------
	private Player		player;
	private CreditCard	creditCard;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(final Player player) {
		this.player = player;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;

	}
}
