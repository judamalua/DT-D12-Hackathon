
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	cookies_en;
	private String	cookies_es;
	private int		pagesize;


	@NotBlank
	@SafeHtml
	public String getCookies_en() {
		return this.cookies_en;
	}

	public void setCookies_en(final String cookies_en) {
		this.cookies_en = cookies_en;
	}

	@NotBlank
	@SafeHtml
	public String getCookies_es() {
		return this.cookies_es;
	}

	public void setCookies_es(final String cookies_es) {
		this.cookies_es = cookies_es;
	}

	public int getPagesize() {
		return this.pagesize;
	}

	public void setPagesize(final int pagesize) {
		this.pagesize = pagesize;
	}

	// Relationships ----------------------------------------------------------

}
