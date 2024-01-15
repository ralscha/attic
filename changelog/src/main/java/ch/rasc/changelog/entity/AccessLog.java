package ch.rasc.changelog.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.jackson.ISO8601DateTimeSerializer;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

@Entity
@Model(value = "Changelog.model.AccessLog", readMethod = "accessLogService.read",
		paging = true)
public class AccessLog extends AbstractPersistable {

	@Size(max = 100)
	@JsonIgnore
	private String sessionId;

	@Size(max = 255)
	private String userName;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@ModelField(dateFormat = "c")
	private DateTime logIn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@ModelField(dateFormat = "c")
	private DateTime logOut;

	@ModelField
	@Transient
	private String duration;

	@JsonIgnore
	private String userAgent;

	@ModelField
	@Transient
	private String browser;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getLogIn() {
		return this.logIn;
	}

	public void setLogIn(DateTime logIn) {
		this.logIn = logIn;
	}

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getLogOut() {
		return this.logOut;
	}

	public void setLogOut(DateTime logOut) {
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

	public String getBrowser() {
		if (this.userAgent != null) {
			UserAgentStringParser parser = UADetectorServiceFactory
					.getResourceModuleParser();
			ReadableUserAgent agent = parser.parse(this.userAgent);
			return agent.getName() + " " + agent.getVersionNumber().getMajor() + " ("
					+ agent.getOperatingSystem().getName() + ")";
		}
		return "";
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
