package ch.rasc.eds.starter.entity;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Table(name = "AppUser")
@Model(value = "Starter.model.User", readMethod = "userService.read",
		createMethod = "userService.update", updateMethod = "userService.update",
		destroyMethod = "userService.destroy", paging = true, identifier = "negative")
public class User extends AbstractPersistable {

	@NotBlank(message = "{fieldrequired}")
	@Size(max = 255)
	@Column(unique = true)
	private String loginName;

	@NotBlank(message = "{fieldrequired}")
	@Size(max = 255)
	private String lastName;

	@NotBlank(message = "{fieldrequired}")
	@Size(max = 255)
	private String firstName;

	@Email(message = "{invalidemail}")
	@Size(max = 255)
	private String email;

	private String role;

	@Size(max = 255)
	@JsonIgnore
	private String passwordHash;

	@Transient
	private String newPassword;

	@Transient
	private String newPasswordRetype;

	@NotBlank(message = "{fieldrequired}")
	@Size(max = 8)
	private String locale;

	private boolean enabled;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private Set<PersistentLogin> persistentLogins = new HashSet<>();

	@Transient
	private boolean passwordReset;

	@ModelField(persist = false)
	private Integer failedLogins;

	@ModelField(dateFormat = "time", persist = false)
	private ZonedDateTime lockedOutUntil;

	@ModelField(dateFormat = "time", persist = false)
	@Transient
	private ZonedDateTime lastLogin;

	@Size(max = 36)
	@JsonIgnore
	private String passwordResetToken;

	@JsonIgnore
	private ZonedDateTime passwordResetTokenValidUntil;

	@JsonIgnore
	@Column(name = "is_deleted")
	private boolean deleted;

	@Transient
	@ModelField(persist = false)
	private String autoOpenView;

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public boolean isPasswordReset() {
		return this.passwordReset;
	}

	public void setPasswordReset(boolean passwordReset) {
		this.passwordReset = passwordReset;
	}

	public Integer getFailedLogins() {
		return this.failedLogins;
	}

	public void setFailedLogins(Integer failedLogins) {
		this.failedLogins = failedLogins;
	}

	public ZonedDateTime getLockedOutUntil() {
		return this.lockedOutUntil;
	}

	public void setLockedOutUntil(ZonedDateTime lockedOutUntil) {
		this.lockedOutUntil = lockedOutUntil;
	}

	public ZonedDateTime getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(ZonedDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPasswordResetToken() {
		return this.passwordResetToken;
	}

	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public ZonedDateTime getPasswordResetTokenValidUntil() {
		return this.passwordResetTokenValidUntil;
	}

	public void setPasswordResetTokenValidUntil(
			ZonedDateTime passwordResetTokenValidUntil) {
		this.passwordResetTokenValidUntil = passwordResetTokenValidUntil;
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordRetype() {
		return this.newPasswordRetype;
	}

	public void setNewPasswordRetype(String newPasswordRetype) {
		this.newPasswordRetype = newPasswordRetype;
	}

	public String getAutoOpenView() {
		return this.autoOpenView;
	}

	public void setAutoOpenView(String autoOpenView) {
		this.autoOpenView = autoOpenView;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<PersistentLogin> getPersistentLogins() {
		return this.persistentLogins;
	}

	public void setPersistentLogins(Set<PersistentLogin> persistentLogins) {
		this.persistentLogins = persistentLogins;
	}

}
