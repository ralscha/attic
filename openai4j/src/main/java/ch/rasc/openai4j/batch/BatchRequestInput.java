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
package ch.rasc.openai4j.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest;
import ch.rasc.openai4j.embeddings.EmbeddingCreateRequest;

/**
 * The per-line object of the batch input file
 */
@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class BatchRequestInput<T> {

	@JsonProperty("custom_id")
	private final String customId;

	private final String method;

	private final String url;

	private final T body;

	private BatchRequestInput(Builder<T> builder) {
		if (builder.customId == null || builder.customId.isBlank()) {
			throw new IllegalArgumentException("customId must not be null or empty");
		}
		this.customId = builder.customId;

		if (builder.method == null || builder.method.isBlank()) {
			this.method = "POST";
		}
		else {
			this.method = builder.method;
		}

		if (builder.url == null || builder.url.isBlank()) {
			throw new IllegalArgumentException("url must not be null or empty");
		}
		this.url = builder.url;

		this.body = builder.body;
	}

	public static <T> Builder<T> builder() {
		return new Builder<T>();
	}

	public static final class Builder<T> {
		private String customId;
		private String method;
		private String url;
		private T body;

		/**
		 * A developer-provided per-request id that will be used to match outputs to
		 * inputs. Must be unique for each request in a batch.
		 */
		public Builder<T> customId(String customId) {
			this.customId = customId;
			return this;
		}

		/**
		 * The HTTP method to be used for the request. Currently only POST is supported.
		 */
		public Builder<T> method(String method) {
			this.method = method;
			return this;
		}

		/**
		 * The OpenAI API relative URL to be used for the request. Currently
		 * /v1/chat/completions and /v1/embeddings are supported.
		 */
		public Builder<T> url(String url) {
			this.url = url;
			return this;
		}

		/**
		 * The body of the request
		 */
		public Builder<T> body(T body) {
			this.body = body;
			return this;
		}

		public BatchRequestInput<T> build() {
			return new BatchRequestInput<T>(this);
		}
	}

	public static BatchRequestInput<ChatCompletionCreateRequest> of(String customId,
			ChatCompletionCreateRequest request) {
		return BatchRequestInput.<ChatCompletionCreateRequest>builder().customId(customId)
				.body(request).url("/v1/chat/completions").build();
	}

	public static BatchRequestInput<EmbeddingCreateRequest> of(String customId,
			EmbeddingCreateRequest request) {
		return BatchRequestInput.<EmbeddingCreateRequest>builder().customId(customId)
				.body(request).url("/v1/embeddings").build();
	}

}
