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
package ch.rasc.openai4j.threads.runs.steps;

import java.util.Map;

import ch.rasc.openai4j.common.ListRequest;
import ch.rasc.openai4j.common.ListResponse;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

public interface ThreadsRunsStepsClient {

	/**
	 * Retrieves a run step.
	 *
	 * @param threadId The ID of the thread to which the run and run step belongs.
	 * @param runId The ID of the run to which the run step belongs.
	 * @param stepId The ID of the run step to retrieve.
	 * @return The run step object matching the specified ID.
	 */
	@RequestLine("GET /threads/{thread_id}/runs/{run_id}/steps/{step_id}")
	ThreadRunStep retrieve(@Param("thread_id") String threadId,
			@Param("run_id") String runId, @Param("step_id") String stepId);

	/**
	 * Returns a list of run steps belonging to a run.
	 *
	 * @param threadId The ID of the thread to which the run and run step belongs.
	 * @param runId The ID of the run to which the run step belongs.
	 * @return A list of run step objects.
	 */
	@RequestLine("GET /threads/{thread_id}/runs/{run_id}/steps")
	ListResponse<ThreadRunStep> list(@Param("thread_id") String threadId,
			@Param("run_id") String runId);

	/**
	 * Returns a list of run steps belonging to a run.
	 *
	 * @param threadId The ID of the thread to which the run and run step belongs.
	 * @param runId The ID of the run to which the run step belongs.
	 * @return A list of run step objects.
	 */
	@RequestLine("GET /threads/{thread_id}/runs/{run_id}/steps")
	ListResponse<ThreadRunStep> list(@Param("thread_id") String threadId,
			@Param("run_id") String runId, @QueryMap Map<String, Object> queryParameters);

	/**
	 * Returns a list of run steps belonging to a run.
	 *
	 * @param threadId The ID of the thread to which the run and run step belongs.
	 * @param runId The ID of the run to which the run step belongs.
	 * @param request A list request object with configuration for paging and ordering
	 * @return A list of run step objects.
	 */
	default ListResponse<ThreadRunStep> list(@Param("thread_id") String threadId,
			@Param("run_id") String runId, ListRequest request) {
		return this.list(threadId, runId, request.toMap());
	}
}
