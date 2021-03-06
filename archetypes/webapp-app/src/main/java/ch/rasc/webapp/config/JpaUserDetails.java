package ch.rasc.webapp.config;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import ch.rasc.webapp.entity.Role;
import ch.rasc.webapp.entity.User;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Set<GrantedAuthority> authorities;

	private final String password;

	private final String username;

	private final boolean enabled;

	private final String fullName;

	private Locale locale;

	public JpaUserDetails(User user) {
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

		Set<GrantedAuthority> builder = new HashSet<>();
		for (Role role : user.getRoles()) {
			builder.add(new SimpleGrantedAuthority(role.getName()));
		}

		this.authorities = Collections.unmodifiableSet(builder);
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

	public String getFullName() {
		return this.fullName;
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
		return this.enabled;
	}

	public Locale getLocale() {
		return this.locale;
	}

}
