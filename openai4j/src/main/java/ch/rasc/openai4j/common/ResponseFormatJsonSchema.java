/*
 * Copyright the original author or authors.
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
package ch.rasc.openai4j.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class ResponseFormatJsonSchema {
	private final String description;
	private final String name;
	private final Object schema;
	private final Boolean strict;

	private ResponseFormatJsonSchema(Builder builder) {
		if (builder.name == null || builder.name.isBlank()) {
			throw new IllegalArgumentException("name must not be null or empty");
		}
		this.description = builder.description;
		this.name = builder.name;
		this.schema = builder.schema;
		this.strict = builder.strict;
	}

	public String description() {
		return this.description;
	}

	public String name() {
		return this.name;
	}

	public Object schema() {
		return this.schema;
	}

	public Boolean strict() {
		return this.strict;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String description;
		private String name;
		private Object schema;
		private Boolean strict;

		private Builder() {
		}

		/**
		 * A description of what the response format is for, used by the model to
		 * determine how to respond in the format.
		 * <p>
		 * Optional
		 */
		public Builder description(String description) {
			this.description = description;
			return this;
		}

		/**
		 * The name of the response format. Must be a-z, A-Z, 0-9, or contain underscores
		 * and dashes, with a maximum length of 64.
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/**
		 * The schema for the response format, described as a JSON Schema object.
		 * <p>
		 * Optional
		 * 
		 * @see JsonSchemaService
		 */
		public Builder schema(Object schema) {
			this.schema = schema;
			return this;
		}

		/**
		 * Whether to enable strict schema adherence when generating the output. If set to
		 * true, the model will always follow the exact schema defined in the schema
		 * field. Only a subset of JSON Schema is supported when strict is true.
		 * <p>
		 * Optional, defaults to false
		 */
		public Builder strict(Boolean strict) {
			this.strict = strict;
			return this;
		}

		public ResponseFormatJsonSchema build() {
			return new ResponseFormatJsonSchema(this);
		}
	}
}
