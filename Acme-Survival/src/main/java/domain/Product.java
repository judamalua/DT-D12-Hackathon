
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Product extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String				name_en;
	private String				name_es;
	private Map<String, String>	name;
	private String				description_en;
	private String				description_es;
	private String				pictureUrl;
	private Double				price;
	private boolean				finalMode;
	private boolean				discontinued;


	@NotBlank
	@SafeHtml
	public String getName_en() {
		return this.name_en;
	}

	public void setName_en(final String name_en) {
		this.name_en = name_en;
	}

	@NotBlank
	@SafeHtml
	public String getName_es() {
		return this.name_es;
	}

	public void setName_es(final String name_es) {
		this.name_es = name_es;
	}

	@NotBlank
	@SafeHtml
	public String getDescription_en() {
		return this.description_en;
	}

	public void setDescription_en(final String description_en) {
		this.description_en = description_en;
	}

	@NotBlank
	@SafeHtml
	public String getDescription_es() {
		return this.description_es;
	}

	public void setDescription_es(final String description_es) {
		this.description_es = description_es;
	}

	@NotBlank
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

	@ElementCollection
	public Map<String, String> getName() {
		return this.name;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
	}

	// Relationships ----------------------------------------------------------

}
