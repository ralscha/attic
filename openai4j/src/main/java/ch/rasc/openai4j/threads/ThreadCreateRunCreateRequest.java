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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.assistants.Tool;
import ch.rasc.openai4j.assistants.ToolResources;
import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest.ToolChoice;
import ch.rasc.openai4j.common.ResponseFormat;
import ch.rasc.openai4j.threads.runs.ThreadRunCreateRequest.TruncationStrategy;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class ThreadCreateRunCreateRequest {

	@JsonProperty("assistant_id")
	private final String assistantId;
	private final ThreadCreateRunCreateRequestThread thread;
	private final String model;
	private final String instructions;
	private final List<Tool> tools;
	@JsonProperty("tool_resources")
	private ToolResources toolResources;
	private final Map<String, String> metadata;
	private final Double temperature;
	@JsonProperty("top_p")
	private final Double topP;
	@JsonProperty("max_prompt_tokens")
	private final Integer maxPromptTokens;
	@JsonProperty("max_completion_tokens")
	private final Integer maxCompletionTokens;
	@JsonProperty("truncation_strategy")
	private final TruncationStrategy truncationStrategy;
	@JsonProperty("tool_choice")
	private final ToolChoice toolChoice;
	@JsonProperty("parallel_tool_calls")
	private final Boolean parallelToolCalls;
	@JsonProperty("response_format")
	private final ResponseFormat responseFormat;

	private ThreadCreateRunCreateRequest(Builder builder) {
		if (builder.assistantId == null) {
			throw new IllegalArgumentException("assistantId cannot be null");
		}
		this.assistantId = builder.assistantId;
		this.thread = builder.thread;
		this.model = builder.model;
		this.instructions = builder.instructions;
		this.tools = builder.tools;
		this.toolResources = builder.toolResources;
		this.metadata = builder.metadata;
		this.temperature = builder.temperature;
		this.topP = builder.topP;
		this.maxPromptTokens = builder.maxPromptTokens;
		this.maxCompletionTokens = builder.maxCompletionTokens;
		this.truncationStrategy = builder.truncationStrategy;
		this.toolChoice = builder.toolChoice;
		this.parallelToolCalls = builder.parallelToolCalls;
		this.responseFormat = builder.responseFormat;
	}

	private record ThreadCreateRunCreateRequestThread(List<ThreadMessageRequest> messages,
			Map<String, String> metadata) {

	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String assistantId;
		private ThreadCreateRunCreateRequestThread thread;
		private String model;
		private String instructions;
		private List<Tool> tools;
		private ToolResources toolResources;
		private Map<String, String> metadata;
		private Double temperature;
		private Double topP;
		private Integer maxPromptTokens;
		private Integer maxCompletionTokens;
		private TruncationStrategy truncationStrategy;
		private ToolChoice toolChoice;
		private Boolean parallelToolCalls;
		private ResponseFormat responseFormat;

		private Builder() {
		}

		/**
		 * The ID of the assistant to use to execute this run.
		 */
		public Builder assistantId(String assistantId) {
			this.assistantId = assistantId;
			return this;
		}

		/**
		 * A message thread to start with
		 */
		public Builder thread(List<ThreadMessageRequest> messages) {
			this.thread = new ThreadCreateRunCreateRequestThread(List.copyOf(messages),
					null);
			return this;
		}

		/**
		 * A message thread to start with
		 */
		public Builder thread(
				Function<ThreadMessageRequest.Builder, ThreadMessageRequest.Builder> fn) {
			this.thread = new ThreadCreateRunCreateRequestThread(
					List.of(fn.apply(ThreadMessageRequest.builder()).build()), null);
			return this;
		}

		/**
		 * A message thread to start with
		 */
		public Builder thread(List<ThreadMessageRequest> messages,
				Map<String, String> metadata) {
			this.thread = new ThreadCreateRunCreateRequestThread(List.copyOf(messages),
					Map.copyOf(metadata));
			return this;
		}

		/**
		 * The ID of the Model to be used to execute this run. If a value is provided
		 * here, it will override the model associated with the assistant. If not, the
		 * model associated with the assistant will be used.
		 */
		public Builder model(String model) {
			this.model = model;
			return this;
		}

		/**
		 * Override the default system message of the assistant. This is useful for
		 * modifying the behavior on a per-run basis.
		 */
		public Builder instructions(String instructions) {
			this.instructions = instructions;
			return this;
		}

		/**
		 * Override the tools the assistant can use for this run. This is useful for
		 * modifying the behavior on a per-run basis.
		 */
		public Builder tools(List<Tool> tools) {
			this.tools = new ArrayList<>(tools);
			return this;
		}

		/**
		 * Add tools the assistant can use for this run. This is useful for modifying the
		 * behavior on a per-run basis.
		 */
		public Builder addTools(Tool... tools) {
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
		 * Defaults to 1.0.
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
		 * Defaults to 1.0.
		 */
		public Builder topP(Double topP) {
			this.topP = topP;
			return this;
		}

		/**
		 * The maximum number of prompt tokens that may be used over the course of the
		 * run. The run will make a best effort to use only the number of prompt tokens
		 * specified, across multiple turns of the run. If the run exceeds the number of
		 * prompt tokens specified, the run will end with status incomplete. See
		 * incomplete_details for more info.
		 */
		public Builder maxPromptTokens(Integer maxPromptTokens) {
			this.maxPromptTokens = maxPromptTokens;
			return this;
		}

		/**
		 * The maximum number of completion tokens that may be used over the course of the
		 * run. The run will make a best effort to use only the number of completion
		 * tokens specified, across multiple turns of the run. If the run exceeds the
		 * number of completion tokens specified, the run will end with status incomplete.
		 * See incomplete_details for more info.
		 */
		public Builder maxCompletionTokens(Integer maxCompletionTokens) {
			this.maxCompletionTokens = maxCompletionTokens;
			return this;
		}

		/**
		 * Controls for how a thread will be truncated prior to the run. Use this to
		 * control the intial context window of the run.
		 */
		public Builder truncationStrategy(TruncationStrategy truncationStrategy) {
			this.truncationStrategy = truncationStrategy;
			return this;
		}

		/**
		 * Controls which (if any) tool is called by the model. none means the model will
		 * not call any tools and instead generates a message. auto is the default value
		 * and means the model can pick between generating a message or calling one or
		 * more tools. required means the model must call one or more tools before
		 * responding to the user. Specifying a particular tool like {"type":
		 * "file_search"} or {"type": "function", "function": {"name": "my_function"}}
		 * forces the model to call that tool.
		 */
		public Builder toolChoice(ToolChoice toolChoice) {
			this.toolChoice = toolChoice;
			return this;
		}

		/**
		 * Whether to enable parallel function calling during tool use.
		 * <p>
		 * Defaults to true
		 */
		public Builder parallelToolCalls(Boolean parallelToolCalls) {
			this.parallelToolCalls = parallelToolCalls;
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

		public ThreadCreateRunCreateRequest build() {
			return new ThreadCreateRunCreateRequest(this);
		}
	}
}
