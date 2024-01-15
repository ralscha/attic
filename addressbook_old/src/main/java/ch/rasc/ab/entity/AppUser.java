package ch.rasc.ab.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model(value = "Addressbook.model.AppUser", readMethod = "appUserService.read",
		createMethod = "appUserService.create", updateMethod = "appUserService.update",
		destroyMethod = "appUserService.destroy", paging = true)
public class AppUser extends AbstractPersistable {

	@NotEmpty(message = "Please enter Username")
	@Size(max = 100)
	@Column(unique = true)
	private String userName;

	@Size(max = 255)
	private String name;

	@Size(max = 255)
	private String firstName;

	@Email(message = "Please enter a valid E-Mail")
	@Size(max = 255)
	@NotEmpty(message = "Please enter E-Mail")
	@Column(unique = true)
	private String email;

	@Size(max = 255)
	@JsonIgnore
	private String passwordHash;

	@Transient
	private String passwordNew;

	@Transient
	private String passwordNewConfirm;

	private boolean enabled;

	private boolean admin;

	@JsonIgnore
	private Integer failedLogins;

	@JsonIgnore
	private Date lockedOut;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getFailedLogins() {
		return this.failedLogins;
	}

	public void setFailedLogins(Integer failedLogins) {
		this.failedLogins = failedLogins;
	}

	public Date getLockedOut() {
		return this.lockedOut;
	}

	public void setLockedOut(Date lockedOut) {
		this.lockedOut = lockedOut;
	}

	public String getPasswordNew() {
		return this.passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordNewConfirm() {
		return this.passwordNewConfirm;
	}

	public void setPasswordNewConfirm(String passwordNewConfirm) {
		this.passwordNewConfirm = passwordNewConfirm;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
