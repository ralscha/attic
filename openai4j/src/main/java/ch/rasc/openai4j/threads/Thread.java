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
package ch.rasc.openai4j.threads;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.assistants.ToolResources;

/**
 * Represents a thread that contains messages.
 */
public record Thread(String id, String object, @JsonProperty("created_at") int createdAt,
		@JsonProperty("tool_resources") ToolResources toolResources,
		Map<String, String> metadata) {

	/**
	 * The identifier, which can be referenced in API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The object type, which is always thread.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The Unix timestamp (in seconds) for when the thread was created.
	 */
	@Override
	public int createdAt() {
		return this.createdAt;
	}

	/**
	 * A set of resources that are made available to the assistant's tools in this thread.
	 * The resources are specific to the type of tool. For example, the code_interpreter
	 * tool requires a list of file IDs, while the file_search tool requires a list of
	 * vector store IDs.
	 */
	@Override
	public ToolResources toolResources() {
		return this.toolResources;
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
}
