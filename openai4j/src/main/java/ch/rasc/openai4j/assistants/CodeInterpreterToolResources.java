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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@SuppressWarnings({ "hiding" })
public class CodeInterpreterToolResources {

	@JsonProperty("file_ids")
	private final List<String> fileIds;

	@JsonCreator
	private CodeInterpreterToolResources(@JsonProperty("file_ids") List<String> fileIds) {
		this.fileIds = fileIds;
	}

	private CodeInterpreterToolResources(Builder builder) {
		this.fileIds = builder.fileIds;
	}

	public List<String> fileIds() {
		return this.fileIds;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private List<String> fileIds = new ArrayList<>();

		/**
		 * A list of file IDs made available to the code_interpreter tool. There can be a
		 * maximum of 20 files associated with the tool.
		 */
		public Builder fileIds(List<String> fileIds) {
			this.fileIds = new ArrayList<>(fileIds);
			return this;
		}

		/**
		 * A list of file IDs made available to the code_interpreter tool. There can be a
		 * maximum of 20 files associated with the tool.
		 */
		public Builder addFileIds(String... fileIds) {
			if (fileIds == null || fileIds.length == 0) {
				return this;
			}

			if (this.fileIds == null) {
				this.fileIds = new ArrayList<>();
			}
			this.fileIds.addAll(List.of(fileIds));
			return this;
		}

		public CodeInterpreterToolResources build() {
			return new CodeInterpreterToolResources(this);
		}
	}
}
