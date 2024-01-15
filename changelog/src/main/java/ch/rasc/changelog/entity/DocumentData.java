package ch.rasc.changelog.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class DocumentData extends AbstractPersistable {

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] data;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "documentId", nullable = false)
	private Documents document;

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Documents getDocument() {
		return this.document;
	}

	public void setDocument(Documents document) {
		this.document = document;
	}
}
