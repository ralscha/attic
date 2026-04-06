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

import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class ResponseFormat {
	private final Object value;

	@JsonCreator
	ResponseFormat(Object value) {
		this.value = value;
	}

	/**
	 * If text the model can return text or any value needed.
	 */
	public static ResponseFormat text() {
		return new ResponseFormat(Map.of("type", "text"));
	}

	/**
	 * Setting to { "type": "json_object" } enables JSON mode, which guarantees the
	 * message the model generates is valid JSON.
	 * <p>
	 * Important: when using JSON mode, you must also instruct the model to produce JSON
	 * yourself via a system or user message. Without this, the model may generate an
	 * unending stream of whitespace until the generation reaches the token limit,
	 * resulting in a long-running and seemingly "stuck" request. Also note that the
	 * message content may be partially cut off if finish_reason="length", which indicates
	 * the generation exceeded max_tokens or the conversation exceeded the max context
	 * length.
	 */
	public static ResponseFormat jsonObject() {
		return new ResponseFormat(Map.of("type", "json_object"));
	}

	/**
	 * Setting to { "type": "json_schema", "json_schema": {...} } enables Structured
	 * Outputs which ensures the model will match your supplied JSON schema.
	 */
	public static ResponseFormat jsonSchema(ResponseFormatJsonSchema jsonSchema) {
		return new ResponseFormat(
				Map.of("type", "json_schema", "json_schema", jsonSchema));
	}

	/**
	 * Setting to { "type": "json_schema", "json_schema": {...} } enables Structured
	 * Outputs which ensures the model will match your supplied JSON schema.
	 */
	public static ResponseFormat jsonSchema(
			Function<ResponseFormatJsonSchema.Builder, ResponseFormatJsonSchema.Builder> fn) {
		return jsonSchema(fn.apply(ResponseFormatJsonSchema.builder()).build());
	}

	/**
	 * auto is the default value
	 */
	public static ResponseFormat auto() {
		return new ResponseFormat("auto");
	}

	@JsonValue
	public Object value() {
		return this.value;
	}

}
