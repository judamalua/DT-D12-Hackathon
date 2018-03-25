
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Thread extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String				name;
	private Collection<String>	tag;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml
	public Collection<String> getTag() {
		return this.tag;
	}

	public void setTag(final Collection<String> tag) {
		this.tag = tag;
	}


	// Relationships ----------------------------------------------------------
	private Actor	actor;
	private Forum	forum;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getactor() {
		return this.actor;
	}

	public void setactor(final Actor actor) {
		this.actor = actor;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Forum getforum() {
		return this.forum;
	}

	public void setforum(final Forum forum) {
		this.forum = forum;

	}
}
