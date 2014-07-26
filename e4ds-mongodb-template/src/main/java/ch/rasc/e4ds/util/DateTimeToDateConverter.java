package ch.rasc.e4ds.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

public enum DateTimeToDateConverter implements Converter<DateTime, Date> {
	INSTANCE;

	@Override
	public Date convert(DateTime source) {
		return source != null ? source.toDate() : null;
	}
}
