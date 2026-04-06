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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ch.rasc.openai4j.chatcompletions.AssistantMessage;
import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest.ToolChoice;
import ch.rasc.openai4j.chatcompletions.ChatCompletionMessage;
import ch.rasc.openai4j.chatcompletions.ChatCompletionResponse;
import ch.rasc.openai4j.chatcompletions.ChatCompletionResponse.Choice.FinishReason;
import ch.rasc.openai4j.chatcompletions.ChatCompletionTool;
import ch.rasc.openai4j.chatcompletions.ChatCompletionsClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.ToolMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.chatcompletions.service.ChatCompletionsModelRequest.Mode;
import ch.rasc.openai4j.common.FunctionParameters;
import ch.rasc.openai4j.common.JsonSchemaService;
import ch.rasc.openai4j.common.ResponseFormat;
import ch.rasc.openai4j.common.ToolCall;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * High level chat completions client that supports calling Java methods
 */
public class ChatCompletionsService {
	private final static Logger log = LoggerFactory
			.getLogger(ChatCompletionsService.class);
	private final JsonSchemaService jsonSchemaService;

	private final ChatCompletionsClient chatCompletionsClient;

	private final ObjectMapper objectMapper;

	private final Validator validator;

	public ChatCompletionsService(ChatCompletionsClient chatCompletionsClient,
			ObjectMapper objectMapper) {
		this.jsonSchemaService = new JsonSchemaService();
		this.chatCompletionsClient = chatCompletionsClient;
		this.objectMapper = objectMapper;

		try (ValidatorFactory validatorFactory = Validation.byDefaultProvider()
				.configure().messageInterpolator(new ParameterMessageInterpolator())
				.buildValidatorFactory()) {
			this.validator = validatorFactory.getValidator();
		}
	}

	public ChatCompletionsService(ChatCompletionsClient chatCompletionsClient) {
		this(chatCompletionsClient, new ObjectMapper());
	}

	/**
	 * Creates and calls a chat completion for the provided prompt and parameters. When
	 * the response of the completion contains a tool call, the tool call is executed and
	 * the completion is called again with the updated prompt.
	 * <p>
	 * The method will repeat this process until the completion is finished or the maximum
	 * number of iterations is reached.
	 *
	 * @param fn A chat completion request builder function
	 * @return A chat completion response
	 * @throws JsonProcessingException If the java function arguments or results cannot be
	 * serialized or deserialized
	 */
	public ChatCompletionResponse createJavaFunctions(
			Function<ChatCompletionsJavaFunctionRequest.Builder, ChatCompletionsJavaFunctionRequest.Builder> fn)
			throws JsonProcessingException {

		var javaFunctionsRequest = fn.apply(ChatCompletionsJavaFunctionRequest.builder())
				.build();
		Map<String, JavaFunction<?, ?>> javaFunctionRegistry = new HashMap<>();
		List<ChatCompletionTool> tools = new ArrayList<>();
		for (JavaFunction<?, ?> javaFunction : javaFunctionsRequest.javaFunctions()) {
			javaFunctionRegistry.put(javaFunction.name(), javaFunction);
			tools.add(javaFunction.toTool(this.jsonSchemaService));
		}

		var requestBuilder = javaFunctionsRequest
				.convertToChatCompletionsCreateRequestBuilder();
		var request = requestBuilder.tools(tools).build();
		ChatCompletionResponse response = this.chatCompletionsClient.create(request);

		var thread = new ArrayList<>(request.messages());
		var choice = response.choices().get(0);

		int iterationCount = 1;
		while (choice.finishReason() == FinishReason.TOOL_CALLS) {
			if (iterationCount > javaFunctionsRequest.maxIterations()) {
				log.debug("Max iterations reached");
				return response;
			}
			log.debug("Iteration {}", iterationCount);

			var message = choice.message();
			thread.add(AssistantMessage.of(choice.message()));

			for (var toolCall : message.toolCalls()) {
				JavaFunction<?, ?> javaFunction = javaFunctionRegistry
						.get(toolCall.function().name());
				if (javaFunction == null) {
					throw new IllegalStateException(
							"Unknown function " + toolCall.function().name());
				}
				var argument = this.objectMapper.readValue(
						toolCall.function().arguments(), javaFunction.parameterClass());

				log.debug("Calling function {}", javaFunction.name());
				log.debug("with argument {}", argument);

				Object result = javaFunction.call(argument);

				if (result != null) {
					String resultJson = this.objectMapper.writeValueAsString(result);
					thread.add(ToolMessage.of(toolCall.id(), resultJson));
				}
				else {
					thread.add(ToolMessage.of(toolCall.id(), null));
				}
			}

			request = requestBuilder.messages(thread).build();
			response = this.chatCompletionsClient.create(request);

			iterationCount += 1;
		}

		return response;
	}

	public record ChatCompletionsModelResponse<T>(ChatCompletionResponse response,
			T responseModel, String error) {

		/**
		 * The original response from the chat completions API
		 */
		@Override
		public ChatCompletionResponse response() {
			return this.response;
		}

		/**
		 * The response model that was created from the response
		 */
		@Override
		public T responseModel() {
			return this.responseModel;
		}

		/**
		 * An error message if the response model could not be created
		 */
		@Override
		public String error() {
			return this.error;
		}
	}

	/**
	 * Creates and calls the chat completions API for the provided prompt and parameters.
	 * The response is converted to the provided response model. The method will repeat
	 * this process when the response can't be converted to the response model or there
	 * are validation errors in the response model.
	 * <p>
	 * The method will repeat this process until the completion is finished or the maximum
	 * number of retries is reached.
	 * @param fn A chat completion request model builder function
	 * @return A chat completion response
	 * @param <T> The response model type
	 */
	public <T> ChatCompletionsModelResponse<T> createModel(
			Function<ChatCompletionsModelRequest.Builder<T>, ChatCompletionsModelRequest.Builder<T>> fn) {

		ChatCompletionsModelRequest<T> request = fn
				.apply(ChatCompletionsModelRequest.builder()).build();

		List<ChatCompletionMessage> thread;
		var requestBuilder = request.convertToChatCompletionsCreateRequestBuilder();
		ObjectNode jsonSchema = this.jsonSchemaService
				.generateStrictSchema(request.responseModel());
		String functionName = request.responseModel().getSimpleName();

		if (request.mode() == Mode.JSON_OBJECT) {
			requestBuilder.responseFormat(ResponseFormat.jsonObject());

			String jsonSchemaSystemMessage = "Make sure that your response to any message matches the json_schema below, "
					+ "do not deviate at all: \n" + jsonSchema;

			List<ChatCompletionMessage> originalMessages = request.messages();
			thread = new ArrayList<>();

			if (!originalMessages.isEmpty() && originalMessages
					.get(0) instanceof SystemMessage firstSystemMessage) {
				SystemMessage newSystemMessage = SystemMessage.of(
						firstSystemMessage.content() + "\n\n" + jsonSchemaSystemMessage);
				thread.add(newSystemMessage);
				thread.addAll(originalMessages.subList(1, originalMessages.size()));

				log.debug("Replacing system message: {}", newSystemMessage.content());
			}
			else {
				thread.add(SystemMessage.of(jsonSchemaSystemMessage));
				thread.addAll(originalMessages);
				log.debug("Adding system message: {}", jsonSchemaSystemMessage);
			}
		}
		else if (request.mode() == Mode.JSON_SCHEMA) {
			ResponseFormat responseFormat = this.jsonSchemaService
					.createStrictResponseFormat(request.responseModel());
			requestBuilder.responseFormat(responseFormat);
			log.debug("Using json schema response format: {}", responseFormat.value());
			thread = new ArrayList<>(request.messages());
		}
		else {
			thread = new ArrayList<>(request.messages());
			JsonNode descriptionNode = jsonSchema.get("description");
			String description = null;
			if (descriptionNode != null) {
				description = descriptionNode.textValue();
			}
			List<ChatCompletionTool> tool = List.of(ChatCompletionTool.of(
					FunctionParameters.of(functionName, description, jsonSchema, true)));
			requestBuilder.tools(tool);
			requestBuilder.toolChoice(ToolChoice.function(functionName));

			log.debug("Adding tool: {}", tool);
		}

		int retryCount = 0;

		ChatCompletionResponse response = null;
		while (retryCount < request.maxRetries()) {
			log.debug("Retry {}", retryCount);

			response = this.chatCompletionsClient
					.create(requestBuilder.messages(thread).build());

			var choice = response.choices().get(0);
			if (choice.finishReason() != FinishReason.STOP) {
				return new ChatCompletionsModelResponse<>(response, null,
						"finish reason not STOP");
			}

			try {
				T responseModelInstance = null;
				String refusal = choice.message().refusal();
				if (request.mode() == Mode.JSON_OBJECT) {
					responseModelInstance = this.objectMapper.readValue(
							choice.message().content(), request.responseModel());
				}
				else if (request.mode() == Mode.JSON_SCHEMA) {
					if (refusal == null || refusal.isBlank()) {
						responseModelInstance = this.objectMapper.readValue(
								choice.message().content(), request.responseModel());
					}
					else {
						log.debug("json schema refusal: {}", refusal);
						return new ChatCompletionsModelResponse<>(response, null,
								"json schema refusal");
					}
				}
				else if (request.mode() == Mode.TOOL) {
					ToolCall firstToolCall = choice.message().toolCalls().get(0);
					if (firstToolCall.function().name().equals(functionName)) {
						responseModelInstance = this.objectMapper.readValue(
								firstToolCall.function().arguments(),
								request.responseModel());
					}
					else {
						String errorMessage = "Recall the correct function, function "
								+ firstToolCall.function().name() + " does not exist";
						thread.add(AssistantMessage.of(choice.message()));
						thread.add(UserMessage.of(errorMessage));
					}
				}

				if (responseModelInstance != null) {
					Set<ConstraintViolation<T>> constraintViolations = this.validator
							.validate(responseModelInstance);
					if (constraintViolations.isEmpty()) {
						return new ChatCompletionsModelResponse<>(response,
								responseModelInstance, null);
					}

					StringBuilder validationErrors;
					if (request.mode() == Mode.JSON_OBJECT) {
						validationErrors = new StringBuilder("Validation errors found\n");
					}
					else {
						validationErrors = new StringBuilder(
								"Recall the function correctly, validation errors found\n");
					}
					for (ConstraintViolation<T> constraintViolation : constraintViolations) {
						validationErrors.append(constraintViolation.getPropertyPath())
								.append(": ").append(constraintViolation.getMessage())
								.append("\n");
					}
					thread.add(AssistantMessage.of(choice.message()));
					thread.add(UserMessage.of(validationErrors.toString()));
					log.debug("Adding validation error user message: {}",
							validationErrors);
				}
			}
			catch (JsonProcessingException e) {
				String errorMessage;
				if (request.mode() == Mode.JSON_OBJECT) {
					errorMessage = "Could not deserialize response\n" + e.getMessage();
				}
				else {
					errorMessage = "Recall the function correctly, exceptions during deserialization found\n"
							+ e.getMessage();
				}
				thread.add(AssistantMessage.of(choice.message()));
				thread.add(UserMessage.of(errorMessage));
				log.debug("Adding deserialization error user message: {}", errorMessage);
			}

			retryCount++;
		}

		return new ChatCompletionsModelResponse<>(response, null, "max retries reached");
	}

}
