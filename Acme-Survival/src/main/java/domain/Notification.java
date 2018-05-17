
package domain;

import java.util.Date;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import annotations.MapNotBlank;
import annotations.MapSafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Notification extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Map<String, String>	title;
	private Map<String, String>	body;
	private Date				moment;


	@NotNull
	@MapNotBlank
	@MapSafeHtml
	@ElementCollection
	public Map<String, String> getTitle() {
		return this.title;
	}

	public void setTitle(final Map<String, String> title) {
		this.title = title;
	}

	@NotNull
	@MapNotBlank
	@MapSafeHtml
	@ElementCollection
	public Map<String, String> getBody() {
		return this.body;
	}

	public void setBody(final Map<String, String> body) {
		this.body = body;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships -------------------------------------------------------------

	private Player	player;
	private Mission	mission;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Player getPlayer() {
		return this.player;
	}
	public void setPlayer(final Player player) {
		this.player = player;
	}

	@Valid
	@OneToOne(optional = true)
	public Mission getMission() {
		return this.mission;
	}

	public void setMission(final Mission mission) {
		this.mission = mission;
	}

}
