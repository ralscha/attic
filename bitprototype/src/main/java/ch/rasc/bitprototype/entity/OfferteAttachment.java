package ch.rasc.bitprototype.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class OfferteAttachment extends AbstractPersistable {

	@Lob
	private byte[] content;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "offerteId")
	private Offerte offerte;

	private String contentType;

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Offerte getOfferte() {
		return this.offerte;
	}

	public void setOfferte(Offerte offerte) {
		this.offerte = offerte;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
