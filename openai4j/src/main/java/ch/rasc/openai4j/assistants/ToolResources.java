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

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A set of resources that are used by the assistant's tools. The resources are specific
 * to the type of tool. For example, the code_interpreter tool requires a list of file
 * IDs, while the file_search tool requires a list of vector store IDs.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ToolResources {

	@JsonProperty("code_interpreter")
	CodeInterpreterToolResources codeInterpreter;
	@JsonProperty("file_search")
	private final FileSearchToolResources fileSearch;

	@JsonCreator
	private ToolResources(
			@JsonProperty("code_interpreter") CodeInterpreterToolResources codeInterpreter,
			@JsonProperty("file_search") FileSearchToolResources fileSearch) {
		this.codeInterpreter = codeInterpreter;
		this.fileSearch = fileSearch;
	}

	public static ToolResources ofCodeInterpreter(
			CodeInterpreterToolResources codeInterpreter) {
		return new ToolResources(codeInterpreter, null);
	}

	public static ToolResources ofCodeInterpreter(
			Function<CodeInterpreterToolResources.Builder, CodeInterpreterToolResources.Builder> fn) {
		return new ToolResources(
				fn.apply(new CodeInterpreterToolResources.Builder()).build(), null);
	}

	public static ToolResources ofFileSearch(FileSearchToolResources fileSearch) {
		return new ToolResources(null, fileSearch);
	}

	public static ToolResources ofFileSearch(
			Function<FileSearchToolResources.Builder, FileSearchToolResources.Builder> fn) {
		return new ToolResources(null,
				fn.apply(new FileSearchToolResources.Builder()).build());
	}

	public CodeInterpreterToolResources codeInterpreter() {
		return this.codeInterpreter;
	}

	public FileSearchToolResources fileSearch() {
		return this.fileSearch;
	}
}
