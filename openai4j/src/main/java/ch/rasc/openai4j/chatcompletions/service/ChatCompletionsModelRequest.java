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
import ch.rasc.openai4j.common.ServiceTier;

@SuppressWarnings({ "hiding" })
public class ChatCompletionsModelRequest<T> {

	public enum Mode {
		TOOL, JSON_OBJECT, JSON_SCHEMA
	}

	private final List<ChatCompletionMessage> messages;
	private final String model;
	private final Double frequencyPenalty;
	private final Map<String, Double> logitBias;
	private final Boolean logprobs;
	private final Integer topLogprobs;
	private final Integer maxTokens;
	private final Double presencePenalty;
	private final Integer seed;
	private final ServiceTier serviceTier;
	private final List<String> stop;
	private final Double temperature;
	private final Double topP;
	private final String user;

	private final Class<T> responseModel;
	private final Mode mode;
	private final Integer maxRetries;

	private ChatCompletionsModelRequest(Builder<T> builder) {
		if (builder.maxRetries == null) {
			builder.maxRetries = 1;
		}
		else if (builder.maxRetries <= 0) {
			throw new IllegalArgumentException("maxRetries must be greater than 0");
		}
		if (builder.mode == null) {
			builder.mode = Mode.JSON_SCHEMA;
		}
		if (builder.responseModel == null) {
			throw new IllegalArgumentException("responseModel must not be null");
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
		this.logprobs = builder.logprobs;
		this.topLogprobs = builder.topLogprobs;
		this.maxTokens = builder.maxTokens;
		this.presencePenalty = builder.presencePenalty;
		this.seed = builder.seed;
		this.serviceTier = builder.serviceTier;
		this.stop = builder.stop;
		this.temperature = builder.temperature;
		this.topP = builder.topP;
		this.user = builder.user;
		this.responseModel = builder.responseModel;
		this.mode = builder.mode;
		this.maxRetries = builder.maxRetries;
	}

	public ChatCompletionCreateRequest.Builder convertToChatCompletionsCreateRequestBuilder() {
		return ChatCompletionCreateRequest.builder().messages(this.messages)
				.model(this.model).frequencyPenalty(this.frequencyPenalty)
				.logitBias(this.logitBias).logprobs(this.logprobs)
				.topLogprobs(this.topLogprobs).maxTokens(this.maxTokens)
				.presencePenalty(this.presencePenalty).seed(this.seed)
				.serviceTier(this.serviceTier).stop(this.stop)
				.temperature(this.temperature).topP(this.topP).user(this.user);
	}

	public List<ChatCompletionMessage> messages() {
		return List.copyOf(this.messages);
	}

	public Class<T> responseModel() {
		return this.responseModel;
	}

	public Mode mode() {
		return this.mode;
	}

	public Integer maxRetries() {
		return this.maxRetries;
	}

	public static <T> Builder<T> builder() {
		return new Builder<>();
	}

	public static final class Builder<T> {
		private List<ChatCompletionMessage> messages;
		private String model;
		private Double frequencyPenalty;
		private Map<String, Double> logitBias;
		private Boolean logprobs;
		private Integer topLogprobs;
		private Integer maxTokens;
		private Double presencePenalty;
		private Integer seed;
		private ServiceTier serviceTier;
		private List<String> stop;
		private Double temperature;
		private Double topP;
		private String user;
		private Class<T> responseModel;
		private Mode mode;
		private Integer maxRetries;

		private Builder() {
		}

		/**
		 * A list of messages comprising the conversation so far.
		 */
		public Builder<T> messages(List<ChatCompletionMessage> messages) {
			if (messages != null) {
				this.messages = new ArrayList<>(messages);
			}
			return this;
		}

		/**
		 * Add messages to the conversation so far.
		 */
		public Builder<T> addMessages(ChatCompletionMessage... message) {
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
		public Builder<T> model(String model) {
			this.model = model;
			return this;
		}

		/**
		 * Number between -2.0 and 2.0. Positive values penalize new tokens based on their
		 * existing frequency in the text so far, decreasing the model's likelihood to
		 * repeat the same line verbatim. Defaults to 0
		 */
		public Builder<T> frequencyPenalty(Double frequencyPenalty) {
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
		public Builder<T> logitBias(Map<String, Double> logitBias) {
			this.logitBias = logitBias;
			return this;
		}

		/**
		 * Whether to return log probabilities of the output tokens or not. If true,
		 * returns the log probabilities of each output token returned in the content of
		 * message. This option is currently not available on the gpt-4-vision-preview
		 * model.
		 * <p>
		 * Defaults to false
		 */
		public Builder<T> logprobs(Boolean logprobs) {
			this.logprobs = logprobs;
			return this;
		}

		/**
		 * An integer between 0 and 5 specifying the number of most likely tokens to
		 * return at each token position, each with an associated log probability.
		 * logprobs must be set to true if this parameter is used.
		 */
		public Builder<T> topLogprobs(Integer topLogprobs) {
			this.topLogprobs = topLogprobs;
			return this;
		}

		/**
		 * The maximum number of tokens to generate in the chat completion. The total
		 * length of input tokens and generated tokens is limited by the model's context
		 * length. Example Python code for counting tokens. Defaults to inf
		 */
		public Builder<T> maxTokens(Integer maxTokens) {
			this.maxTokens = maxTokens;
			return this;
		}

		/**
		 * Number between -2.0 and 2.0. Positive values penalize new tokens based on
		 * whether they appear in the text so far, increasing the model's likelihood to
		 * talk about new topics. Defaults to 0
		 */
		public Builder<T> presencePenalty(Double presencePenalty) {
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
		public Builder<T> seed(Integer seed) {
			this.seed = seed;
			return this;
		}

		/**
		 * Specifies the latency tier to use for processing the request. This parameter is
		 * relevant for customers subscribed to the scale tier service:
		 * <p>
		 * If set to 'auto', the system will utilize scale tier credits until they are
		 * exhausted.
		 * <p>
		 * If set to 'default', the request will be processed using the default service
		 * tier with a lower uptime SLA and no latency guarentee.
		 * <p>
		 * When not set, the default behavior is 'auto'.
		 * <p>
		 * When this parameter is set, the response body will include the service_tier
		 * utilized.
		 */
		public Builder<T> serviceTier(ServiceTier serviceTier) {
			this.serviceTier = serviceTier;
			return this;
		}

		/**
		 * Up to 4 sequences where the API will stop generating further tokens.
		 */
		public Builder<T> stop(List<String> stop) {
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
		public Builder<T> temperature(Double temperature) {
			this.temperature = temperature;
			return this;
		}

		/**
		 * An alternative to sampling with temperature, called nucleus sampling, where the
		 * model considers the results of the tokens with top_p probability mass. So 0.1
		 * means only the tokens comprising the top 10% probability mass are considered.
		 * We generally recommend altering this or temperature but not both. Defaults to 1
		 */
		public Builder<T> topP(Double topP) {
			this.topP = topP;
			return this;
		}

		/**
		 * A unique identifier representing your end-user, which can help OpenAI to
		 * monitor and detect abuse.
		 */
		public Builder<T> user(String user) {
			this.user = user;
			return this;
		}

		/**
		 * The class of the response model. The response will be deserialized to this
		 */
		public Builder<T> responseModel(Class<T> responseModel) {
			this.responseModel = responseModel;
			return this;
		}

		/**
		 * Either use the system message for providing the JSON schema (JSON) or use a
		 * tool call to generate the JSON schema (TOOL)
		 */
		public Builder<T> mode(Mode mode) {
			this.mode = mode;
			return this;
		}

		/**
		 * The maximum number of retries if there are deserialization or validation errors
		 * Defaults to 1
		 */
		public Builder<T> maxRetries(int maxRetries) {
			this.maxRetries = maxRetries;
			return this;
		}

		public ChatCompletionsModelRequest<T> build() {
			return new ChatCompletionsModelRequest<>(this);
		}
	}
}
