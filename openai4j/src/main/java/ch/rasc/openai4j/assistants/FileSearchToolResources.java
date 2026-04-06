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
package ch.rasc.openai4j.assistants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@SuppressWarnings({ "hiding" })
public class FileSearchToolResources {

	@JsonProperty("vector_store_ids")
	private final List<String> vectorStoreIds;

	@JsonProperty("vector_stores")
	private final List<VectorStore> vectorStores;

	@JsonCreator
	private FileSearchToolResources(
			@JsonProperty("vector_store_ids") List<String> vectorStoreIds,
			@JsonProperty("vector_stores") List<VectorStore> vectorStores) {
		this.vectorStoreIds = vectorStoreIds;
		this.vectorStores = vectorStores;
	}

	private FileSearchToolResources(Builder builder) {
		this.vectorStoreIds = builder.vectorStoreIds;
		this.vectorStores = builder.vectorStores;
	}

	public List<String> vectorStoreIds() {
		return this.vectorStoreIds;
	}

	public List<VectorStore> vectorStores() {
		return this.vectorStores;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private List<String> vectorStoreIds;
		private List<VectorStore> vectorStores;

		/**
		 * The vector store attached to this assistant. There can be a maximum of 1 vector
		 * store attached to the assistant.
		 */
		public Builder vectorStoreIds(String vectorStoreId) {
			this.vectorStoreIds = List.of(vectorStoreId);
			return this;
		}

		/**
		 * A helper to create a vector store with file_ids and attach it to this
		 * assistant. There can be a maximum of 1 vector store attached to the assistant.
		 */
		public Builder vectorStores(VectorStore vectorStore) {
			this.vectorStores = List.of(vectorStore);
			return this;
		}

		/**
		 * A helper to create a vector store with file_ids and attach it to this
		 * assistant. There can be a maximum of 1 vector store attached to the assistant.
		 */
		public Builder vectorStores(
				Function<VectorStore.Builder, VectorStore.Builder> fn) {
			return this.vectorStores(fn.apply(VectorStore.builder()).build());
		}

		public FileSearchToolResources build() {
			return new FileSearchToolResources(this);
		}
	}

	@JsonInclude(Include.NON_EMPTY)
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	@SuppressWarnings({ "unused" })
	static class VectorStore {
		@JsonProperty("file_ids")
		private final List<String> fileIds;
		private final Map<String, String> metadata;

		private VectorStore(Builder builder) {
			this.fileIds = builder.fileIds;
			this.metadata = builder.metadata;
		}

		public static Builder builder() {
			return new Builder();
		}

		public static class Builder {
			private List<String> fileIds;
			private Map<String, String> metadata;

			/**
			 * A list of file IDs to add to the vector store. There can be a maximum of
			 * 10000 files in a vector store.
			 */
			public Builder fileIds(List<String> fileIds) {
				this.fileIds = new ArrayList<>(fileIds);
				return this;
			}

			/**
			 * A list of file IDs to add to the vector store. There can be a maximum of
			 * 10000 files in a vector store.
			 */
			public Builder addFileIds(String... fileIds) {
				if (fileIds == null || fileIds.length == 0) {
					return this;
				}

				if (this.fileIds == null) {
					this.fileIds = new ArrayList<>();
				}
				this.fileIds.addAll(List.of(fileIds));
				return this;
			}

			/**
			 * Set of 16 key-value pairs that can be attached to a vector store. This can
			 * be useful for storing additional information about the vector store in a
			 * structured format. Keys can be a maximum of 64 characters long and values
			 * can be a maxium of 512 characters long.
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

			public VectorStore build() {
				return new VectorStore(this);
			}
		}
	}
}
