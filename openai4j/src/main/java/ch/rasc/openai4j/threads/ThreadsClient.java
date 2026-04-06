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
package ch.rasc.openai4j.threads;

import java.util.function.Function;

import ch.rasc.openai4j.Beta;
import ch.rasc.openai4j.common.DeletionStatus;
import ch.rasc.openai4j.threads.runs.ThreadRun;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Beta
public interface ThreadsClient {

	/**
	 * Create a thread.
	 *
	 * @return A thread object.
	 */
	@RequestLine("POST /threads")
	@Headers("Content-Type: application/json")
	Thread create(ThreadCreateRequest request);

	/**
	 * Create a thread.
	 *
	 * @return A thread object.
	 */
	default Thread create(
			Function<ThreadCreateRequest.Builder, ThreadCreateRequest.Builder> fn) {
		return this.create(fn.apply(ThreadCreateRequest.builder()).build());
	}

	/**
	 * Create an empty thread.
	 *
	 * @return A thread object.
	 */
	default Thread create() {
		return this.create(ThreadCreateRequest.builder().build());
	}

	/**
	 * Create a thread and run it in one request.
	 *
	 * @return A run object.
	 */
	@RequestLine("POST /threads/runs")
	@Headers("Content-Type: application/json")
	ThreadRun createAndRun(ThreadCreateRunCreateRequest request);

	/**
	 * Create a thread and run it in one request.
	 *
	 * @return A run object.
	 */
	default ThreadRun createAndRun(
			Function<ThreadCreateRunCreateRequest.Builder, ThreadCreateRunCreateRequest.Builder> fn) {
		return this
				.createAndRun(fn.apply(ThreadCreateRunCreateRequest.builder()).build());
	}

	/**
	 * Retrieves a thread.
	 *
	 * @return The thread object matching the specified ID.
	 */
	@RequestLine("GET /threads/{thread_id}")
	Thread retrieve(@Param("thread_id") String threadId);

	/**
	 * Modifies a thread.
	 *
	 * @return The modified thread object matching the specified thread ID.
	 */
	@RequestLine("POST /threads/{thread_id}")
	@Headers("Content-Type: application/json")
	Thread modify(@Param("thread_id") String threadId, ThreadModifyRequest request);

	/**
	 * Modifies a thread.
	 * 
	 * @return The modified thread object matching the specified thread ID.
	 */
	default Thread modify(@Param("thread_id") String threadId,
			Function<ThreadModifyRequest.Builder, ThreadModifyRequest.Builder> fn) {
		return this.modify(threadId, fn.apply(ThreadModifyRequest.builder()).build());
	}

	/**
	 * Delete a thread
	 *
	 * @return Deletion status.
	 */
	@RequestLine("DELETE /threads/{thread_id}")
	DeletionStatus delete(@Param("thread_id") String threadId);

}
