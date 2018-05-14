
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import annotations.MapNotBlank;
import annotations.MapSafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public abstract class ItemDesign extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Map<String, String>	name;
	private Map<String, String>	description;
	private String				imageUrl;
	private boolean				finalMode;


	@MapNotBlank
	@MapSafeHtml
	@ElementCollection
	@NotNull
	public Map<String, String> getName() {
		return this.name;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
	}

	@MapNotBlank
	@MapSafeHtml
	@ElementCollection
	@NotNull
	public Map<String, String> getDescription() {
		return this.description;
	}

	public void setDescription(final Map<String, String> description) {
		this.description = description;
	}

	@NotBlank
	@URL
	@SafeHtml
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	// Relationships ----------------------------------------------------------

}
