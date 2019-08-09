package wampclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserReason {
	@JsonProperty("UserData5")
	private String userData5;

	@JsonProperty("UserData6")
	private String userData6;

	public String getUserData5() {
		return this.userData5;
	}

	public void setUserData5(String userData5) {
		this.userData5 = userData5;
	}

	public String getUserData6() {
		return this.userData6;
	}

	public void setUserData6(String userData6) {
		this.userData6 = userData6;
	}

}
