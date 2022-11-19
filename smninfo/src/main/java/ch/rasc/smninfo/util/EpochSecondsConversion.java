package ch.rasc.smninfo.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.univocity.parsers.conversions.ObjectConversion;

public class EpochSecondsConversion extends ObjectConversion<Long> {

	private final DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyyMMddHHmm").withZone(ZoneOffset.UTC);

	public EpochSecondsConversion(@SuppressWarnings("unused") String[] dummy) {
		super(null, null);
	}

	@Override
	public String revert(Long input) {
		if (input == null) {
			return super.revert(null);
		}
		return this.formatter.format(Instant.ofEpochSecond(input));
	}

	@Override
	protected Long fromString(String input) {
		return Instant.from(this.formatter.parse(input)).getEpochSecond();
	}

}
