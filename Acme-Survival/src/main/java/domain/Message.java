
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	text;
	private Date	moment;
	private String	image;


	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@URL
	@SafeHtml
	public String getImage() {
		return this.image;
	}

	public void setImage(final String image) {
		this.image = image;
	}


	// Relationships ----------------------------------------------------------
	private Actor	actor;
	private Thread	thread;


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
	public Thread getthread() {
		return this.thread;
	}

	public void setthread(final Thread thread) {
		this.thread = thread;

	}
}
