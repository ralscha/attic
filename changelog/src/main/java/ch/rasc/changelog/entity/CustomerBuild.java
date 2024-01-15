package ch.rasc.changelog.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.changelog.util.DMYDateDeserializer;
import ch.rasc.changelog.util.DMYDateSerializer;
import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelType;

@Entity
@Model(value = "Changelog.model.CustomerBuild", readMethod = "customerService.loadBuilds",
		createMethod = "customerService.createBuild",
		updateMethod = "customerService.updateBuild",
		destroyMethod = "customerService.destroyBuild")
public class CustomerBuild extends AbstractPersistable {

	@ManyToOne
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customer;

	@Size(max = 254)
	private String versionNumber;

	@ModelField(type = ModelType.BOOLEAN)
	private Boolean internalBuild;

	@Temporal(TemporalType.DATE)
	@ModelField(dateFormat = "d.m.Y", type = ModelType.DATE)
	private Date versionDate;

	@Transient
	private Long customerId;

	@JsonIgnore
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getVersionNumber() {
		return this.versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	@JsonSerialize(using = DMYDateSerializer.class)
	public Date getVersionDate() {
		return this.versionDate;
	}

	@JsonDeserialize(using = DMYDateDeserializer.class)
	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
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

	public Boolean getInternalBuild() {
		return this.internalBuild;
	}

	public void setInternalBuild(Boolean internalBuild) {
		this.internalBuild = internalBuild;
	}

}
