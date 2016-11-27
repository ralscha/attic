package ch.rasc.eds.starter.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;

@Model(value = "SimpleApp.model.User", identifier = "negative", rootProperty = "records",
		createMethod = "userService.create", readMethod = "userService.read",
		updateMethod = "userService.update", destroyMethod = "userService.destroy",
		autodetectTypes = false)
@Entity
public class User extends AbstractPersistable {

	private String firstName;

	@NotNull
	private String lastName;

	@Email
	private String email;

	private String department;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", firstName=" + this.firstName + ", lastName="
				+ this.lastName + ", email=" + this.email + ", department="
				+ this.department + "]";
	}

}
