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

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Batch(String id, String object, String endpoint, BatchErrors errors,
		@JsonProperty("input_file_id") String inputFileId,
		@JsonProperty("completion_window") String completionWindow, String status,
		@JsonProperty("output_file_id") String outputFileId,
		@JsonProperty("error_file_id") String errorFileId,
		@JsonProperty("created_at") String createdAt,
		@JsonProperty("in_progress_at") String inProgressAt,
		@JsonProperty("expires_at") String expiresAt,
		@JsonProperty("finalizing_at") String finalizingAt,
		@JsonProperty("completed_at") String completedAt,
		@JsonProperty("failed_at") String failedAt,
		@JsonProperty("expired_at") String expiredAt,
		@JsonProperty("cancelling_at") String cancellingAt,
		@JsonProperty("cancelled_at") String cancelledAt,
		@JsonProperty("request_counts") RequestCounts requestCounts,
		Map<String, String> metadata) {

	/**
	 * The ID of the batch.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The object type, which is always <code>batch</code>.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The OpenAI API endpoint used by the batch.
	 */
	@Override
	public String endpoint() {
		return this.endpoint;
	}

	/**
	 * The errors that occurred during the batch processing.
	 */
	@Override
	public BatchErrors errors() {
		return this.errors;
	}

	/**
	 * The ID of the input file for the batch.
	 */
	@Override
	public String inputFileId() {
		return this.inputFileId;
	}

	/**
	 * The time frame within which the batch should be processed.
	 */
	@Override
	public String completionWindow() {
		return this.completionWindow;
	}

	/**
	 * The current status of the batch.
	 */
	@Override
	public String status() {
		return this.status;
	}

	/**
	 * The ID of the file containing the outputs of successfully executed requests.
	 */
	@Override
	public String outputFileId() {
		return this.outputFileId;
	}

	/**
	 * The ID of the file containing the outputs of requests with errors.
	 */
	@Override
	public String errorFileId() {
		return this.errorFileId;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch was created.
	 */
	@Override
	public String createdAt() {
		return this.createdAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch started processing.
	 */
	@Override
	public String inProgressAt() {
		return this.inProgressAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch will expire.
	 */
	@Override
	public String expiresAt() {
		return this.expiresAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch started finalizing.
	 */
	@Override
	public String finalizingAt() {
		return this.finalizingAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch was completed.
	 */
	@Override
	public String completedAt() {
		return this.completedAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch failed.
	 */
	@Override
	public String failedAt() {
		return this.failedAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch expired.
	 */
	@Override
	public String expiredAt() {
		return this.expiredAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch started cancelling.
	 */
	@Override
	public String cancellingAt() {
		return this.cancellingAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the batch was cancelled.
	 */
	@Override
	public String cancelledAt() {
		return this.cancelledAt;
	}

	/**
	 * The request counts for different statuses within the batch.
	 */
	@Override
	public RequestCounts requestCounts() {
		return this.requestCounts;
	}

	/**
	 * Set of 16 key-value pairs that can be attached to an object. This can be useful for
	 * storing additional information about the object in a structured format. Keys can be
	 * a maximum of 64 characters long and values can be a maxium of 512 characters long.
	 */
	@Override
	public Map<String, String> metadata() {
		return this.metadata;
	}

	public record BatchErrors(String object, List<BatchError> data) {

		/**
		 * The object type, which is always <code>list</code>.
		 */
		@Override
		public String object() {
			return this.object;
		}

	}

	public record BatchError(String code, String message, String param, Integer line) {
		/**
		 * An error code identifying the error type.
		 */
		@Override
		public String code() {
			return this.code;
		}

		/**
		 * A human-readable message providing more details about the error.
		 */
		@Override
		public String message() {
			return this.message;
		}

		/**
		 * The name of the parameter that caused the error, if applicable.
		 */
		@Override
		public String param() {
			return this.param;
		}

		/**
		 * The line number of the input file where the error occurred, if applicable.
		 */
		@Override
		public Integer line() {
			return this.line;
		}

	}

	public record RequestCounts(int total, int completed, int failed) {

		/**
		 * Total number of requests in the batch.
		 */
		@Override
		public int total() {
			return this.total;
		}

		/**
		 * Number of requests that have been completed successfully.
		 */
		@Override
		public int completed() {
			return this.completed;
		}

		/**
		 * Number of requests that have failed.
		 */
		@Override
		public int failed() {
			return this.failed;
		}

	}

}
