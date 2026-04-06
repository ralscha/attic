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
package ch.rasc.openai4j.vectorstores.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import ch.rasc.openai4j.common.Error;
import ch.rasc.openai4j.vectorstores.ChunkingStrategy;

/**
 * A list of files attached to a vector store.
 */
public record VectorStoreFile(String id, String object,
		@JsonProperty("usage_bytes") long usageBytes,
		@JsonProperty("created_at") long createdAt,
		@JsonProperty("vector_store_id") String vectorStoreId,
		VectorStoreFileStatus status, @JsonProperty("last_error") Error lastError,
		@JsonProperty("chunking_strategy") ChunkingStrategy chunkingStrategy) {

	/**
	 * The identifier, which can be referenced in API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The object type, which is always vector_store.file.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The total vector store usage in bytes. Note that this may be different from the
	 * original file size.
	 */
	@Override
	public long usageBytes() {
		return this.usageBytes;
	}

	/**
	 * The Unix timestamp (in seconds) for when the vector store file was created.
	 */
	@Override
	public long createdAt() {
		return this.createdAt;
	}

	/**
	 * The ID of the vector store that the File is attached to.
	 */
	public String vectorStoreId() {
		return this.vectorStoreId;
	}

	/**
	 * The status of the vector store file, which can be either in_progress, completed,
	 * cancelled, or failed. The status completed indicates that the vector store file is
	 * ready for use.
	 */
	public VectorStoreFileStatus status() {
		return this.status;
	}

	/**
	 * The last error associated with this vector store file. Will be null if there are no
	 * errors.
	 */
	public Error lastError() {
		return this.lastError;
	}

	/**
	 * The strategy used to chunk the file.
	 */
	public ChunkingStrategy chunkingStrategy() {
		return this.chunkingStrategy;
	}

	public enum VectorStoreFileStatus {
		IN_PROGRESS("in_progress"), COMPLETED("completed"), CANCELLED("cancelled"),
		FAILED("failed");

		private final String value;

		VectorStoreFileStatus(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

}
