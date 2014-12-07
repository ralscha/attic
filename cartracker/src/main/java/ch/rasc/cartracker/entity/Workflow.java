package ch.rasc.cartracker.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import ch.rasc.cartracker.entity.option.Status;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model(value = "CarTracker.model.Workflow", readMethod = "workflowService.read")
public class Workflow extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "carId")
	@JsonIgnore
	private Car car;

	@ManyToOne(optional = true)
	@JoinColumn(name = "lastStatusId")
	@JsonIgnore
	private Status lastStatus;

	@ManyToOne
	@JoinColumn(name = "nextStatusId")
	@JsonIgnore
	private Status nextStatus;

	@ManyToOne
	@JoinColumn(name = "staffId")
	@JsonIgnore
	private Staff staff;

	private String notes;

	private boolean approved;

	@Transient
	@ModelField(persist = false)
	private String lastStatusName;

	@Transient
	@ModelField(persist = false)
	private String nextStatusName;

	@Transient
	@ModelField(persist = false)
	private String lastName;

	@Transient
	@ModelField(persist = false)
	private String firstName;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Status getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(Status lastStatus) {
		this.lastStatus = lastStatus;
	}

	public Status getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(Status nextStatus) {
		this.nextStatus = nextStatus;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getLastStatusName() {
		return lastStatusName;
	}

	public void setLastStatusName(String lastStatusName) {
		this.lastStatusName = lastStatusName;
	}

	public String getNextStatusName() {
		return nextStatusName;
	}

	public void setNextStatusName(String nextStatusName) {
		this.nextStatusName = nextStatusName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@PostLoad
	private void populate() {
		if (staff != null) {
			firstName = staff.getFirstName();
			lastName = staff.getLastName();
		}
		else {
			firstName = null;
			lastName = null;
		}

		if (nextStatus != null) {
			nextStatusName = nextStatus.getLongName();
		}
		else {
			nextStatusName = null;
		}

		if (lastStatus != null) {
			lastStatusName = lastStatus.getLongName();
		}
		else {
			lastStatusName = null;
		}

	}

}
