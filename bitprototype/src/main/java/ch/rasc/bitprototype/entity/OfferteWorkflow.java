package ch.rasc.bitprototype.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.bitprototype.service.OfferteStatus;
import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.entity.DateTimeConverter;
import ch.rasc.edsutil.jackson.ISO8601DateTimeSerializer;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelType;

@Entity
@Model(value = "BitP.model.OfferteWorkflow", readMethod = "workflowService.readOfferte")
public class OfferteWorkflow extends AbstractPersistable {

	@ModelField(dateFormat = "c")
	@Convert(converter = DateTimeConverter.class)
	private DateTime createDate;

	@ManyToOne
	@JoinColumn(name = "offerteId")
	private Offerte offerte;

	@ModelField(type = ModelType.STRING)
	private OfferteStatus lastStatus;

	@ModelField(type = ModelType.STRING)
	private OfferteStatus nextStatus;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	private String notes;

	@Transient
	@ModelField(persist = false)
	private String lastName;

	@Transient
	@ModelField(persist = false)
	private String firstName;

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public Offerte getOfferte() {
		return this.offerte;
	}

	public void setOfferte(Offerte offerte) {
		this.offerte = offerte;
	}

	public OfferteStatus getLastStatus() {
		return this.lastStatus;
	}

	public void setLastStatus(OfferteStatus lastStatus) {
		this.lastStatus = lastStatus;
	}

	public OfferteStatus getNextStatus() {
		return this.nextStatus;
	}

	public void setNextStatus(OfferteStatus nextStatus) {
		this.nextStatus = nextStatus;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@PostLoad
	private void populate() {
		if (this.user != null) {
			this.firstName = this.user.getFirstName();
			this.lastName = this.user.getName();
		}
		else {
			this.firstName = null;
			this.lastName = null;
		}

	}

}
