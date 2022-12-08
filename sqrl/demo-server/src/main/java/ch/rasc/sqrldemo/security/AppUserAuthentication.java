package ch.rasc.sqrldemo.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AppUserAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;

	private final AppSqrlIdentity userDetail;

	public AppUserAuthentication(AppSqrlIdentity userDetail) {
		this.userDetail = userDetail;
	}

	@Override
	public String getName() {
		return this.userDetail.getIdentityKey();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userDetail.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.userDetail;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
		throw new UnsupportedOperationException(
				"this authentication object is always authenticated");
	}

}
