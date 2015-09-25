#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AppUser")
@Model(value = "${jsAppNamespace}.model.User", readMethod = "userService.read",
		createMethod = "userService.create", updateMethod = "userService.update",
		destroyMethod = "userService.destroy", paging = true)
public class User extends AbstractPersistable {

	@NotEmpty(message = "{user_missing_username}")
	@Size(max = 100)
	@Column(unique = true)
	private String userName;

	@Size(max = 255)
	private String name;

	@Size(max = 255)
	private String firstName;

	@Email(message = "{user_missing_email}")
	@Size(max = 255)
	@NotEmpty(message = "{user_missing_email}")
	@Column(unique = true)
	private String email;

	private String role;

	@Size(max = 255)
	@JsonIgnore
	private String passwordHash;

	@Transient
	private String passwordNew;

	@Transient
	private String passwordNewConfirm;

	@Transient
	private String oldPassword;

	@Size(max = 8)
	private String locale;

	private boolean enabled;

	@JsonIgnore
	private Integer failedLogins;

	@JsonIgnore
	private LocalDateTime lockedOut;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Integer getFailedLogins() {
		return failedLogins;
	}

	public void setFailedLogins(Integer failedLogins) {
		this.failedLogins = failedLogins;
	}

	public LocalDateTime getLockedOut() {
		return lockedOut;
	}

	public void setLockedOut(LocalDateTime lockedOut) {
		this.lockedOut = lockedOut;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordNewConfirm() {
		return passwordNewConfirm;
	}

	public void setPasswordNewConfirm(String passwordNewConfirm) {
		this.passwordNewConfirm = passwordNewConfirm;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
