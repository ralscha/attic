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
package ch.rasc.openai4j.embeddings;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class EmbeddingCreateRequest {

	private final Object input;
	private final String model;
	@JsonProperty("encoding_format")
	private final EncodingFormat encodingFormat;
	private final Integer dimensions;
	private final String user;

	private EmbeddingCreateRequest(Builder builder) {
		if (builder.input == null) {
			throw new IllegalArgumentException("input must not be null");
		}
		if (builder.model == null || builder.model.isBlank()) {
			throw new IllegalArgumentException("model must not be null or empty");
		}
		this.input = builder.input;
		this.model = builder.model;
		this.encodingFormat = builder.encodingFormat;
		this.dimensions = builder.dimensions;
		this.user = builder.user;
	}

	public enum EncodingFormat {
		FLOAT("float"), BASE64("base64");

		private final String value;

		EncodingFormat(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Object input;
		private String model;
		private EncodingFormat encodingFormat;
		private Integer dimensions;
		private String user;

		private Builder() {
		}

		/**
		 * Input text to embed, encoded as a string. The input must not exceed the max
		 * input tokens for the model (8192 tokens for text-embedding-ada-002), cannot be
		 * an empty string.
		 */
		public Builder input(String input) {
			this.input = input;
			return this;
		}

		/**
		 * Input text to embed, encoded as an array of strings. Each input input must not
		 * exceed the max input tokens for the model (8192 tokens for
		 * text-embedding-ada-002), array must be 2048 dimensions or less.
		 */
		public Builder input(List<String> input) {
			this.input = input;
			return this;
		}

		/**
		 * The array of integers that will be turned into an embedding. The input must not
		 * exceed the max input tokens for the model (8192 tokens for
		 * text-embedding-ada-002), cannot be empty, and any array must be 2048 dimensions
		 * or less.
		 */
		public Builder input(int[] input) {
			this.input = input;
			return this;
		}

		/**
		 * The array of arrays containing integers that will be turned into an embedding.
		 * The input must not exceed the max input tokens for the model (8192 tokens for
		 * text-embedding-ada-002), cannot be empty, and any array must be 2048 dimensions
		 * or less.
		 */
		public Builder input(int[][] input) {
			this.input = input;
			return this;
		}

		/**
		 * ID of the model to use. You can use the List models API to see all of your
		 * available models.
		 */
		public Builder model(String model) {
			this.model = model;
			return this;
		}

		/**
		 * The format to return the embeddings in. Can be either float or base64. Defaults
		 * to float. In this client the floats are mapped into doubles.
		 */
		public Builder encodingFormat(EncodingFormat encodingFormat) {
			this.encodingFormat = encodingFormat;
			return this;
		}

		/**
		 * The number of dimensions the resulting output embeddings should have. Only
		 * supported in text-embedding-3 and later models.
		 */
		public Builder dimensions(Integer dimensions) {
			this.dimensions = dimensions;
			return this;
		}

		/**
		 * A unique identifier representing your end-user, which can help OpenAI to
		 * monitor and detect abuse.
		 */
		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public EmbeddingCreateRequest build() {
			return new EmbeddingCreateRequest(this);
		}
	}
}
