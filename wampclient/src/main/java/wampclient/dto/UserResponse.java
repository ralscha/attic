package wampclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
	@JsonProperty("User")
	private User user;

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
