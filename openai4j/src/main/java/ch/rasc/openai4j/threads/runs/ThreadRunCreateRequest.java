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
package ch.rasc.openai4j.threads.runs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.assistants.Tool;
import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest.ToolChoice;
import ch.rasc.openai4j.common.ResponseFormat;
import ch.rasc.openai4j.threads.ThreadMessageRequest;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class ThreadRunCreateRequest {

	@JsonProperty("assistant_id")
	private final String assistantId;
	private final String model;
	private final String instructions;
	@JsonProperty("additional_instructions")
	private final String additionalInstructions;
	@JsonProperty("additional_messages")
	private final List<ThreadMessageRequest> additionalMessages;
	private final List<Tool> tools;
	private final Map<String, String> metadata;
	private final Double temperature;
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

	private ThreadRunCreateRequest(Builder builder) {
		if (builder.assistantId == null) {
			throw new IllegalArgumentException("assistantId cannot be null");
		}
		this.assistantId = builder.assistantId;
		this.model = builder.model;
		this.instructions = builder.instructions;
		this.additionalInstructions = builder.additionalInstructions;
		this.additionalMessages = builder.additionalMessages;
		this.tools = builder.tools;
		this.metadata = builder.metadata;
		this.temperature = builder.temperature;
		this.maxPromptTokens = builder.maxPromptTokens;
		this.maxCompletionTokens = builder.maxCompletionTokens;
		this.truncationStrategy = builder.truncationStrategy;
		this.toolChoice = builder.toolChoice;
		this.parallelToolCalls = builder.parallelToolCalls;
		this.responseFormat = builder.responseFormat;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String assistantId;
		private String model;
		private String instructions;
		private String additionalInstructions;
		private List<ThreadMessageRequest> additionalMessages;
		private List<Tool> tools;
		private Map<String, String> metadata;
		private Double temperature;
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
		 * Appends additional instructions at the end of the instructions for the run.
		 * This is useful for modifying the behavior on a per-run basis without overriding
		 * other instructions.
		 */
		public Builder additionalInstructions(String additionalInstructions) {
			this.additionalInstructions = additionalInstructions;
			return this;
		}

		/**
		 * Adds additional messages to the thread before creating the run.
		 */
		public Builder additionalMessages(List<ThreadMessageRequest> additionalMessages) {
			this.additionalMessages = new ArrayList<>(additionalMessages);
			return this;
		}

		/**
		 * Adds additional messages to the thread before creating the run.
		 */
		public Builder addAdditionalMessages(ThreadMessageRequest... additionalMessages) {
			if (this.additionalMessages == null) {
				this.additionalMessages = new ArrayList<>();
			}
			this.additionalMessages.addAll(List.of(additionalMessages));
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
		 * Override the tools the assistant can use for this run. This is useful for
		 * modifying the behavior on a per-run basis.
		 */
		public Builder addTools(Tool... tools) {
			if (this.tools == null) {
				this.tools = new ArrayList<>();
			}
			this.tools.addAll(List.of(tools));
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

		public ThreadRunCreateRequest build() {
			return new ThreadRunCreateRequest(this);
		}

		/**
		 * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will
		 * make the output more random, while lower values like 0.2 will make it more
		 * focused and deterministic. Optional. Defaults to 1
		 */
		public Builder temperature(Double temperature) {
			this.temperature = temperature;
			return this;
		}

		/**
		 * The maximum number of prompt tokens that may be used over the course of the
		 * run. The run will make a best effort to use only the number of prompt tokens
		 * specified, across multiple turns of the run. If the run exceeds the number of
		 * prompt tokens specified, the run will end with status complete.
		 */
		public Builder maxPromptTokens(Integer maxPromptTokens) {
			this.maxPromptTokens = maxPromptTokens;
			return this;
		}

		/**
		 * The maximum number of completion tokens that may be used over the course of the
		 * run. The run will make a best effort to use only the number of completion
		 * tokens specified, across multiple turns of the run. If the run exceeds the
		 * number of completion tokens specified, the run will end with status complete.
		 * See incomplete_details for more info.
		 */
		public Builder maxCompletionTokens(Integer maxCompletionTokens) {
			this.maxCompletionTokens = maxCompletionTokens;
			return this;
		}

		/**
		 * The truncation strategy to use for the thread. The default is
		 * <code>auto</code>. If set to <code>last_messages</code>, the thread will be
		 * truncated to the n most recent messages in the thread. When set to
		 * <code>auto</code>, messages in the middle of the thread will be dropped to fit
		 * the context length of the model, max_prompt_tokens.
		 */
		public Builder truncationStrategy(TruncationStrategy truncationStrategy) {
			this.truncationStrategy = truncationStrategy;
			return this;
		}

		/**
		 * Controls which (if any) tool is called by the model. none means the model will
		 * not call any tools and instead generates a message. <code>auto</code> is the
		 * default value and means the model can pick between generating a message or
		 * calling a tool. Specifying a particular tool like {"type": "TOOL_TYPE"} or
		 * {"type": "function", "function": {"name": "my_function"}} forces the model to
		 * call that tool.
		 * <p>
		 * <code>none</code> means the model will not call a function and instead
		 * generates a message. <code>auto</code> means the model can pick between
		 * generating a message or calling a function.
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
		 * and all GPT-3.5 Turbo models newer than gpt-3.5-turbo-1106.
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

	}

	public record TruncationStrategy(String type,
			@JsonProperty("last_messages") Integer lastMessages) {

		/**
		 * Messages in the middle of the thread will be dropped to fit the context length
		 * of the model, max_prompt_tokens.
		 */
		public static TruncationStrategy auto() {
			return new TruncationStrategy("auto", null);
		}

		/**
		 * The thread will be truncated to the <code>lastMessages</code> most recent
		 * messages in the thread
		 */
		public static TruncationStrategy lastMessages(int lastMessages) {
			return new TruncationStrategy("last_messages", lastMessages);
		}

	}
}
