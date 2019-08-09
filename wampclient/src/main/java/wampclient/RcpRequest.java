package wampclient;

public class RcpRequest {
	private String state;

	private String reason;

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "RcpRequest [state=" + this.state + ", reason=" + this.reason + "]";
	}

}
