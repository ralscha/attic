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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ToolMessage extends ChatCompletionMessage {

	private final String toolCallId;
	private final String content;

	private ToolMessage(String toolCallId, String content) {
		if (toolCallId == null) {
			throw new IllegalArgumentException("toolCallId must not be null");
		}
		this.toolCallId = toolCallId;
		this.content = content;
	}

	/**
	 * Create a new tool message.
	 *
	 * @param content The contents of the tool message.
	 * @param toolCallId Tool call that this message is responding to.
	 */
	public static ToolMessage of(String toolCallId, String content) {
		return new ToolMessage(toolCallId, content);
	}

	/**
	 * The contents of the tool message.
	 */
	@JsonInclude
	@JsonProperty
	public String content() {
		return this.content;
	}

	/**
	 * Tool call that this message is responding to.
	 */
	@JsonProperty("tool_call_id")
	public String toolCallId() {
		return this.toolCallId;
	}

	@Override
	String role() {
		return "tool";
	}
}
