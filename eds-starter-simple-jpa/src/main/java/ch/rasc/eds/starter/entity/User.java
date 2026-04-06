package ch.rasc.eds.starter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.extclassgenerator.Model;

@Model(value = "Starter.model.User", identifier = "negative",
		createMethod = "userService.create", readMethod = "userService.read",
		updateMethod = "userService.update", destroyMethod = "userService.destroy",
		autodetectTypes = false)
@Entity
public class User extends AbstractPersistable<Long> {

	private String firstName;

	@NotNull
	private String lastName;

	@Email
	private String email;

	@ManyToOne
	@JsonIgnore
	private Department department;

	@Transient
	private long departmentId;

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

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

}
