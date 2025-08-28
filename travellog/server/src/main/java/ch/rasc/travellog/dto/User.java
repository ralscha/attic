package ch.rasc.travellog.dto;

import java.time.ZoneOffset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.rasc.travellog.db.tables.records.AppUserRecord;

@JsonInclude(Include.NON_NULL)
public class User {

	private String id;

	private String email;

	private Long lastAccess;

	private String authority;

	private boolean enabled;

	private boolean expired;

	private boolean admin;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getLastAccess() {
		return this.lastAccess;
	}

	public void setLastAccess(Long lastAccess) {
		this.lastAccess = lastAccess;
	}

	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isExpired() {
		return this.expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public User(String id, AppUserRecord record) {
		this.id = id;
		this.email = record.getEmail();
		this.lastAccess = record.getLastAccess() != null
				? record.getLastAccess().toEpochSecond(ZoneOffset.UTC)
				: null;
		this.authority = record.getAuthority();
		this.enabled = record.getEnabled();
		this.expired = record.getExpired() != null;
		this.admin = "ADMIN".equals(record.getAuthority());
	}

}
