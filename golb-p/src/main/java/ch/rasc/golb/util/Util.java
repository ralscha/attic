package ch.rasc.golb.util;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

import org.springframework.util.DigestUtils;

public class Util {

	public static boolean isNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	public static Date ldtToDate(LocalDateTime ldt) {
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static String generateEtag(String input) {
		StringBuilder builder = new StringBuilder(37);
		builder.append("\"0");
		DigestUtils.appendMd5DigestAsHex(input.getBytes(StandardCharsets.UTF_8), builder);
		builder.append('"');
		return builder.toString();
	}

}
