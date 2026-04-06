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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.vectorstores.VectorStore.ExpirationPolicy;
import ch.rasc.openai4j.vectorstores.VectorStore.ExpirationPolicyAnchor;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class VectorStoreCreateRequest {

	@JsonProperty("file_ids")
	private final List<String> fileIds;
	private final String name;
	@JsonProperty("expires_after")
	private final ExpirationPolicy expiresAfter;
	@JsonProperty("chunking_strategy")
	private final ChunkingStrategy chunkingStrategy;
	private final Map<String, String> metadata;

	private VectorStoreCreateRequest(Builder builder) {
		this.fileIds = builder.fileIds;
		this.name = builder.name;
		this.expiresAfter = builder.expiresAfter;
		this.chunkingStrategy = builder.chunkingStrategy;
		this.metadata = builder.metadata;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<String> fileIds;
		private String name;
		private ExpirationPolicy expiresAfter;
		private ChunkingStrategy chunkingStrategy;
		private Map<String, String> metadata;

		private Builder() {
		}

		/**
		 * A list of File IDs that the vector store should use. Useful for tools like
		 * file_search that can access files.
		 */
		public Builder fileIds(List<String> fileIds) {
			this.fileIds = new ArrayList<>(fileIds);
			return this;
		}

		/**
		 * A list of File IDs that the vector store should use. Useful for tools like
		 * file_search that can access files.
		 */
		public Builder addFileIds(String... fileIds) {
			if (fileIds == null || fileIds.length == 0) {
				this.fileIds = new ArrayList<>();
			}
			if (this.fileIds == null) {
				this.fileIds = new ArrayList<>();
			}
			this.fileIds.addAll(List.of(fileIds));
			return this;
		}

		/**
		 * The name of the vector store.
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/**
		 * The expiration policy for a vector store.
		 */
		public Builder expiresAfterLastActiveAt(int days) {
			this.expiresAfter = new ExpirationPolicy(
					ExpirationPolicyAnchor.LAST_ACTIVE_AT, days);
			return this;
		}

		/**
		 * The chunking strategy used to chunk the file(s). If not set, will use the auto
		 * strategy. Only applicable if file_ids is non-empty.
		 */
		public Builder chunkingStrategy(ChunkingStrategy chunkingStrategy) {
			this.chunkingStrategy = chunkingStrategy;
			return this;
		}

		/**
		 * Set of 16 key-value pairs that can be attached to an object. This can be useful
		 * for storing additional information about the object in a structured format.
		 * Keys can be a maximum of 64 characters long and values can be a maxium of 512
		 * characters long.
		 */
		public Builder metadata(Map<String, String> metadata) {
			this.metadata = new HashMap<>(metadata);
			return this;
		}

		/**
		 * Add a key-value pair to the metadata
		 */
		public Builder putMetadata(String key, String value) {
			if (this.metadata == null) {
				this.metadata = new HashMap<>();
			}
			this.metadata.put(key, value);
			return this;
		}

		public VectorStoreCreateRequest build() {
			return new VectorStoreCreateRequest(this);
		}
	}

}
