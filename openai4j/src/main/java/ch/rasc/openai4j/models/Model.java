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
package ch.rasc.openai4j.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes an OpenAI model offering that can be used with the API.
 */
public record Model(String id, int created, String object,
		@JsonProperty("owned_by") String ownedBy) {

	/**
	 * The model identifier, which can be referenced in the API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The Unix timestamp (in seconds) when the model was created.
	 */
	@Override
	public int created() {
		return this.created;
	}

	/**
	 * The object type, which is always "model".
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The organization that owns the model.
	 */
	@Override
	public String ownedBy() {
		return this.ownedBy;
	}

}
