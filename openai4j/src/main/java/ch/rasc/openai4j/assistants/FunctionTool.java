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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.common.FunctionParameters;

public class FunctionTool extends Tool {

	private final FunctionParameters function;

	private FunctionTool(FunctionParameters function) {
		super("function");
		this.function = function;
	}

	@JsonCreator
	public static FunctionTool of(@JsonProperty("function") FunctionParameters function) {
		return new FunctionTool(function);
	}

	@JsonProperty
	FunctionParameters function() {
		return this.function;
	}
}
