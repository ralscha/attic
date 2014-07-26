package ch.rasc.e4ds.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

public enum DateToDateTimeConverter implements Converter<Date, DateTime> {
	INSTANCE;

	@Override
	public DateTime convert(Date source) {
		return source != null ? new DateTime(source) : null;
	}
}
