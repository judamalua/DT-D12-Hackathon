
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Forum extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	name;
	private String	description;
	private String	image;
	private boolean	staff;
	private boolean	support;


	@Length(min = 5, max = 50)
	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Length(min = 10, max = 1000)
	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	@SafeHtml
	public String getImage() {
		return this.image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public boolean getStaff() {
		return this.staff;
	}

	public void setStaff(final boolean staff) {
		this.staff = staff;
	}

	public boolean getSupport() {
		return this.support;
	}

	public void setSupport(final boolean support) {
		this.support = support;
	}


	// Relationships ----------------------------------------------------------
	private Forum	forum;
	private Actor	owner;


	@Valid
	@ManyToOne
	public Forum getForum() {
		return this.forum;
	}

	public void setForum(final Forum forum) {
		this.forum = forum;

	}

	@Valid
	@NotNull
	@ManyToOne
	public Actor getOwner() {
		return this.owner;
	}

	public void setOwner(final Actor owner) {
		this.owner = owner;
	}

}
