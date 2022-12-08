package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SqrlAsk {

	private final String messageText;
	private final String button1;
	private final String url1;
	private final String button2;
	private final String url2;

	public SqrlAsk(String messageText, String button1, String url1, String button2,
			String url2) {
		this.messageText = messageText;
		this.button1 = button1;
		this.url1 = url1;
		this.button2 = button2;
		this.url2 = url2;
	}

	public String encode() {
		List<String> parts = new ArrayList<>();

		parts.add(Base64.getUrlEncoder().withoutPadding()
				.encodeToString(this.messageText.getBytes(StandardCharsets.UTF_8)));

		String button = encodeButton(this.button1, this.url1);
		if (button != null) {
			parts.add(button);
		}
		button = encodeButton(this.button2, this.url2);
		if (button != null) {
			parts.add(button);
		}

		return parts.stream().collect(Collectors.joining("~"));
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

	public String getMessageText() {
		return this.messageText;
	}

	public String getButton1() {
		return this.button1;
	}

	public String getUrl1() {
		return this.url1;
	}

	public String getButton2() {
		return this.button2;
	}

	public String getUrl2() {
		return this.url2;
	}

}
