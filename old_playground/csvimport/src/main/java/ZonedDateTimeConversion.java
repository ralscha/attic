import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.univocity.parsers.conversions.ObjectConversion;

public class ZonedDateTimeConversion extends ObjectConversion<ZonedDateTime> {

	private final DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyyMMddHHmm").withZone(ZoneOffset.UTC);

	public ZonedDateTimeConversion(@SuppressWarnings("unused") String[] dummy) {
		super(null, null);
	}

	@Override
	public String revert(ZonedDateTime input) {
		if (input == null) {
			return super.revert(null);
		}
		return input.format(this.formatter);
	}

	@Override
	protected ZonedDateTime fromString(String input) {
		return ZonedDateTime.from(this.formatter.parse(input));
	}

}
