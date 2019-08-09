package ch.rasc.golb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.rasc.extclassgenerator.Model;

@Model(value = "Golb.model.UrlCheck", readMethod = "urlCheckService.read")
@JsonInclude(Include.NON_NULL)
public class UrlCheck {
	private final String url;

	private final String post;

	private final int status;

	private final boolean successful;

	public UrlCheck(String url, String post, int status, boolean successful) {
		this.url = url;
		this.post = post;
		this.status = status;
		this.successful = successful;
	}

	public String getUrl() {
		return this.url;
	}

	public int getStatus() {
		return this.status;
	}

	public String getPost() {
		return this.post;
	}

	public boolean isSuccessful() {
		return this.successful;
	}

}
