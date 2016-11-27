package ch.rasc.eds.starter.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import ch.rasc.eds.starter.entity.User;

public class UserSettings {
	@NotBlank(message = "{fieldrequired}")
	private String firstName;

	@NotBlank(message = "{fieldrequired}")
	private String lastName;

	@NotBlank(message = "{fieldrequired}")
	private String locale;

	@Email(message = "{invalidemail}")
	@NotBlank(message = "{fieldrequired}")
	private String email;

	private String currentPassword;
	private String newPassword;
	private String newPasswordRetype;

	public UserSettings() {
		// default constructor
	}

	public UserSettings(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.locale = user.getLocale();
		this.email = user.getEmail();
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

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurrentPassword() {
		return this.currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
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
}
