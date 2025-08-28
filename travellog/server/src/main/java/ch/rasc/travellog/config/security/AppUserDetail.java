package ch.rasc.travellog.config.security;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AppUserDetail {

	private final Long appUserId;

	private final String email;

	private final Set<GrantedAuthority> authorities;

	public AppUserDetail(long appUserId, String email, String authority) {
		this.appUserId = appUserId;
		this.email = email;
		this.authorities = Set.of(new SimpleGrantedAuthority(authority));
	}

	public Long getAppUserId() {
		return this.appUserId;
	}

	public String getEmail() {
		return this.email;
	}

	public Set<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

}
