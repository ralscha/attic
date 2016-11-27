package ch.rasc.eds.starter.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.rasc.extclassgenerator.Model;

@Model(value = "SimpleApp.model.User", rootProperty = "records",
		createMethod = "userService.create", readMethod = "userService.read",
		updateMethod = "userService.update", destroyMethod = "userService.destroy",
		autodetectTypes = false)
@Document(collection = "users")
@TypeAlias("u")
public class User {

	@Id
	private String id;

	private String firstName;

	@NotNull
	private String lastName;

	@Email
	private String email;

	private String department;

	public User() {
		// default constructor
	}

	public User(String firstName, String lastName, String email, String department) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.department = department;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

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

	@Override
	public String toString() {
		return "User [id=" + this.id + ", firstName=" + this.firstName + ", lastName="
				+ this.lastName + ", email=" + this.email + ", department="
				+ this.department + "]";
	}

}
