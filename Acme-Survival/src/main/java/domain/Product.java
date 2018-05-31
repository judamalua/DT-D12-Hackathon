
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import annotations.ExtendedURL;
import annotations.MapLength;
import annotations.MapNotBlank;
import annotations.MapSafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@javax.persistence.Index(columnList = "finalMode,discontinued")
})
public class Product extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Map<String, String>	name;
	private Map<String, String>	description;
	private String				pictureUrl;
	private Double				price;
	private boolean				finalMode;
	private boolean				discontinued;


	@MapLength(min = 5, max = 50)
	@NotNull
	@MapNotBlank
	@MapSafeHtml
	@ElementCollection
	public Map<String, String> getName() {
		return this.name;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
	}

	@MapLength(min = 5, max = 1000)
	@MapSafeHtml
	@NotNull
	@MapNotBlank
	@ElementCollection
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
	public String getPictureUrl() {
		return this.pictureUrl;
	}

	public void setPictureUrl(final String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@NotNull
	@Range(min = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	public boolean getDiscontinued() {
		return this.discontinued;
	}

	public void setDiscontinued(final boolean discontinued) {
		this.discontinued = discontinued;
	}

	// Relationships ----------------------------------------------------------

}
