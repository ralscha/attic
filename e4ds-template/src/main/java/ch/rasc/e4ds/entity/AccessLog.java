package ch.rasc.e4ds.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.jackson.ISO8601LocalDateTimeSerializer;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Model(value = "E4ds.model.AccessLog", readMethod = "accessLogService.read",
		paging = true)
public class AccessLog extends AbstractPersistable {

	@Size(max = 100)
	@JsonIgnore
	private String sessionId;

	@Size(max = 255)
	private String userName;

	@ModelField(dateFormat = "c")
	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	private LocalDateTime logIn;

	@ModelField(dateFormat = "c")
	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	private LocalDateTime logOut;

	@ModelField
	@Transient
	private String duration;

	@JsonIgnore
	private String userAgent;

	@Size(max = 20)
	private String userAgentName;

	@Size(max = 10)
	private String userAgentVersion;

	@Size(max = 20)
	private String operatingSystem;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getLogIn() {
		return this.logIn;
	}

	public void setLogIn(LocalDateTime logIn) {
		this.logIn = logIn;
	}

	public LocalDateTime getLogOut() {
		return this.logOut;
	}

	public void setLogOut(LocalDateTime logOut) {
		this.logOut = logOut;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getUserAgentName() {
		return this.userAgentName;
	}

	public void setUserAgentName(String userAgentName) {
		this.userAgentName = userAgentName;
	}

	public String getUserAgentVersion() {
		return this.userAgentVersion;
	}

	public void setUserAgentVersion(String userAgentVersion) {
		this.userAgentVersion = userAgentVersion;
	}

	public String getOperatingSystem() {
		return this.operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

}
