package ch.rasc.e4ds.security;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import ch.rasc.e4ds.entity.User;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Collection<GrantedAuthority> authorities;

	private final String password;

	private final String username;

	private final boolean enabled;

	private final String fullName;

	private final Long userDbId;

	private final boolean locked;

	private final Locale locale;

	public JpaUserDetails(User user) {
		this.userDbId = user.getId();

		this.password = user.getPasswordHash();
		this.username = user.getUserName();
		this.enabled = user.isEnabled();
		this.fullName = String.join(" ", user.getFirstName(), user.getName());

		if (StringUtils.hasText(user.getLocale())) {
			this.locale = new Locale(user.getLocale());
		}
		else {
			this.locale = Locale.ENGLISH;
		}

		this.locked = user.getLockedOut() != null
				&& user.getLockedOut().isAfter(LocalDateTime.now());

		Set<GrantedAuthority> builder = new HashSet<>();
		for (String role : user.getRole().split(",")) {
			builder.add(new SimpleGrantedAuthority(role));
		}

		this.authorities = Collections.unmodifiableCollection(builder);
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
