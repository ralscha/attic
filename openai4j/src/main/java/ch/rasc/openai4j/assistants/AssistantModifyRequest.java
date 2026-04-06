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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.common.ResponseFormat;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class AssistantModifyRequest {

	private final String model;
	private final String name;
	private final String description;
	private final String instructions;
	private final List<Tool> tools;
	@JsonProperty("tool_resources")
	private final ToolResources toolResources;
	private final Map<String, String> metadata;
	private final Double temperature;
	@JsonProperty("top_p")
	private final Double topP;
	@JsonProperty("response_format")
	private final ResponseFormat responseFormat;

	private AssistantModifyRequest(Builder builder) {
		this.model = builder.model;
		this.name = builder.name;
		this.description = builder.description;
		this.instructions = builder.instructions;
		this.tools = builder.tools;
		this.toolResources = builder.toolResources;
		this.metadata = builder.metadata;
		this.temperature = builder.temperature;
		this.topP = builder.topP;
		this.responseFormat = builder.responseFormat;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String model;
		private String name;
		private String description;
		private String instructions;
		private List<Tool> tools;
		private ToolResources toolResources;
		private Map<String, String> metadata;
		private Double temperature;
		private Double topP;
		private ResponseFormat responseFormat;

		private Builder() {
		}

		/**
		 * ID of the model to use. You can use the List models API to see all of your
		 * available models.
		 */
		public Builder model(String model) {
			this.model = model;
			return this;
		}

		/**
		 * The name of the assistant. The maximum length is 256 characters.
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/**
		 * The description of the assistant. The maximum length is 512 characters.
		 */
		public Builder description(String description) {
			this.description = description;
			return this;
		}

		/**
		 * The system instructions that the assistant uses. The maximum length is 256,000
		 * characters.
		 */
		public Builder instructions(String instructions) {
			this.instructions = instructions;
			return this;
		}

		/**
		 * A list of tool enabled on the assistant. There can be a maximum of 128 tools
		 * per assistant. Tools can be of types code_interpreter, file_search, or
		 * function.
		 */
		public Builder tools(List<Tool> tools) {
			this.tools = new ArrayList<>(tools);
			return this;
		}

		/**
		 * Add a tool to the list of tools enabled on the assistant
		 */
		public Builder addTools(Tool... tools) {
			if (tools == null || tools.length == 0) {
				return this;
			}
			if (this.tools == null) {
				this.tools = new ArrayList<>();
			}
			this.tools.addAll(List.of(tools));
			return this;
		}

		/**
		 * A set of resources that are used by the assistant's tools. The resources are
		 * specific to the type of tool. For example, the code_interpreter tool requires a
		 * list of file IDs, while the file_search tool requires a list of vector store
		 * IDs.
		 */
		public Builder toolResources(ToolResources toolResources) {
			this.toolResources = toolResources;
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

		/**
		 * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will
		 * make the output more random, while lower values like 0.2 will make it more
		 * focused and deterministic.
		 * <p>
		 * Defaults to 1
		 */
		public Builder temperature(Double temperature) {
			this.temperature = temperature;
			return this;
		}

		/**
		 * An alternative to sampling with temperature, called nucleus sampling, where the
		 * model considers the results of the tokens with top_p probability mass. So 0.1
		 * means only the tokens comprising the top 10% probability mass are considered.
		 * <p>
		 * We generally recommend altering this or temperature but not both.
		 * <p>
		 * Defaults to 1
		 */
		public Builder topP(Double topP) {
			this.topP = topP;
			return this;
		}

		/**
		 * Specifies the format that the model must output. Compatible with GPT-4 Turbo
		 * and all GPT-3.5 Turbo models since gpt-3.5-turbo-1106.
		 * <p>
		 * Setting to { "type": "json_object" } enables JSON mode, which guarantees the
		 * message the model generates is valid JSON.
		 * <p>
		 * Important: when using JSON mode, you must also instruct the model to produce
		 * JSON yourself via a system or user message. Without this, the model may
		 * generate an unending stream of whitespace until the generation reaches the
		 * token limit, resulting in a long-running and seemingly "stuck" request. Also
		 * note that the message content may be partially cut off if
		 * finish_reason="length", which indicates the generation exceeded max_tokens or
		 * the conversation exceeded the max context length.
		 */
		public Builder responseFormat(ResponseFormat responseFormat) {
			this.responseFormat = responseFormat;
			return this;
		}

		public AssistantModifyRequest build() {
			return new AssistantModifyRequest(this);
		}
	}
}
