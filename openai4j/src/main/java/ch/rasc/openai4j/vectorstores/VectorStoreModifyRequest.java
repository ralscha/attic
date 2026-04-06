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

import java.util.HashMap;
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
public class VectorStoreModifyRequest {

	private final String name;
	@JsonProperty("expires_after")
	private final ExpirationPolicy expiresAfter;
	private final Map<String, String> metadata;

	private VectorStoreModifyRequest(Builder builder) {
		this.name = builder.name;
		this.expiresAfter = builder.expiresAfter;
		this.metadata = builder.metadata;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String name;
		private ExpirationPolicy expiresAfter;
		private Map<String, String> metadata;

		private Builder() {
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

		public VectorStoreModifyRequest build() {
			return new VectorStoreModifyRequest(this);
		}
	}

}
