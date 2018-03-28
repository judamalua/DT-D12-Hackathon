
package domain;

import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Thread extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String			name;
	private HashSet<String>	tag;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml
	@EachNotBlank
	public HashSet<String> getTag() {
		return this.tag;
	}

	public void setTag(final HashSet<String> tag) {
		this.tag = tag;
	}


	// Relationships ----------------------------------------------------------
	private Actor	actor;
	private Forum	forum;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;

	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Forum getForum() {
		return this.forum;
	}

	public void setForum(final Forum forum) {
		this.forum = forum;

	}
}
