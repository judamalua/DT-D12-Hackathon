
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import annotations.ExtendedURL;

@Entity
@Access(AccessType.PROPERTY)
public class Slider extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String	title_en;
	private String	title_es;
	private String	text_en;
	private String	text_es;
	private String	pictureUrl;
	private String	align;


	@NotBlank
	@SafeHtml
	public String getTitle_en() {
		return this.title_en;
	}

	public void setTitle_en(final String title_en) {
		this.title_en = title_en;
	}

	@NotBlank
	@SafeHtml
	public String getTitle_es() {
		return this.title_es;
	}

	public void setTitle_es(final String title_es) {
		this.title_es = title_es;
	}

	@NotBlank
	@SafeHtml
	public String getText_en() {
		return this.text_en;
	}

	public void setText_en(final String text_en) {
		this.text_en = text_en;
	}

	@NotBlank
	@SafeHtml
	public String getText_es() {
		return this.text_es;
	}

	public void setText_es(final String text_es) {
		this.text_es = text_es;
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

	@Pattern(regexp = "^left|right|center$")
	public String getAlign() {
		return this.align;
	}

	public void setAlign(final String align) {
		this.align = align;
	}

	// Relationships ----------------------------------------------------------

}
