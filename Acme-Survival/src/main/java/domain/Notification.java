
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
	private Integer				characterId;
	private boolean				foundShelter;


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
	@Lob
	@Column(length = 100000)
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

	public Integer getCharacterId() {
		return this.characterId;
	}

	public void setCharacterId(final Integer characterId) {
		this.characterId = characterId;
	}

	public boolean isFoundShelter() {
		return this.foundShelter;
	}

	public void setFoundShelter(final boolean foundShelter) {
		this.foundShelter = foundShelter;
	}


	// Relationships -------------------------------------------------------------

	private Player					player;
	private Attack					attack;
	private Gather					gather;
	private Collection<Event>		events;
	private Collection<ItemDesign>	itemDesigns;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Player getPlayer() {
		return this.player;
	}
	public void setPlayer(final Player player) {
		this.player = player;
	}

	@OneToOne(optional = true)
	public Attack getAttack() {
		return this.attack;
	}

	public void setAttack(final Attack attack) {
		this.attack = attack;
	}
	@OneToOne(optional = true)
	public Gather getGather() {
		return this.gather;
	}

	public void setGather(final Gather gather) {
		this.gather = gather;
	}

	@Valid
	@ManyToMany
	@NotNull
	public Collection<Event> getEvents() {
		return this.events;
	}

	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

	@Valid
	@ManyToMany
	@NotNull
	public Collection<ItemDesign> getItemDesigns() {
		return this.itemDesigns;
	}

	public void setItemDesigns(final Collection<ItemDesign> itemDesigns) {
		this.itemDesigns = itemDesigns;
	}

}
