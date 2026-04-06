package ch.rasc.base;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Test {

	public static void main(String[] args) {
		String test = "äöüTESTÖÄÜ";
		byte[] encoded = Base91.encode(test.getBytes(StandardCharsets.UTF_8));

		String encodedString = new String(encoded, StandardCharsets.ISO_8859_1);
		System.out.println(encodedString);

		byte[] decoded = Base91
				.decode(encodedString.getBytes(StandardCharsets.ISO_8859_1));
		String decodedString = new String(decoded, StandardCharsets.UTF_8);
		System.out.println(decodedString);

		String base64 = Base64.getEncoder()
				.encodeToString(test.getBytes(StandardCharsets.UTF_8));
		System.out.println(base64);
		String decoded64 = new String(Base64.getDecoder().decode(base64),
				StandardCharsets.UTF_8);
		System.out.println(decoded64);
	}

}
