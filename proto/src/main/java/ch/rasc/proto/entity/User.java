package ch.rasc.proto.entity;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import ch.rasc.edsutil.jackson.ISO8601LocalDateTimeSerializer;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Model(value = "Proto.model.User", readMethod = "userService.read",
		createMethod = "userService.update", updateMethod = "userService.update",
		destroyMethod = "userService.destroy", rootProperty = "records",
		identifier = "negative")
public class User extends AbstractPersistable {

	@NotBlank(message = "{fieldrequired}")
	private String loginName;

	@NotBlank(message = "{fieldrequired}")
	private String lastName;

	@NotBlank(message = "{fieldrequired}")
	private String firstName;

	@Email(message = "{invalidemail}")
	private String email;

	private String role;

	@JsonIgnore
	private String passwordHash;

	private transient String newPassword;

	private transient String newPasswordRetype;

	@NotBlank(message = "{fieldrequired}")
	private String locale;

	private boolean enabled;

	private transient boolean passwordReset;

	@ModelField(persist = false)
	private Integer failedLogins;

	@ModelField(dateFormat = "c", persist = false)
	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	private LocalDateTime lockedOutUntil;

	@JsonIgnore
	private String passwordResetToken;

	@JsonIgnore
	private LocalDateTime passwordResetTokenValidUntil;

	@JsonIgnore
	private boolean deleted;

	@ModelField(persist = false)
	private transient String autoOpenView;

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

	public LocalDateTime getLockedOutUntil() {
		return this.lockedOutUntil;
	}

	public void setLockedOutUntil(LocalDateTime lockedOutUntil) {
		this.lockedOutUntil = lockedOutUntil;
	}

	public String getPasswordResetToken() {
		return this.passwordResetToken;
	}

	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public LocalDateTime getPasswordResetTokenValidUntil() {
		return this.passwordResetTokenValidUntil;
	}

	public void setPasswordResetTokenValidUntil(LocalDateTime passwordResetTokenValidUntil) {
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

}
