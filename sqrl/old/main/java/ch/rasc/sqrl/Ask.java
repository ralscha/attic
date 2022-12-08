package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Holds optional response to queries that will be shown to the user from the SQRL client
 */
@JsonInclude(Include.NON_NULL)
public class Ask {
	private String message;
	private String button1;
	private String url1;
	private String button2;
	private String url2;

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getButton1() {
		return this.button1;
	}

	public void setButton1(String button1) {
		this.button1 = button1;
	}

	public String getUrl1() {
		return this.url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	public String getButton2() {
		return this.button2;
	}

	public void setButton2(String button2) {
		this.button2 = button2;
	}

	public String getUrl2() {
		return this.url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String encode() {
		List<String> strings = new ArrayList<>();

		strings.add(Base64.getUrlEncoder().withoutPadding()
				.encodeToString(this.message.getBytes(StandardCharsets.UTF_8)));

		String button = encodeButton(this.button1, this.url1);
		if (button != null) {
			strings.add(button);
		}
		button = encodeButton(this.button2, this.url2);
		if (button != null) {
			strings.add(button);
		}

		return strings.stream().collect(Collectors.joining("~"));
	}

	private static String encodeButton(String button, String url) {
		if (button != null && !button.isBlank()) {
			String encoded = button.replace(";", "");
			if (url != null && !url.isBlank()) {
				encoded = encoded + ";" + url;
			}
		}
		return null;
	}

}
