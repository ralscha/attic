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

import java.util.Map;
import java.util.function.Function;

import ch.rasc.openai4j.Beta;
import ch.rasc.openai4j.common.ListRequest;
import ch.rasc.openai4j.common.ListResponse;
import ch.rasc.openai4j.common.PollConfig;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

@Beta
public interface ThreadsRunsClient {

	/**
	 * Create a run.
	 *
	 * @param threadId The ID of the thread to run.
	 * @return A run object.
	 */
	@RequestLine("POST /threads/{thread_id}/runs")
	@Headers("Content-Type: application/json")
	ThreadRun create(@Param("thread_id") String threadId, ThreadRunCreateRequest request);

	/**
	 * Create a run.
	 *
	 * @param threadId The ID of the thread to run.
	 * @return A run object.
	 */
	default ThreadRun create(@Param("thread_id") String threadId,
			Function<ThreadRunCreateRequest.Builder, ThreadRunCreateRequest.Builder> fn) {
		return this.create(threadId, fn.apply(ThreadRunCreateRequest.builder()).build());
	}

	/**
	 * Retrieves a run.
	 *
	 * @param threadId The ID of the thread that was run.
	 * @param runId The ID of the run to retrieve.
	 * @return The run object matching the specified ID.
	 */
	@RequestLine("GET /threads/{thread_id}/runs/{run_id}")
	ThreadRun retrieve(@Param("thread_id") String threadId,
			@Param("run_id") String runId);

	/**
	 * Modifies a run.
	 *
	 * @return The modified run object matching the specified ID.
	 */
	@RequestLine("POST /threads/{thread_id}/runs/{run_id}")
	@Headers("Content-Type: application/json")
	ThreadRun modify(@Param("thread_id") String threadId, @Param("run_id") String runId,
			ThreadRunModifyRequest request);

	/**
	 * Cancels a run that is in_progress.
	 *
	 * @return The modified run object matching the specified ID.
	 */
	@RequestLine("POST /threads/{thread_id}/runs/{run_id}/cancel")
	@Headers("Content-Type: application/json")
	ThreadRun cancel(@Param("thread_id") String threadId, @Param("run_id") String runId);

	/**
	 * Returns a list of runs belonging to a thread.
	 *
	 * @param threadId The ID of the thread the run belongs to.
	 * @return A list of run objects.
	 */
	@RequestLine("GET /threads/{thread_id}/runs")
	ListResponse<ThreadRun> list(@Param("thread_id") String threadId);

	/**
	 * Returns a list of runs belonging to a thread.
	 *
	 * @param threadId The ID of the thread the run belongs to.
	 * @return A list of run objects.
	 */
	@RequestLine("GET /threads/{thread_id}/runs")
	ListResponse<ThreadRun> list(@Param("thread_id") String threadId,
			@QueryMap Map<String, Object> queryParameters);

	/**
	 * Returns a list of runs belonging to a thread.
	 *
	 * @param threadId The ID of the thread the run belongs to.
	 * @param request A list request object with configuration for paging and ordering
	 * @return A list of run objects.
	 */
	default ListResponse<ThreadRun> list(@Param("thread_id") String threadId,
			ListRequest request) {
		return this.list(threadId, request.toMap());
	}

	/**
	 * Returns a list of runs belonging to a thread.
	 *
	 * @param threadId The ID of the thread the run belongs to.
	 * @param fn A list request object with configuration for paging and ordering
	 * @return A list of run objects.
	 */
	default ListResponse<ThreadRun> list(@Param("thread_id") String threadId,
			Function<ListRequest.Builder, ListRequest.Builder> fn) {
		return this.list(threadId, fn.apply(ListRequest.builder()).build());
	}

	/**
	 * When a run has the status: "requires_action" and required_action.type is
	 * submit_tool_outputs, this endpoint can be used to submit the outputs from the tool
	 * calls once they're all completed. All outputs must be submitted in a single
	 * request.
	 *
	 * @param threadId The ID of the thread to which this run belongs.
	 * @param runId The ID of the run that requires the tool output submission.
	 * @return The modified run object matching the specified ID.
	 */
	@RequestLine("POST /threads/{thread_id}/runs/{run_id}/submit_tool_outputs")
	ThreadRun submitToolOutputs(@Param("thread_id") String threadId,
			@Param("run_id") String runId, ThreadRunSubmitToolOutputsRequest request);

	/**
	 * When a run has the status: "requires_action" and required_action.type is
	 * submit_tool_outputs, this endpoint can be used to submit the outputs from the tool
	 * calls once they're all completed. All outputs must be submitted in a single
	 * request.
	 *
	 * @param threadId The ID of the thread to which this run belongs.
	 * @param runId The ID of the run that requires the tool output submission.
	 * @return The modified run object matching the specified ID.
	 */
	default ThreadRun submitToolOutputs(@Param("thread_id") String threadId,
			@Param("run_id") String runId,
			Function<ThreadRunSubmitToolOutputsRequest.Builder, ThreadRunSubmitToolOutputsRequest.Builder> fn) {
		return this.submitToolOutputs(threadId, runId,
				fn.apply(ThreadRunSubmitToolOutputsRequest.builder()).build());
	}

	/**
	 * Wait for the thread run to finish processing. This method will poll the server
	 * every 1 second until the run is finished or 2 minutes have passed.
	 *
	 * @return The latest VectorStore object
	 */
	default ThreadRun waitForProcessing(ThreadRun run) {
		return waitForProcessing(run, pollConfig -> pollConfig);
	}

	/**
	 * Wait for the thread run to finish processing. This method will poll the server
	 * every pollInterval until the run is finished or until maxWait have passed.
	 *
	 * @return The latest ThreadRun object
	 */
	default ThreadRun waitForProcessing(ThreadRun run,
			Function<PollConfig.Builder, PollConfig.Builder> fn) {
		PollConfig pollConfig = fn.apply(PollConfig.builder()).build();

		long start = System.currentTimeMillis();
		long maxWaitInMillis = pollConfig.maxWaitTimeUnit()
				.toMillis(pollConfig.maxWait());
		long waitUntil = start + maxWaitInMillis;

		ThreadRun currentRun = this.retrieve(run.threadId(), run.id());

		while (!currentRun.status().isTerminal()) {
			try {
				pollConfig.pollIntervalTimeUnit().sleep(pollConfig.pollInterval());
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}

			if (System.currentTimeMillis() > waitUntil) {
				throw new RuntimeException("Giving up on waiting for thread run "
						+ run.id() + " to finish processing after " + pollConfig.maxWait()
						+ " " + pollConfig.maxWaitTimeUnit());
			}

			currentRun = this.retrieve(currentRun.threadId(), currentRun.id());
		}
		return currentRun;
	}

}
