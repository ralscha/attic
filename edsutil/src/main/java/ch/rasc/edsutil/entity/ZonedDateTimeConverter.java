/**
 * Copyright 2013-2016 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.edsutil.entity;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ZonedDateTimeConverter
		implements AttributeConverter<ZonedDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(ZonedDateTime value) {
		if (value != null) {
			return Timestamp
					.valueOf(value.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
		}
		return null;
	}

	@Override
	public ZonedDateTime convertToEntityAttribute(Timestamp value) {
		if (value != null) {
			return ZonedDateTime.of(value.toLocalDateTime(), ZoneOffset.UTC);
		}
		return null;
	}

}
