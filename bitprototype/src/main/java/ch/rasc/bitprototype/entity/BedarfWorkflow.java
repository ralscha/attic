package ch.rasc.bitprototype.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.bitprototype.service.BedarfStatus;
import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.entity.DateTimeConverter;
import ch.rasc.edsutil.jackson.ISO8601DateTimeSerializer;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelType;

@Entity
@Model(value = "BitP.model.BedarfWorkflow", readMethod = "workflowService.readBedarf")
public class BedarfWorkflow extends AbstractPersistable {

	@ModelField(dateFormat = "c")
	@Convert(converter = DateTimeConverter.class)
	private DateTime createDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "bedarfId")
	private Bedarf bedarf;

	@ModelField(type = ModelType.STRING)
	private BedarfStatus lastStatus;

	@ModelField(type = ModelType.STRING)
	private BedarfStatus nextStatus;

	@JsonIgnore
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

	public Bedarf getBedarf() {
		return this.bedarf;
	}

	public void setBedarf(Bedarf bedarf) {
		this.bedarf = bedarf;
	}

	public BedarfStatus getLastStatus() {
		return this.lastStatus;
	}

	public void setLastStatus(BedarfStatus lastStatus) {
		this.lastStatus = lastStatus;
	}

	public BedarfStatus getNextStatus() {
		return this.nextStatus;
	}

	public void setNextStatus(BedarfStatus nextStatus) {
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
