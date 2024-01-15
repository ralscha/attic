package ch.rasc.tttracker.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.jpa.domain.AbstractPersistable;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelType;
import ch.rasc.extclassgenerator.ModelValidation;
import ch.rasc.extclassgenerator.ModelValidationParameter;
import ch.rasc.extclassgenerator.ModelValidationType;

@Entity
@Model(value = "TTT.model.User")
@ModelField(value = "id", type = ModelType.INTEGER)
public class User extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 50)
	private String userName;

	@NotNull
	@Size(min = 1, max = 100)
	private String firstName;

	@NotNull
	@Size(min = 1, max = 100)
	private String lastName;

	@NotNull
	@Email
	@Size(min = 1, max = 100)
	private String email;

	@NotNull
	@Size(min = 1, max = 100)
	private String password;

	@ModelField(type = ModelType.STRING)
	@ModelValidation(value = ModelValidationType.INCLUSION,
			parameters = @ModelValidationParameter(name = "list", value = "['Y','N']") )
	private Character adminRole;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Character getAdminRole() {
		return this.adminRole;
	}

	public void setAdminRole(Character adminRole) {
		this.adminRole = adminRole;
	}

}
