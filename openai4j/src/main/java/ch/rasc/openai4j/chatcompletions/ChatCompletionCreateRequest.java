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
package ch.rasc.openai4j.chatcompletions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import ch.rasc.openai4j.audio.AudioSpeechRequest;
import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest.Builder.Audio;
import ch.rasc.openai4j.common.ResponseFormat;
import ch.rasc.openai4j.common.ServiceTier;
import ch.rasc.openai4j.moderations.ModerationCreateRequest;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class ChatCompletionCreateRequest {

	public enum Modality {
		TEXT("text"), AUDIO("audio");

		private final String value;

		private Modality(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return this.value;
		}
	}

	private final List<ChatCompletionMessage> messages;
	private final String model;
	private final Boolean store;
	private final Map<String, Object> metadata;
	@JsonProperty("frequency_penalty")
	private final Double frequencyPenalty;
	@JsonProperty("logit_bias")
	private final Map<String, Double> logitBias;
	private final Boolean logprobs;
	@JsonProperty("top_logprobs")
	private final Integer topLogprobs;
	@JsonProperty("max_tokens")
	private final Integer maxTokens;
	private final Integer n;

	private final List<Modality> modalities;
	private final Prediction prediction;

	private final Audio audio;

	@JsonProperty("presence_penalty")
	private final Double presencePenalty;
	@JsonProperty("response_format")
	private final ResponseFormat responseFormat;
	private final Integer seed;
	@JsonProperty("service_tier")
	private final ServiceTier serviceTier;
	private final Object stop;
	private final Double temperature;
	@JsonProperty("top_p")
	private final Double topP;
	private final List<ChatCompletionTool> tools;
	@JsonProperty("tool_choice")
	private final ToolChoice toolChoice;
	@JsonProperty("parallel_tool_calls")
	private final Boolean parallelToolCalls;
	private final String user;

	private ChatCompletionCreateRequest(Builder builder) {
		if (builder.messages == null || builder.messages.isEmpty()) {
			throw new IllegalArgumentException("messages must not be null or empty");
		}
		if (builder.model == null || builder.model.isBlank()) {
			throw new IllegalArgumentException("model must not be null or empty");
		}
		if (builder.topLogprobs != null
				&& (builder.topLogprobs < 0 || builder.topLogprobs > 5)) {
			throw new IllegalArgumentException(
					"topLogprobs must be between 0 and 5 (inclusive)");
		}

		this.messages = builder.messages;
		this.model = builder.model;
		this.store = builder.store;
		this.metadata = builder.metadata;
		this.frequencyPenalty = builder.frequencyPenalty;
		this.logitBias = builder.logitBias;
		this.logprobs = builder.logprobs;
		this.topLogprobs = builder.topLogprobs;
		this.maxTokens = builder.maxTokens;
		this.n = builder.n;
		this.modalities = builder.modalities;
		this.prediction = builder.prediction;
		this.audio = builder.audio;
		this.presencePenalty = builder.presencePenalty;
		this.responseFormat = builder.responseFormat;
		this.seed = builder.seed;
		this.serviceTier = builder.serviceTier;
		this.stop = builder.stop;
		this.temperature = builder.temperature;
		this.topP = builder.topP;
		this.tools = builder.tools;
		this.toolChoice = builder.toolChoice;
		this.parallelToolCalls = builder.parallelToolCalls;
		this.user = builder.user;
	}

	public static class ToolChoice {
		private final Object value;

		@JsonCreator
		ToolChoice(Object value) {
			this.value = value;
		}

		/**
		 * none means the model will not call a function and instead generates a message
		 */
		public static ToolChoice none() {
			return new ToolChoice("none");
		}

		/**
		 * auto means the model can pick between generating a message or calling a
		 * function.
		 */
		public static ToolChoice auto() {
			return new ToolChoice("auto");
		}

		/**
		 * required means the model must call one or more tools before responding to the
		 * user.
		 */
		public static ToolChoice required() {
			return new ToolChoice("required");
		}

		/**
		 * Specifying a particular function via {"type: "function", "function": {"name":
		 * "my_function"}} forces the model to call that function.
		 */
		public static ToolChoice function(String functionName) {
			return new ToolChoice(
					Map.of("type", "function", "function", Map.of("name", functionName)));
		}

		@JsonValue
		public Object value() {
			return this.value;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<ChatCompletionMessage> messages;
		private String model;
		private Boolean store;
		private Map<String, Object> metadata;
		private Double frequencyPenalty;
		private Map<String, Double> logitBias;
		private Boolean logprobs;
		private Integer topLogprobs;
		private Integer maxTokens;
		private Integer n;
		private List<Modality> modalities;
		private Prediction prediction;
		private Audio audio;
		private Double presencePenalty;
		private ResponseFormat responseFormat;
		private Integer seed;
		private ServiceTier serviceTier;
		private Object stop;
		private Double temperature;
		private Double topP;
		private List<ChatCompletionTool> tools;
		private ToolChoice toolChoice;
		private Boolean parallelToolCalls;
		private String user;

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
		 * Whether or not to store the output of this chat completion request for use in
		 * the model distillation or evals products.
		 * <p>
		 * Optional. Defaults to false
		 */
		public Builder store(Boolean store) {
			this.store = store;
			return this;
		}

		/**
		 * Developer-defined tags and values used for filtering completions in the
		 * dashboard.
		 */
		public Builder metadata(Map<String, Object> metadata) {
			this.metadata = Map.copyOf(metadata);
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
			if (logitBias != null) {
				this.logitBias = new HashMap<>(logitBias);
			}
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
		public Builder logprobs(Boolean logprobs) {
			this.logprobs = logprobs;
			return this;
		}

		/**
		 * An integer between 0 and 5 specifying the number of most likely tokens to
		 * return at each token position, each with an associated log probability.
		 * logprobs must be set to true if this parameter is used.
		 */
		public Builder topLogprobs(Integer topLogprobs) {
			this.topLogprobs = topLogprobs;
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
		 * How many chat completion choices to generate for each input message. Defaults
		 * to 1
		 */
		public Builder n(Integer n) {
			this.n = n;
			return this;
		}

		/**
		 * Output types that you would like the model to generate for this request. Most
		 * models are capable of generating text, which is the default: ["text"]
		 * <p>
		 * The gpt-4o-audio-preview model can also be used to generate audio. To request
		 * that this model generate both text and audio responses, you can use: ["text",
		 * "audio"]
		 * <p>
		 * Optional.
		 */
		public Builder modalities(Modality... modalities) {
			if (modalities != null && modalities.length > 0) {
				this.modalities = new ArrayList<>(Set.of(modalities));
			}
			return this;
		}

		/**
		 * Configuration for a Predicted Output, which can greatly improve response times
		 * when large parts of the model response are known ahead of time. This is most
		 * common when you are regenerating a file with only minor changes to most of the
		 * content.
		 */
		public Builder prediction(Prediction prediction) {
			this.prediction = prediction;
			return this;
		}

		/**
		 * Configuration for a Predicted Output, which can greatly improve response times
		 * when large parts of the model response are known ahead of time. This is most
		 * common when you are regenerating a file with only minor changes to most of the
		 * content.
		 */
		public Builder prediction(Function<Prediction.Builder, Prediction.Builder> fn) {
			this.prediction = fn.apply(Prediction.builder()).build();
			return this;
		}

		public enum AudioFormat {
			WAV("wav"), MP3("mp3"), FLAC("flac"), OPUS("opus"), PMC16("pcm16");

			private final String value;

			AudioFormat(String value) {
				this.value = value;
			}

			@JsonValue
			public String value() {
				return this.value;
			}
		}

		public enum Voice {
			ALLOY("alloy"), ECHO("echo"), FABLE("fable"), ONYX("onyx"), NOVA("nova"),
			SHIMMER("shimmer");

			private final String value;

			Voice(String value) {
				this.value = value;
			}

			@JsonValue
			public String value() {
				return this.value;
			}
		}

		record Audio(Voice voice, AudioFormat format) {
		}

		/**
		 * Parameters for audio output. Required when audio output is requested with
		 * modalities: ["audio"].
		 * 
		 * @param voice Specifies the voice type.
		 * @param format Specifies the output audio format.
		 */
		public Builder audio(Voice voice, AudioFormat format) {
			if (voice == null) {
				throw new IllegalArgumentException("voice must not be null");
			}
			if (format == null) {
				throw new IllegalArgumentException("format must not be null");
			}
			this.audio = new Audio(voice, format);
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
		 * An object specifying the format that the model must output. Compatible with
		 * GPT-4 Turbo and all GPT-3.5 Turbo models newer than gpt-3.5-turbo-1106.
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
		public Builder serviceTier(ServiceTier serviceTier) {
			this.serviceTier = serviceTier;
			return this;
		}

		/** for copy */
		private Builder stop(Object stop) {
			this.stop = stop;
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
		 * Sequence where the API will stop generating further tokens.
		 */
		public Builder stop(String stop) {
			this.stop = stop;
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
		 * A list of tools the model may call. Currently, only functions are supported as
		 * a tool. Use this to provide a list of functions the model may generate JSON
		 * inputs for.
		 */
		public Builder tools(List<ChatCompletionTool> tools) {
			if (tools != null) {
				this.tools = new ArrayList<>(tools);
			}
			return this;
		}

		/**
		 * Add tools the model may call. Currently, only functions are supported as a
		 * tool. Use this to provide a list of functions the model may generate JSON
		 * inputs for.
		 */
		public Builder addTools(ChatCompletionTool... tool) {
			if (this.tools == null) {
				this.tools = new ArrayList<>();
			}
			this.tools.addAll(List.of(tool));
			return this;
		}

		/**
		 * Controls which (if any) function is called by the model. none means the model
		 * will not call a function and instead generates a message. auto means the model
		 * can pick between generating a message or calling a function. Specifying a
		 * particular function via {"type: "function", "function": {"name":
		 * "my_function"}} forces the model to call that function.
		 * <p>
		 * none is the default when no functions are present. auto is the default if
		 * functions are present.
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
		 * A unique identifier representing your end-user, which can help OpenAI to
		 * monitor and detect abuse.
		 */
		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public ChatCompletionCreateRequest build() {
			return new ChatCompletionCreateRequest(this);
		}
	}

	@JsonProperty
	public List<ChatCompletionMessage> messages() {
		return this.messages;
	}

}
