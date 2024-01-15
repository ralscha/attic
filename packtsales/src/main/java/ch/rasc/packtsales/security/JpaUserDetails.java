package ch.rasc.packtsales.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.ImmutableSet;

import ch.rasc.packtsales.entity.AppUser;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final ImmutableSet<GrantedAuthority> authorities;

	private final String passwd;

	private final String email;

	private final Long userDbId;

	public JpaUserDetails(AppUser user) {
		this.userDbId = user.getId();

		this.passwd = user.getPasswd();
		this.email = user.getEmail();

		this.authorities = ImmutableSet.of();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.passwd;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public Long getUserDbId() {
		return this.userDbId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
