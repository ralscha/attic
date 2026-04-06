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
package ch.rasc.openai4j.chatcompletions.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest;
import ch.rasc.openai4j.chatcompletions.ChatCompletionMessage;

@SuppressWarnings({ "hiding" })
public class ChatCompletionsJavaFunctionRequest {

	private final List<ChatCompletionMessage> messages;
	private final String model;
	private final Double frequencyPenalty;
	private final Map<String, Double> logitBias;
	private final Integer maxTokens;
	private final Double presencePenalty;
	private final Integer seed;
	private final List<String> stop;
	private final Double temperature;
	private final Double topP;
	private final String user;

	private final List<JavaFunction<?, ?>> javaFunctions;
	private final Integer maxIterations;

	private ChatCompletionsJavaFunctionRequest(Builder builder) {
		if (builder.maxIterations == null) {
			builder.maxIterations = 1;
		}
		else if (builder.maxIterations <= 0) {
			throw new IllegalArgumentException("maxIterations must be greater than 0");
		}
		if (builder.javaFunctions == null || builder.javaFunctions.isEmpty()) {
			throw new IllegalArgumentException("javaFunctions must not be null or empty");
		}
		if (builder.messages == null || builder.messages.isEmpty()) {
			throw new IllegalArgumentException("messages must not be null or empty");
		}
		if (builder.model == null || builder.model.isBlank()) {
			throw new IllegalArgumentException("model must not be null or empty");
		}

		this.messages = builder.messages;
		this.model = builder.model;
		this.frequencyPenalty = builder.frequencyPenalty;
		this.logitBias = builder.logitBias;
		this.maxTokens = builder.maxTokens;
		this.presencePenalty = builder.presencePenalty;
		this.seed = builder.seed;
		this.stop = builder.stop;
		this.temperature = builder.temperature;
		this.topP = builder.topP;
		this.user = builder.user;
		this.javaFunctions = builder.javaFunctions;
		this.maxIterations = builder.maxIterations;
	}

	public ChatCompletionCreateRequest.Builder convertToChatCompletionsCreateRequestBuilder() {
		return ChatCompletionCreateRequest.builder().messages(this.messages)
				.model(this.model).frequencyPenalty(this.frequencyPenalty)
				.logitBias(this.logitBias).maxTokens(this.maxTokens)
				.presencePenalty(this.presencePenalty).seed(this.seed).stop(this.stop)
				.temperature(this.temperature).topP(this.topP).user(this.user);
	}

	public List<ChatCompletionMessage> messages() {
		return List.copyOf(this.messages);
	}

	public Integer maxIterations() {
		return this.maxIterations;
	}

	public List<JavaFunction<?, ?>> javaFunctions() {
		return List.copyOf(this.javaFunctions);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<ChatCompletionMessage> messages;
		private String model;
		private Double frequencyPenalty;
		private Map<String, Double> logitBias;
		private Integer maxTokens;
		private Double presencePenalty;
		private Integer seed;
		private List<String> stop;
		private Double temperature;
		private Double topP;
		private String user;
		private List<JavaFunction<?, ?>> javaFunctions;
		private Integer maxIterations;

		private Builder() {
		}

		/**
		 * A list of messages comprising the conversation so far.
		 */
		public Builder messages(List<ChatCompletionMessage> messages) {
			if (messages != null) {
				this.messages = new ArrayList<>(messages);
			}
			return this;
		}

		/**
		 * Add messages to the conversation so far.
		 */
		public Builder addMessages(ChatCompletionMessage... message) {
			if (this.messages == null) {
				this.messages = new ArrayList<>();
			}
			this.messages.addAll(List.of(message));
			return this;
		}

		/**
		 * ID of the model to use. See the model endpoint compatibility table for details
		 * on which models work with the Chat API.
		 */
		public Builder model(String model) {
			this.model = model;
			return this;
		}

		/**
		 * Number between -2.0 and 2.0. Positive values penalize new tokens based on their
		 * existing frequency in the text so far, decreasing the model's likelihood to
		 * repeat the same line verbatim. Defaults to 0
		 */
		public Builder frequencyPenalty(Double frequencyPenalty) {
			this.frequencyPenalty = frequencyPenalty;
			return this;
		}

		/**
		 * Modify the likelihood of specified tokens appearing in the completion.
		 * <p>
		 * Accepts a JSON object that maps tokens (specified by their token ID in the
		 * tokenizer) to an associated bias value from -100 to 100. Mathematically, the
		 * bias is added to the logits generated by the model prior to sampling. The exact
		 * effect will vary per model, but values between -1 and 1 should decrease or
		 * increase likelihood of selection; values like -100 or 100 should result in a
		 * ban or exclusive selection of the relevant token.
		 */
		public Builder logitBias(Map<String, Double> logitBias) {
			this.logitBias = logitBias;
			return this;
		}

		/**
		 * The maximum number of tokens to generate in the chat completion. The total
		 * length of input tokens and generated tokens is limited by the model's context
		 * length. Example Python code for counting tokens. Defaults to inf
		 */
		public Builder maxTokens(Integer maxTokens) {
			this.maxTokens = maxTokens;
			return this;
		}

		/**
		 * Number between -2.0 and 2.0. Positive values penalize new tokens based on
		 * whether they appear in the text so far, increasing the model's likelihood to
		 * talk about new topics. Defaults to 0
		 */
		public Builder presencePenalty(Double presencePenalty) {
			this.presencePenalty = presencePenalty;
			return this;
		}

		/**
		 * This feature is in Beta. If specified, our system will make a best effort to
		 * sample deterministically, such that repeated requests with the same seed and
		 * parameters should return the same result. Determinism is not guaranteed, and
		 * you should refer to the system_fingerprint response parameter to monitor
		 * changes in the backend.
		 */
		public Builder seed(Integer seed) {
			this.seed = seed;
			return this;
		}

		/**
		 * Up to 4 sequences where the API will stop generating further tokens.
		 */
		public Builder stop(List<String> stop) {
			if (stop != null) {
				this.stop = new ArrayList<>(stop);
			}
			return this;
		}

		/**
		 * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will
		 * make the output more random, while lower values like 0.2 will make it more
		 * focused and deterministic. We generally recommend altering this or top_p but
		 * not both. Defaults to 1
		 */
		public Builder temperature(Double temperature) {
			this.temperature = temperature;
			return this;
		}

		/**
		 * An alternative to sampling with temperature, called nucleus sampling, where the
		 * model considers the results of the tokens with top_p probability mass. So 0.1
		 * means only the tokens comprising the top 10% probability mass are considered.
		 * We generally recommend altering this or temperature but not both. Defaults to 1
		 */
		public Builder topP(Double topP) {
			this.topP = topP;
			return this;
		}

		/**
		 * A unique identifier representing your end-user, which can help OpenAI to
		 * monitor and detect abuse.
		 */
		public Builder user(String user) {
			this.user = user;
			return this;
		}

		/**
		 * The maximum number of iterations to call the java functions. Set it to a
		 * reasonable value to avoid infinite loops. Defaults to 1
		 */
		public Builder maxIterations(Integer maxIterations) {
			this.maxIterations = maxIterations;
			return this;
		}

		/**
		 * A list of java functions that can be called from the chat completion
		 */
		public Builder javaFunctions(List<JavaFunction<?, ?>> javaFunctions) {
			if (javaFunctions != null) {
				this.javaFunctions = new ArrayList<>(javaFunctions);
			}
			return this;
		}

		/**
		 * Add java functions to the list of callable functions
		 */
		public Builder addJavaFunctions(JavaFunction<?, ?>... javaFunction) {
			if (this.javaFunctions == null) {
				this.javaFunctions = new ArrayList<>();
			}
			this.javaFunctions.addAll(List.of(javaFunction));
			return this;
		}

		public ChatCompletionsJavaFunctionRequest build() {
			return new ChatCompletionsJavaFunctionRequest(this);
		}
	}

}
