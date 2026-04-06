package ch.rasc.springmongodb.fulltext;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
enum LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
	INSTANCE;
	@Override
	public String convert(LocalDateTime source) {
		return source == null ? null : source.toString();
	}
}