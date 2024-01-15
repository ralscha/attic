package ch.rasc.changelog.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

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
@Indexed
@Model(value = "Changelog.model.Change", readMethod = "changeService.load",
		createMethod = "changeService.create", updateMethod = "changeService.update",
		destroyMethod = "changeService.destroy", paging = true)
public class Change extends AbstractPersistable {

	@Size(max = 254)
	@Fields({ @Field(name = "search"), @Field(analyze = Analyze.NO, store = Store.YES) })
	private String bugNumber;

	@Size(max = 254)
	@Fields({ @Field(name = "search"), @Field(analyze = Analyze.NO, store = Store.YES) })
	private String module;

	@Size(max = 254)
	@Fields({ @Field(name = "search"), @Field(analyze = Analyze.NO, store = Store.YES) })
	private String subject;

	@Fields({ @Field(name = "search"), @Field(analyze = Analyze.NO, store = Store.YES) })
	private String description;

	@Temporal(TemporalType.DATE)
	@Field(analyze = Analyze.NO, store = Store.YES)
	@ModelField(type = ModelType.DATE, dateFormat = "d.m.Y")
	private Date implementationDate;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	@Field(analyze = Analyze.NO, store = Store.YES)
	@ModelField(type = ModelType.STRING)
	private ChangeType typ;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "change", orphanRemoval = true)
	private Set<CustomerChange> customerChanges = new HashSet<>();

	@Transient
	@ModelField(persist = false)
	private Integer noOfCustomers;

	@Transient
	@ModelField(persist = false)
	private String customerTooltip;

	@Transient
	@ModelField(type = ModelType.AUTO)
	private Set<Long> customerIds;

	public void update(Change modifiedChange) {
		this.bugNumber = modifiedChange.getBugNumber();
		this.module = modifiedChange.getModule();
		this.description = modifiedChange.getDescription();
		this.implementationDate = modifiedChange.getImplementationDate();
		this.typ = modifiedChange.getTyp();
		this.subject = modifiedChange.getSubject().substring(0,
				Math.min(modifiedChange.getSubject().length(), 254));
	}

	public String getBugNumber() {
		return this.bugNumber;
	}

	public void setBugNumber(String bugNumber) {
		this.bugNumber = bugNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonSerialize(using = DMYDateSerializer.class)
	public Date getImplementationDate() {
		return this.implementationDate;
	}

	@JsonDeserialize(using = DMYDateDeserializer.class)
	public void setImplementationDate(Date implementDate) {
		this.implementationDate = implementDate;
	}

	public ChangeType getTyp() {
		return this.typ;
	}

	public void setTyp(ChangeType typ) {
		this.typ = typ;
	}

	@JsonIgnore
	public Set<CustomerChange> getCustomerChanges() {
		return this.customerChanges;
	}

	public void setCustomerChanges(Set<CustomerChange> customerChanges) {
		this.customerChanges = customerChanges;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String modul) {
		this.module = modul;
	}

	public Integer getNoOfCustomers() {
		return this.noOfCustomers;
	}

	public void setNoOfCustomers(Integer noOfCustomers) {
		this.noOfCustomers = noOfCustomers;
	}

	public String getCustomerTooltip() {
		return this.customerTooltip;
	}

	public void setCustomerTooltip(String customerTooltip) {
		this.customerTooltip = customerTooltip;
	}

	public Set<Long> getCustomerIds() {
		return this.customerIds;
	}

	public void setCustomerIds(Set<Long> customerIds) {
		this.customerIds = customerIds;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
