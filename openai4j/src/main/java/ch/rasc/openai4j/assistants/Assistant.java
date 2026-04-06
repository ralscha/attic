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

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.common.ResponseFormat;

/**
 * Represents an assistant that can call the model and use tools.
 */
public record Assistant(String id, String object,
		@JsonProperty("created_at") int createdAt, String name, String description,
		String model, String instructions, List<Tool> tools,
		@JsonProperty("tool_resources") ToolResources toolResources,
		Map<String, String> metadata, Double temperature,
		@JsonProperty("top_p") Double topP,
		@JsonProperty("response_format") ResponseFormat responseFormat) {

	/**
	 * The identifier, which can be referenced in API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The object type, which is always assistant.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The Unix timestamp (in seconds) for when the assistant was created.
	 */
	@Override
	public int createdAt() {
		return this.createdAt;
	}

	/**
	 * The name of the assistant. The maximum length is 256 characters.
	 */
	@Override
	public String name() {
		return this.name;
	}

	/**
	 * The description of the assistant. The maximum length is 512 characters.
	 */
	@Override
	public String description() {
		return this.description;
	}

	/**
	 * ID of the model to use. You can use the List models API to see all of your
	 * available models
	 */
	@Override
	public String model() {
		return this.model;
	}

	/**
	 * The system instructions that the assistant uses. The maximum length is 256,000
	 * characters.
	 */
	@Override
	public String instructions() {
		return this.instructions;
	}

	/**
	 * A list of tool enabled on the assistant. There can be a maximum of 128 tools per
	 * assistant. Tools can be of types code_interpreter, file_search, or function.
	 */
	@Override
	public List<Tool> tools() {
		return this.tools;
	}

	/**
	 * A set of resources that are used by the assistant's tools. The resources are
	 * specific to the type of tool. For example, the code_interpreter tool requires a
	 * list of file IDs, while the file_search tool requires a list of vector store IDs.
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

	/**
	 * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make
	 * the output more random, while lower values like 0.2 will make it more focused and
	 * deterministic.
	 */
	@Override
	public Double temperature() {
		return this.temperature;
	}

	/**
	 * An alternative to sampling with temperature, called nucleus sampling, where the
	 * model considers the results of the tokens with top_p probability mass. So 0.1 means
	 * only the tokens comprising the top 10% probability mass are considered.
	 * <p>
	 * We generally recommend altering this or temperature but not both.
	 */
	@Override
	public Double topP() {
		return this.topP;
	}

	/**
	 * Specifies the format that the model must output. Compatible with GPT-4 Turbo and
	 * all GPT-3.5 Turbo models since gpt-3.5-turbo-1106.
	 * <p>
	 * Setting to { "type": "json_object" } enables JSON mode, which guarantees the
	 * message the model generates is valid JSON.
	 * <p>
	 * Important: when using JSON mode, you must also instruct the model to produce JSON
	 * yourself via a system or user message. Without this, the model may generate an
	 * unending stream of whitespace until the generation reaches the token limit,
	 * resulting in a long-running and seemingly "stuck" request. Also note that the
	 * message content may be partially cut off if finish_reason="length", which indicates
	 * the generation exceeded max_tokens or the conversation exceeded the max context
	 * length.
	 */
	@Override
	public ResponseFormat responseFormat() {
		return this.responseFormat;
	}

}
