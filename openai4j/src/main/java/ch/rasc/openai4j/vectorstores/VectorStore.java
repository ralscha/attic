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
package ch.rasc.openai4j.vectorstores;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A vector store is a collection of processed files can be used by the file_search tool.
 */
public record VectorStore(String id, String object,
		@JsonProperty("created_at") long createdAt, String name,
		@JsonProperty("usage_bytes") long usageBytes,
		@JsonProperty("file_counts") FileCounts fileCounts, VectorStoreStatus status,
		@JsonProperty("expires_after") ExpirationPolicy expiresAfter,
		@JsonProperty("expires_at") Long expiresAt,
		@JsonProperty("last_active_at") Long lastActiveAt, Map<String, String> metadata) {

	/**
	 * The identifier, which can be referenced in API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The object type, which is always vector_store.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The Unix timestamp (in seconds) for when the vector store was created.
	 */
	@Override
	public long createdAt() {
		return this.createdAt;
	}

	/**
	 * The name of the vector store.
	 */
	@Override
	public String name() {
		return this.name;
	}

	/**
	 * The total number of bytes used by the files in the vector store.
	 */
	@Override
	public long usageBytes() {
		return this.usageBytes;
	}

	/**
	 * The status of the vector store, which can be either expired, in_progress, or
	 * completed. A status of completed indicates that the vector store is ready for use.
	 */
	public VectorStoreStatus status() {
		return this.status;
	}

	/**
	 * The expiration policy for a vector store.
	 */
	public ExpirationPolicy expiresAfter() {
		return this.expiresAfter;
	}

	/**
	 * The Unix timestamp (in seconds) for when the vector store will expire.
	 */
	public Long expiresAt() {
		return this.expiresAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the vector store was last active.
	 */
	public Long lastActiveAt() {
		return this.lastActiveAt;
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

	/**
	 * The status of the vector store, which can be either expired, in_progress, or
	 * completed. A status of completed indicates that the vector store is ready for use.
	 */
	public enum VectorStoreStatus {
		EXPIRED("expired"), IN_PROGRESS("in_progress"), COMPLETED("completed");

		private final String value;

		VectorStoreStatus(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	/**
	 * The expiration policy for a vector store.
	 */
	public record ExpirationPolicy(ExpirationPolicyAnchor anchor, Integer days) {

		/**
		 * Anchor timestamp after which the expiration policy applies. Supported anchors:
		 * <p>
		 * @see ExpirationPolicyAnchor
		 */
		@Override
		public ExpirationPolicyAnchor anchor() {
			return this.anchor;
		}

		/**
		 * The number of days after the anchor time that the vector store will expire.
		 */
		@Override
		public Integer days() {
			return this.days;
		}
	}

	public enum ExpirationPolicyAnchor {
		LAST_ACTIVE_AT("last_active_at");

		private final String value;

		ExpirationPolicyAnchor(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	public record FileCounts(@JsonProperty("in_progress") int inProgress, int completed,
			int failed, int cancelled, int total) {

		/**
		 * The number of files that are currently being processed.
		 */
		public int inProgress() {
			return this.inProgress;
		}

		/**
		 * The number of files that have been successfully processed.
		 */
		public int completed() {
			return this.completed;
		}

		/**
		 * The number of files that have failed to process.
		 */
		public int failed() {
			return this.failed;
		}

		/**
		 * The number of files that were cancelled.
		 */
		public int cancelled() {
			return this.cancelled;
		}

		/**
		 * The total number of files.
		 */
		public int total() {
			return this.total;
		}
	}

}
