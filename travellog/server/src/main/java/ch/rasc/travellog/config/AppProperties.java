package ch.rasc.travellog.config;

import java.time.Duration;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ch.rasc.travellog.service.TokenService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "app")
@Component
@Validated
public class AppProperties {

	/**
	 * URL to this application. Required for emails
	 */
	@NotEmpty
	@URL
	private String url;

	/**
	 * Instance identification. Required for token generator
	 * @see TokenService
	 */
	@NotEmpty
	@Length(min = 3, max = 3)
	private String instanceNo;

	/**
	 * Sender address for emails. Required for emails
	 */
	@NotEmpty
	@Email
	private String defaultEmailSender;

	/**
	 * How long after the last access an user is going to be inactivated
	 * <p>
	 * Default: 365d
	 */
	@Nullable
	private Duration inactiveUserMaxAge;

	/**
	 * How long an unconfirmed signed up user stays in the database
	 * <p>
	 * Default: 2 days
	 */
	@NotNull
	private Duration signupNotConfirmedUserMaxAge;

	/**
	 * How long an expired user stays in the database. After that time the user is getting
	 * deleted from the database.
	 * <p>
	 * Default: 365 days
	 */
	@Nullable
	private Duration expiredUserMaxAge;

	/**
	 * How long after the last access a session is going to be destroyed
	 * <p>
	 * Default: 30 days
	 */
	@NotNull
	private Duration inactiveSessionMaxAge;

	/**
	 * How long a password reset token is valid
	 * <p>
	 * Default: 1 hour
	 */
	@NotNull
	private Duration passwordResetTokenMaxAge;

	@NotNull
	private String photoStorageLocation;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDefaultEmailSender() {
		return this.defaultEmailSender;
	}

	public void setDefaultEmailSender(String defaultEmailSender) {
		this.defaultEmailSender = defaultEmailSender;
	}

	public Duration getInactiveUserMaxAge() {
		return this.inactiveUserMaxAge;
	}

	public void setInactiveUserMaxAge(Duration inactiveUserMaxAge) {
		this.inactiveUserMaxAge = inactiveUserMaxAge;
	}

	public Duration getSignupNotConfirmedUserMaxAge() {
		return this.signupNotConfirmedUserMaxAge;
	}

	public void setSignupNotConfirmedUserMaxAge(Duration signupNotConfirmedUserMaxAge) {
		this.signupNotConfirmedUserMaxAge = signupNotConfirmedUserMaxAge;
	}

	public Duration getExpiredUserMaxAge() {
		return this.expiredUserMaxAge;
	}

	public void setExpiredUserMaxAge(Duration expiredUserMaxAge) {
		this.expiredUserMaxAge = expiredUserMaxAge;
	}

	public Duration getInactiveSessionMaxAge() {
		return this.inactiveSessionMaxAge;
	}

	public void setInactiveSessionMaxAge(Duration inactiveSessionMaxAge) {
		this.inactiveSessionMaxAge = inactiveSessionMaxAge;
	}

	public Duration getPasswordResetTokenMaxAge() {
		return this.passwordResetTokenMaxAge;
	}

	public void setPasswordResetTokenMaxAge(Duration passwordResetTokenMaxAge) {
		this.passwordResetTokenMaxAge = passwordResetTokenMaxAge;
	}

	public String getInstanceNo() {
		return this.instanceNo;
	}

	public void setInstanceNo(String instanceNo) {
		this.instanceNo = instanceNo;
	}

	public String getPhotoStorageLocation() {
		return this.photoStorageLocation;
	}

	public void setPhotoStorageLocation(String photoStorageLocation) {
		this.photoStorageLocation = photoStorageLocation;
	}

}
