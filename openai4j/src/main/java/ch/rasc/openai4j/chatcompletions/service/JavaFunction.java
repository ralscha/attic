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

import java.util.function.Function;

import ch.rasc.openai4j.chatcompletions.ChatCompletionTool;
import ch.rasc.openai4j.common.FunctionParameters;
import ch.rasc.openai4j.common.JsonSchemaService;

public class JavaFunction<T, R> {

	private final String name;

	private final String description;

	private final Class<T> parameterClass;

	private final Function<T, R> functionCall;

	private JavaFunction(String name, String description, Class<T> parameterClass,
			Function<T, R> functionCall) {
		this.name = name;
		this.description = description;
		this.parameterClass = parameterClass;
		this.functionCall = functionCall;
	}

	/**
	 * Create a new function with the given name, description, parameter class and
	 * function executor.
	 *
	 * @param name The name of the function to be called. Must be a-z, A-Z, 0-9, or
	 * contain underscores and dashes, with a maximum length of 64.
	 * @param description A description of what the function does, used by the model to
	 * choose when and how to call the function.
	 * @param parameterClass The class of the parameter that will be passed to the
	 * function executor.
	 * @param functionExecutor The function that will be called when the model executes
	 * the function.
	 * @return A new function.
	 * @param <T> Parameter type
	 * @param <R> Return type
	 */
	public static <T, R> JavaFunction<T, R> of(String name, String description,
			Class<T> parameterClass, Function<T, R> functionExecutor) {
		return new JavaFunction<>(name, description, parameterClass, functionExecutor);
	}

	/**
	 * The name of the function to be called. Must be a-z, A-Z, 0-9, or contain
	 * underscores and dashes, with a maximum length of 64.
	 */
	public String name() {
		return this.name;
	}

	/**
	 * Parameter class of the function.
	 */
	public Class<T> parameterClass() {
		return this.parameterClass;
	}

	/**
	 * Converts the function to a {@link ChatCompletionTool}.
	 */
	ChatCompletionTool toTool(JsonSchemaService jsonSchemaService) {
		return ChatCompletionTool.of(FunctionParameters.of(this.name, this.description,
				jsonSchemaService.generateStrictSchema(this.parameterClass), true));
	}

	public R call(Object parameter) {
		return this.functionCall.apply((T) parameter);
	}

}
