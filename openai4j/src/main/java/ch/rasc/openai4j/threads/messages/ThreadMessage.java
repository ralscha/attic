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
package ch.rasc.openai4j.threads.messages;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.threads.MessageContent;
import ch.rasc.openai4j.threads.ThreadMessageRequest.Attachment;

/**
 * Represents a message within a thread.
 */
public record ThreadMessage(String id, String object,
		@JsonProperty("created_at") int createdAt,
		@JsonProperty("thread_id") String threadId, String status,
		@JsonProperty("incomplete_details") IncompleteDetail incompleteDetails,
		@JsonProperty("completed_at") Integer completedAt,
		@JsonProperty("incomplete_at") Integer incompleteAt, String role,
		List<MessageContent> content, @JsonProperty("assistant_id") String assistantId,
		@JsonProperty("run_id") String runId, List<Attachment> attachments,
		Map<String, String> metadata) {

	/**
	 * The identifier, which can be referenced in API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The object type, which is always thread.message.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The Unix timestamp (in seconds) for when the message was created.
	 */
	@Override
	public int createdAt() {
		return this.createdAt;
	}

	/**
	 * The thread ID that this message belongs to.
	 */
	@Override
	public String threadId() {
		return this.threadId;
	}

	/**
	 * The status of the message, which can be either in_progress, incomplete, or
	 * completed.
	 */
	@Override
	public String status() {
		return this.status;
	}

	/**
	 * On an incomplete message, details about why the message is incomplete.
	 */
	@Override
	public IncompleteDetail incompleteDetails() {
		return this.incompleteDetails;
	}

	/**
	 * The Unix timestamp (in seconds) for when the message was completed.
	 */
	@Override
	public Integer completedAt() {
		return this.completedAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the message was marked as incomplete.
	 */
	@Override
	public Integer incompleteAt() {
		return this.incompleteAt;
	}

	/**
	 * The entity that produced the message. One of user or assistant.
	 */
	@Override
	public String role() {
		return this.role;
	}

	/**
	 * The content of the message in array of text and/or images.
	 */
	@Override
	public List<MessageContent> content() {
		return this.content;
	}

	/**
	 * If applicable, the ID of the assistant that authored this message.
	 */
	@Override
	public String assistantId() {
		return this.assistantId;
	}

	/**
	 * The ID of the run associated with the creation of this message. Value is null when
	 * messages are created manually using the create message or create thread endpoints.
	 */
	@Override
	public String runId() {
		return this.runId;
	}

	/**
	 * A list of files attached to the message, and the tools they were added to.
	 */
	@Override
	public List<Attachment> attachments() {
		return this.attachments;
	}

	/**
	 * Set of 16 key-value pairs that can be attached to an object. This can be useful for
	 * storing additional information about the object in a structured format. Keys can be
	 * a maximum of 64 characters long and values can be a maxium of 512 characters long.
	 */
	@Override
	public Map<String, String> metadata() {
		return this.metadata;
	}

	public record IncompleteDetail(String reason) {
		/**
		 * The reason the message is incomplete.
		 */
		@Override
		public String reason() {
			return this.reason;
		}
	}

}
