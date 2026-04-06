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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The per-line object of the batch output and error files
 */
public record BatchRequestOutput<T>(String id, @JsonProperty("custom_id") String customId,
		Response<T> response, Error error) {

	/**
	 * The ID of the batch.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * A developer-provided per-request id that will be used to match outputs to inputs.
	 */
	@Override
	public String customId() {
		return this.customId;
	}

	/**
	 * For requests that failed with a non-HTTP error, this will contain more information
	 * on the cause of the failure.
	 */
	@Override
	public Error error() {
		return this.error;
	}

	public record Response<T>(@JsonProperty("status_code") int statusCode,
			@JsonProperty("request_id") String requestId, T body) {
		/**
		 * The HTTP status code of the response
		 */
		@Override
		public int statusCode() {
			return this.statusCode;
		}

		/**
		 * An unique identifier for the OpenAI API request. Include this request ID when
		 * contacting support.
		 */
		@Override
		public String requestId() {
			return this.requestId;
		}

		/**
		 * The body of the response
		 */
		@Override
		public T body() {
			return this.body;
		}
	}

	public record Error(String code, String message) {
		/**
		 * A machine-readable error code.
		 */
		@Override
		public String code() {
			return this.code;
		}

		/**
		 * A human-readable error message.
		 */
		@Override
		public String message() {
			return this.message;
		}
	}

}
