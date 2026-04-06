package ch.rasc.springmongodb.fulltext;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
enum StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
	INSTANCE;
	@Override
	public LocalDateTime convert(String source) {
		return source == null ? null : LocalDateTime.parse(source);
	}
}