package ch.rasc.changelog.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Sets;

import ch.rasc.changelog.util.DMYHMSDateDeserializer;
import ch.rasc.changelog.util.DMYHMSDateSerializer;
import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Model(value = "Changelog.model.Documents", readMethod = "customerService.loadDocuments",
		destroyMethod = "customerService.destroyDocument")
public class Documents extends AbstractPersistable {

	@ManyToOne
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customer;

	@Transient
	private Long customerId;

	@Size(max = 254)
	private String description;

	private String contentType;

	private Long size;

	@Size(max = 254)
	private String fileName;

	@ModelField(dateFormat = "d.m.Y H:i:s")
	private Date date;

	@OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	private Set<DocumentData> documentData = Sets.newHashSet();

	@JsonIgnore
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getCustomerId() {
		if (this.customerId == null) {
			this.customerId = this.customer.getId();
		}
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return this.size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@JsonSerialize(using = DMYHMSDateSerializer.class)
	public Date getDate() {
		return this.date;
	}

	@JsonDeserialize(using = DMYHMSDateDeserializer.class)
	public void setDate(Date date) {
		this.date = date;
	}

	@JsonIgnore
	public Set<DocumentData> getDocumentData() {
		return this.documentData;
	}

	public void setDocumentData(Set<DocumentData> documentData) {
		this.documentData = documentData;
	}
}
