package wampclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@JsonProperty("UserData1")
	private String userData1;

	@JsonProperty("UserData2")
	private String userData2;

	@JsonProperty("UserData3")
	private String userData3;

	@JsonProperty("UserData4")
	private String userData4;

	private UserReason reason;

	@JsonProperty("UserData7")
	private String userData7;

	@JsonProperty("UserData8")
	private String userData8;

	@JsonProperty("UserData9")
	private String userData9;

	@JsonProperty("UserData10")
	private String userData10;

	public String getUserData1() {
		return this.userData1;
	}

	public void setUserData1(String userData1) {
		this.userData1 = userData1;
	}

	public String getUserData2() {
		return this.userData2;
	}

	public void setUserData2(String userData2) {
		this.userData2 = userData2;
	}

	public String getUserData3() {
		return this.userData3;
	}

	public void setUserData3(String userData3) {
		this.userData3 = userData3;
	}

	public String getUserData4() {
		return this.userData4;
	}

	public void setUserData4(String userData4) {
		this.userData4 = userData4;
	}

	public UserReason getReason() {
		return this.reason;
	}

	public void setReason(UserReason reason) {
		this.reason = reason;
	}

	public String getUserData7() {
		return this.userData7;
	}

	public void setUserData7(String userData7) {
		this.userData7 = userData7;
	}

	public String getUserData8() {
		return this.userData8;
	}

	public void setUserData8(String userData8) {
		this.userData8 = userData8;
	}

	public String getUserData9() {
		return this.userData9;
	}

	public void setUserData9(String userData9) {
		this.userData9 = userData9;
	}

	public String getUserData10() {
		return this.userData10;
	}

	public void setUserData10(String userData10) {
		this.userData10 = userData10;
	}

}
