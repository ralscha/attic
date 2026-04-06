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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("hiding")
public class ThreadRunSubmitToolOutputsRequest {

	@JsonProperty("tool_outputs")
	private final List<ToolOutput> toolOutputs;

	private ThreadRunSubmitToolOutputsRequest(Builder builder) {
		this.toolOutputs = builder.toolOutputs;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<ToolOutput> toolOutputs;

		private Builder() {
		}

		/**
		 * Set tool outputs to the request.
		 */
		public Builder toolOutputs(List<ToolOutput> toolOutputs) {
			this.toolOutputs = new ArrayList<>(toolOutputs);
			return this;
		}

		/**
		 * Add a tool output to the request.
		 * @param toolCallId The ID of the tool call in the required_action object within
		 * the run object the output is being submitted for.
		 * @param output The output of the tool call to be submitted to continue the run.
		 */
		public Builder addToolOutput(String toolCallId, String output) {
			if (this.toolOutputs == null) {
				this.toolOutputs = new ArrayList<>();
			}
			this.toolOutputs.add(new ToolOutput(toolCallId, output));
			return this;
		}

		/**
		 * Add tool outputs to the request.
		 */
		public Builder addToolOutputs(ToolOutput... toolOutputs) {
			if (this.toolOutputs == null) {
				this.toolOutputs = new ArrayList<>();
			}
			this.toolOutputs.addAll(List.of(toolOutputs));
			return this;
		}

		public ThreadRunSubmitToolOutputsRequest build() {
			return new ThreadRunSubmitToolOutputsRequest(this);
		}
	}
}
