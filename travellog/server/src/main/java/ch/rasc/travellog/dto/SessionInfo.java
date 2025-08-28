package ch.rasc.travellog.dto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SessionInfo {

	private final String id;

	private final Boolean loggedIn;

	private final long lastAccess;

	private final String ip;

	private final String userAgent;

	public SessionInfo(String id, Boolean loggedIn, LocalDateTime lastAccess, String ip,
			String userAgent) {
		this.id = id;
		this.loggedIn = loggedIn;
		this.lastAccess = lastAccess.toEpochSecond(ZoneOffset.UTC);
		this.ip = ip;
		this.userAgent = userAgent;
	}

	public String getId() {
		return this.id;
	}

	public Boolean getLoggedIn() {
		return this.loggedIn;
	}

	public long getLastAccess() {
		return this.lastAccess;
	}

	public String getIp() {
		return this.ip;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

}
