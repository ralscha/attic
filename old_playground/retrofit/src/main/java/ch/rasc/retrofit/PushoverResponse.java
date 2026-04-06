package ch.rasc.retrofit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushoverResponse {
	private int status;
	private String request;
	private List<String> errors;

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "PushoverResponse [status=" + this.status + ", request=" + this.request
				+ ", errors=" + this.errors + "]";
	}

}
