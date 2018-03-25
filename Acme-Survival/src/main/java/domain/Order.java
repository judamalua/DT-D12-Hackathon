
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
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
	public Player getplayer() {
		return this.player;
	}

	public void setplayer(final Player player) {
		this.player = player;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public CreditCard getcreditCard() {
		return this.creditCard;
	}

	public void setcreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;

	}
}
