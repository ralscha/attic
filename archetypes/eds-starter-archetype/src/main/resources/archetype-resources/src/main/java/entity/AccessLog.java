#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

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
@Model(value = "${jsAppNamespace}.model.AccessLog", readMethod = "accessLogService.read",
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
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getLogIn() {
		return logIn;
	}

	public void setLogIn(LocalDateTime logIn) {
		this.logIn = logIn;
	}

	public LocalDateTime getLogOut() {
		return logOut;
	}

	public void setLogOut(LocalDateTime logOut) {
		this.logOut = logOut;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getUserAgentName() {
		return userAgentName;
	}

	public void setUserAgentName(String userAgentName) {
		this.userAgentName = userAgentName;
	}

	public String getUserAgentVersion() {
		return userAgentVersion;
	}

	public void setUserAgentVersion(String userAgentVersion) {
		this.userAgentVersion = userAgentVersion;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

}
