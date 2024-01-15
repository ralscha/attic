package ch.rasc.proto.config.security;

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

import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.entity.User;

public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Collection<GrantedAuthority> authorities;

	private final String password;

	private final String loginName;

	private final boolean enabled;

	private final String fullName;

	private final Long userDbId;

	private final boolean locked;

	private final Locale locale;

	public AppUserDetails(User user) {
		this.userDbId = user.getId();

		this.password = user.getPasswordHash();
		this.loginName = user.getLoginName();
		this.enabled = user.isEnabled();
		this.fullName = String.join(" ", user.getFirstName(), user.getLastName());

		if (StringUtils.hasText(user.getLocale())) {
			this.locale = new Locale(user.getLocale());
		}
		else {
			this.locale = Locale.ENGLISH;
		}

		this.locked = user.getLockedOutUntil() != null
				&& user.getLockedOutUntil().isAfter(LocalDateTime.now());

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
		return this.loginName;
	}

	public User getUser(DbManager dbManager) {
		return dbManager.runInTx(db -> {
			return DbManager.get(db, User.class, getUserDbId());
		});
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

	public boolean hasRole(String role) {
		if (getAuthorities() != null) {
			for (GrantedAuthority authority : getAuthorities()) {
				String userRole = authority.getAuthority();
				if (role.equals(userRole)) {
					return true;
				}
			}
		}

		return false;
	}
}
