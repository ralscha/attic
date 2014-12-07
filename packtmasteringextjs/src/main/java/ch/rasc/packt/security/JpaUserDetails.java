package ch.rasc.packt.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ch.rasc.packt.entity.User;

import com.google.common.collect.ImmutableSet;

public class JpaUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final ImmutableSet<GrantedAuthority> authorities;

	private final String password;

	private final String username;

	private final String name;

	private final Long userDbId;

	public JpaUserDetails(User user) {
		this.userDbId = user.getId();

		this.password = user.getPassword();
		this.username = user.getUserName();
		this.name = user.getName();

		this.authorities = ImmutableSet.<GrantedAuthority> of(new SimpleGrantedAuthority(
				user.getAppGroup().getName()));
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public Long getUserDbId() {
		return userDbId;
	}

	public String getName() {
		return name;
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
