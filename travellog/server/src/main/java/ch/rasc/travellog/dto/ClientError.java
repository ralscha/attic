package ch.rasc.travellog.dto;

public class ClientError {
	private long ts;
	private UserAgent userAgent;
	private String error;

	public ClientError() {
	}

	public long getTs() {
		return this.ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

	public UserAgent getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ClientError [ts=" + this.ts + ", userAgent=" + this.userAgent + ", error="
				+ this.error + "]";
	}

}
