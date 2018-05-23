
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String				businessNameFirst;
	private String				businessNameLast;
	private Integer				pageSize;
	private Collection<String>	languages;


	@SafeHtml
	@NotBlank
	public String getBusinessNameFirst() {
		return this.businessNameFirst;
	}

	public void setBusinessNameFirst(final String businessNameFirst) {
		this.businessNameFirst = businessNameFirst;
	}

	@SafeHtml
	@NotBlank
	public String getBusinessNameLast() {
		return this.businessNameLast;
	}

	public void setBusinessNameLast(final String businessNameLast) {
		this.businessNameLast = businessNameLast;
	}

	@NotNull
	@Range(min = 1)
	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getLanguages() {
		return this.languages;
	}

	public void setLanguages(final Collection<String> languages) {
		this.languages = languages;
	}

	// Relationships ----------------------------------------------------------

}
