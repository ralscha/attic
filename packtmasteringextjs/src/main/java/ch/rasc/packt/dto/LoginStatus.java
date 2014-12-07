package ch.rasc.packt.dto;

public class LoginStatus {

	private final boolean success;

	private final String msg;

	public LoginStatus(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMsg() {
		return msg;
	}

}