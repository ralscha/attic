package ch.rasc.eds.starter.entity;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Model(value = "Starter.model.AccessLog", readMethod = "accessLogService.read",
		paging = true)
public class AccessLog extends AbstractPersistable {

	@Size(max = 100)
	@JsonIgnore
	private String sessionId;

	@Size(max = 255)
	private String loginName;

	@ModelField(dateFormat = "time")
	private ZonedDateTime loginTimestamp;

	@JsonIgnore
	private String userAgent;

	@Size(max = 20)
	private String userAgentName;

	@Size(max = 10)
	private String userAgentVersion;

	@Size(max = 20)
	private String operatingSystem;

	@Size(max = 45)
	private String ipAddress;

	@Size(max = 255)
	private String location;

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public ZonedDateTime getLoginTimestamp() {
		return this.loginTimestamp;
	}

	public void setLoginTimestamp(ZonedDateTime loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
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

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
