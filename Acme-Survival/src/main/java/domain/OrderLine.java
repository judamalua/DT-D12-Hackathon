
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
public class OrderLine extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private int	amount;


	@Range(min = 0)
	public int getAmount() {
		return this.amount;
	}

	public void setAmount(final int amount) {
		this.amount = amount;
	}


	// Relationships ----------------------------------------------------------
	private Product	product;
	private Order	order;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Product getproduct() {
		return this.product;
	}

	public void setproduct(final Product product) {
		this.product = product;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Order getorder() {
		return this.order;
	}

	public void setorder(final Order order) {
		this.order = order;

	}
}
