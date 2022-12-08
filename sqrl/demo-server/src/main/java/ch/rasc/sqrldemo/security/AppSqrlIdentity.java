package ch.rasc.sqrldemo.security;

import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import ch.rasc.sqrl.auth.SqrlIdentity;
import ch.rasc.sqrldemo.db.tables.records.AppUserRecord;

public class AppSqrlIdentity implements SqrlIdentity {

	private final String identityKey;

	private final String serverUnlockKey;

	private final String verifyUnlockKey;

	private final boolean enabled;

	private final Set<GrantedAuthority> authorities;

	public AppSqrlIdentity(AppUserRecord user) {
		this.identityKey = user.getIdentityKey();
		this.serverUnlockKey = user.getServerUnlockKey();
		this.verifyUnlockKey = user.getVerifyUnlockKey();
		this.authorities = Set.of();
		this.enabled = Objects.requireNonNullElse(user.getEnabled(), false);
	}

	public Set<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getIdentityKey() {
		return this.identityKey;
	}

	@Override
	public String getServerUnlockKey() {
		return this.serverUnlockKey;
	}

	@Override
	public String getVerifyUnlockKey() {
		return this.verifyUnlockKey;
	}

	@Override
	public boolean isDisabled() {
		return !this.enabled;
	}

}
