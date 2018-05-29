
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import annotations.ExtendedURL;
import annotations.MapLength;
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


	@MapLength(min = 5, max = 50)
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

	@MapLength(min = 5, max = 1000)
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
	@ExtendedURL(admitData = true)
	@Column(columnDefinition = "longtext")
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
