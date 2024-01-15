package ch.rasc.bitprototype.security;

import java.util.Collection;
import java.util.Locale;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import ch.rasc.bitprototype.entity.User;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final ImmutableSet<GrantedAuthority> authorities;

	private final String password;

	private final String username;

	private final boolean enabled;

	private final String fullName;

	private final Long userDbId;

	private final boolean locked;

	private Locale locale;

	public JpaUserDetails(User user) {
		this.userDbId = user.getId();

		this.password = user.getPasswordHash();
		this.username = user.getUserName();
		this.enabled = user.isEnabled();
		this.fullName = Joiner.on(" ").skipNulls().join(user.getFirstName(),
				user.getName());

		if (StringUtils.hasText(user.getLocale())) {
			this.locale = new Locale(user.getLocale());
		}
		else {
			this.locale = Locale.ENGLISH;
		}

		if (user.getLockedOut() != null && user.getLockedOut().isAfter(DateTime.now())) {
			this.locked = true;
		}
		else {
			this.locked = false;
		}

		Builder<GrantedAuthority> builder = ImmutableSet.builder();
		for (String role : Splitter.on(",").split(user.getRole())) {
			builder.add(new SimpleGrantedAuthority(role));
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

	public Long getUserDbId() {
		return this.userDbId;
	}

	public String getFullName() {
		return this.fullName;
	}

	public Locale getLocale() {
		return this.locale;
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
