package ch.rasc.tttracker.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ch.rasc.tttracker.domain.User;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Set<GrantedAuthority> authorities;

	private final String password;

	private final String username;

	public JpaUserDetails(User user) {
		this.password = user.getPassword();
		this.username = user.getUserName();

		if (user.getAdminRole().equals("Y")) {
			this.authorities = Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
		}
		else {
			this.authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
		}
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
