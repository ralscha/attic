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

import java.util.Map;
import java.util.function.Function;

import ch.rasc.openai4j.Beta;
import ch.rasc.openai4j.common.DeletionStatus;
import ch.rasc.openai4j.common.ListResponse;
import ch.rasc.openai4j.threads.ThreadMessageRequest;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

@Beta
public interface ThreadsMessagesClient {

	/**
	 * Returns a list of messages for a given thread.
	 *
	 * @param threadId The ID of the thread the messages belong to.
	 * @return A list of message objects.
	 */
	@RequestLine("GET /threads/{thread_id}/messages")
	ListResponse<ThreadMessage> list(@Param("thread_id") String threadId);

	/**
	 * Returns a list of messages for a given thread.
	 *
	 * @param threadId The ID of the thread the messages belong to.
	 * @return A list of message objects.
	 */
	@RequestLine("GET /threads/{thread_id}/messages")
	ListResponse<ThreadMessage> list(@Param("thread_id") String threadId,
			@QueryMap Map<String, Object> queryParameters);

	/**
	 * Returns a list of messages for a given thread.
	 *
	 * @param threadId The ID of the thread the messages belong to.
	 * @param request A list request object with configuration for paging and ordering
	 * @return A list of message objects.
	 */
	default ListResponse<ThreadMessage> list(@Param("thread_id") String threadId,
			ThreadMessagesListRequest request) {
		return this.list(threadId, request.toMap());
	}

	/**
	 * Returns a list of messages for a given thread.
	 *
	 * @param threadId The ID of the thread the messages belong to.
	 * @param fn A list request object with configuration for paging and ordering
	 * @return A list of message objects.
	 */
	default ListResponse<ThreadMessage> list(@Param("thread_id") String threadId,
			Function<ThreadMessagesListRequest.Builder, ThreadMessagesListRequest.Builder> fn) {
		return this.list(threadId, fn.apply(ThreadMessagesListRequest.builder()).build());
	}

	/**
	 * Create a message.
	 *
	 * @return A message object.
	 */
	@RequestLine("POST /threads/{thread_id}/messages")
	@Headers("Content-Type: application/json")
	ThreadMessage create(@Param("thread_id") String threadId,
			ThreadMessageRequest request);

	/**
	 * Create a message.
	 *
	 * @return A message object.
	 */
	default ThreadMessage create(@Param("thread_id") String threadId,
			Function<ThreadMessageRequest.Builder, ThreadMessageRequest.Builder> fn) {
		return this.create(threadId, fn.apply(ThreadMessageRequest.builder()).build());
	}

	/**
	 * Retrieve a message.
	 *
	 * @return The message object matching the specified ID.
	 */
	@RequestLine("GET /threads/{thread_id}/messages/{message_id}")
	ThreadMessage retrieve(@Param("thread_id") String threadId,
			@Param("message_id") String messageId);

	/**
	 * Modifies a message.
	 *
	 * @return The modified message object.
	 */
	@RequestLine("POST /threads/{thread_id}/messages/{message_id}")
	@Headers("Content-Type: application/json")
	ThreadMessage modify(@Param("thread_id") String threadId,
			@Param("message_id") String messageId, ThreadMessageModifyRequest request);

	/**
	 * Deletes a message.
	 * 
	 * @return Deletion status
	 */
	@RequestLine("DELETE /threads/{thread_id}/messages/{message_id}")
	@Headers("Content-Type: application/json")
	DeletionStatus delete(@Param("thread_id") String threadId,
			@Param("message_id") String messageId);

}
