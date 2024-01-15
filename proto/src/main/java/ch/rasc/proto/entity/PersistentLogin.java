package ch.rasc.proto.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import ch.rasc.edsutil.jackson.ISO8601LocalDateTimeSerializer;
import ch.rasc.extclassgenerator.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Model(value = "Proto.model.PersistentLogin", idProperty = "series",
		readMethod = "securityService.readPersistentLogins", writeAllFields = false,
		destroyMethod = "securityService.destroyPersistentLogin")
public class PersistentLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String series;

	@JsonIgnore
	@NotNull
	private String token;

	@JsonSerialize(using = ISO8601LocalDateTimeSerializer.class)
	private LocalDateTime lastUsed;

	private String ipAddress;

	private String userAgent;

	@JsonIgnore
	private Long userId;

	public String getSeries() {
		return this.series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getLastUsed() {
		return this.lastUsed;
	}

	public void setLastUsed(LocalDateTime lastUsed) {
		this.lastUsed = lastUsed;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}