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
package ch.rasc.openai4j.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FunctionParameters {
	private final String name;
	private final String description;
	private final Object parameters;
	private final Boolean strict;

	private FunctionParameters(String name, String description, Object parameters,
			Boolean strict) {
		this.name = name;
		this.description = description;
		this.parameters = parameters;
		this.strict = strict;
	}

	@JsonCreator
	public static FunctionParameters of(@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("parameters") Object parameters,
			@JsonProperty("strict") Boolean strict) {
		return new FunctionParameters(name, description, parameters, strict);
	}

	/**
	 * Create a FunctionParameters object with the given name and description.
	 * @param name The name of the function to be called. Must be a-z, A-Z, 0-9, or
	 * contain
	 * @param parameters The parameters the functions accepts, described as a JSON Schema
	 * object.Omitting parameters defines a function with an empty parameter list.
	 * @return A FunctionParameters object with the given name and description.
	 */
	public static FunctionParameters of(String name, Object parameters) {
		return new FunctionParameters(name, null, parameters, null);
	}

	/**
	 * Create a FunctionParameters object with the given name and description.
	 * @param name The name of the function to be called. Must be a-z, A-Z, 0-9, or
	 * contain
	 * @param parameters The parameters the functions accepts, described as a JSON Schema
	 * object.Omitting parameters defines a function with an empty parameter list.
	 * @param strict Whether to enable strict schema adherence when generating the
	 * function call. If set to true, the model will follow the exact schema defined in
	 * the parameters field. Only a subset of JSON Schema is supported when strict is
	 * true.
	 * @return A FunctionParameters object with the given name and description.
	 */
	public static FunctionParameters of(String name, Object parameters, boolean strict) {
		return new FunctionParameters(name, null, parameters, strict);
	}

	/**
	 * Create a FunctionParameters object with the given name and description. Omits the
	 * parameters and therefore defines a function with an empty parameter list.
	 * @param name The name of the function to be called. Must be a-z, A-Z, 0-9, or
	 * contain
	 * @return A FunctionParameters object with the given name and description.
	 */
	public static FunctionParameters of(String name) {
		return new FunctionParameters(name, null, null, null);
	}

	/**
	 * A description of what the function does, used by the model to choose when and how
	 * to call the function.
	 */
	@JsonProperty
	public String description() {
		return this.description;
	}

	/**
	 * The name of the function to be called. Must be a-z, A-Z, 0-9, or contain
	 * underscores and dashes, with a maximum length of 64.
	 */
	@JsonProperty
	public String name() {
		return this.name;
	}

	/**
	 * The parameters the functions accepts, described as a JSON Schema object.
	 * <p>
	 * Omitting parameters defines a function with an empty parameter list.
	 */
	@JsonProperty
	public Object parameters() {
		return this.parameters;
	}

	/**
	 * Whether to enable strict schema adherence when generating the function call. If set
	 * to true, the model will follow the exact schema defined in the parameters field.
	 * Only a subset of JSON Schema is supported when strict is true.
	 * <p>
	 * Optional, defaults to false
	 */
	@JsonProperty
	public Boolean strict() {
		return this.strict;
	}
}
