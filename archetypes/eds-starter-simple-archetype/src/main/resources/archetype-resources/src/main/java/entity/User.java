#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;

@Model(value = "${jsAppNamespace}.model.User", identifier = "negative", rootProperty = "records",
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
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", department=" + department + "]";
	}

}
