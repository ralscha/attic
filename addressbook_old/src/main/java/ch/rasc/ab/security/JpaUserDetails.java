package ch.rasc.ab.security;

import java.util.Collection;
import java.util.Date;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import ch.rasc.ab.entity.AppUser;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final ImmutableSet<GrantedAuthority> authorities;

	private final String password;

	private final String username;

	private final boolean enabled;

	private final String fullName;

	private final Long appUserDbId;

	private final boolean locked;

	public JpaUserDetails(AppUser user) {
		this.appUserDbId = user.getId();

		this.password = user.getPasswordHash();
		this.username = user.getUserName();
		this.enabled = user.isEnabled();
		this.fullName = Joiner.on(" ").skipNulls().join(user.getFirstName(),
				user.getName());

		if (user.getLockedOut() != null && user.getLockedOut().after(new Date())) {
			this.locked = true;
		}
		else {
			this.locked = false;
		}

		Builder<GrantedAuthority> builder = ImmutableSet.builder();

		if (user.isAdmin()) {
			builder.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			builder.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		else {
			builder.add(new SimpleGrantedAuthority("ROLE_USER"));
		}

		this.authorities = builder.build();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public Long getAppUserDbId() {
		return this.appUserDbId;
	}

	public String getFullName() {
		return this.fullName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
