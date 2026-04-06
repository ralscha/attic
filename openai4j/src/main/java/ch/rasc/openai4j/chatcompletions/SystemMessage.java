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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class SystemMessage extends ChatCompletionMessage {
	private final String content;
	private final String name;

	private SystemMessage(String content, String name) {
		if (content == null) {
			throw new IllegalArgumentException("content must not be null");
		}
		this.content = content;
		this.name = name;
	}

	/**
	 * Create a new system message with the given content.
	 *
	 * @param content The contents of the system message.
	 */
	public static SystemMessage of(String content) {
		return new SystemMessage(content, null);
	}

	/**
	 * Create a new system message with the given content and name.
	 *
	 * @param content The contents of the system message.
	 * @param name An optional name for the participant. Provides the model information to
	 * differentiate between participants of the same role.
	 */
	public static SystemMessage of(String content, String name) {
		return new SystemMessage(content, name);
	}

	/**
	 * The contents of the system message.
	 */
	@JsonInclude
	@JsonProperty
	public String content() {
		return this.content;
	}

	/**
	 * An optional name for the participant. Provides the model information to
	 * differentiate between participants of the same role.
	 */
	@JsonProperty
	public String name() {
		return this.name;
	}

	/**
	 * The role of the messages author, in this case <code>system</code>.
	 */
	@Override
	String role() {
		return "system";
	}
}
