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
package ch.rasc.openai4j.vectorstores.filebatches;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.vectorstores.VectorStore.FileCounts;
import ch.rasc.openai4j.vectorstores.files.VectorStoreFile.VectorStoreFileStatus;

/**
 * A batch of files attached to a vector store.
 */
public record VectorStoreFileBatch(String id, String object,
		@JsonProperty("created_at") long createdAt,
		@JsonProperty("vector_store_id") String vectorStoreId,
		VectorStoreFileStatus status,
		@JsonProperty("file_counts") FileCounts fileCounts) {

	/**
	 * The identifier, which can be referenced in API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The object type, which is always vector_store.file_batch.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The Unix timestamp (in seconds) for when the vector store files batch was created.
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
	 * The status of the vector store files batch, which can be either in_progress,
	 * completed, cancelled or failed.
	 */
	public VectorStoreFileStatus status() {
		return this.status;
	}

	public FileCounts fileCounts() {
		return this.fileCounts;
	}

}
