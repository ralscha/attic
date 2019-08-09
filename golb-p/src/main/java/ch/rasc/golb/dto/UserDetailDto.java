package ch.rasc.golb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.rasc.golb.config.security.MongoUserDetails;
import ch.rasc.golb.entity.Authority;
import ch.rasc.golb.entity.User;

@JsonInclude(Include.NON_NULL)
public class UserDetailDto {

	private final String firstName;

	private final String lastName;

	private final String locale;

	private final String autoOpenView;

	private final boolean screenLocked;

	private final boolean preAuth;

	private final String csrf;

	public UserDetailDto(MongoUserDetails userDetails, User user, String csrf) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.locale = user.getLocale();
		this.screenLocked = userDetails.isScreenLocked();
		this.preAuth = userDetails.isPreAuth();

		if (userDetails.hasAuthority(Authority.ADMIN.name())) {
			this.autoOpenView = "post";
		}
		else {
			this.autoOpenView = null;
		}

		this.csrf = csrf;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getLocale() {
		return this.locale;
	}

	public String getAutoOpenView() {
		return this.autoOpenView;
	}

	public boolean isScreenLocked() {
		return this.screenLocked;
	}

	public boolean isPreAuth() {
		return this.preAuth;
	}

	public String getCsrf() {
		return this.csrf;
	}

}
