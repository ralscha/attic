package ch.rasc.e4ds.domain;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AccessLog {

	@Id
	private String id;

	private String sessionId;

	private String userName;

	private DateTime logIn;

	private DateTime logOut;

	public AccessLog() {
		// default constructor
	}

	public AccessLog(String userName, String sessionId) {
		this.userName = userName;
		this.sessionId = sessionId;
		this.logIn = DateTime.now();
		this.logOut = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public DateTime getLogIn() {
		return logIn;
	}

	public void setLogIn(DateTime logIn) {
		this.logIn = logIn;
	}

	public DateTime getLogOut() {
		return logOut;
	}

	public void setLogOut(DateTime logOut) {
		this.logOut = logOut;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getDurationInSeconds() {
		if (logIn != null && logOut != null) {
			Duration duration = new Duration(logIn, logOut);
			return duration.getStandardSeconds();
		}
		return null;
	}

}
