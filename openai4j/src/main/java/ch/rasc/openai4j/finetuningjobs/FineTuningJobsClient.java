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
package ch.rasc.openai4j.finetuningjobs;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ch.rasc.openai4j.common.ListResponse;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

public interface FineTuningJobsClient {

	/**
	 * Creates a job that fine-tunes a specified model from a given dataset.
	 *
	 * @return Fine-tuning job object.
	 */
	@RequestLine("POST /fine_tuning/jobs")
	FineTuningJob create(FineTuningJobCreateRequest request);

	/**
	 * Creates a job that fine-tunes a specified model from a given dataset.
	 *
	 * @return Fine-tuning job object.
	 */
	default FineTuningJob create(
			Function<FineTuningJobCreateRequest.Builder, FineTuningJobCreateRequest.Builder> fn) {
		return this.create(fn.apply(FineTuningJobCreateRequest.builder()).build());
	}

	/**
	 * List your organization's fine-tuning jobs
	 *
	 * @return A list of paginated fine-tuning job objects.
	 */
	@RequestLine("GET /fine_tuning/jobs")
	ListResponse<FineTuningJob> list();

	/**
	 * List your organization's fine-tuning jobs
	 *
	 * @return A list of paginated fine-tuning job objects.
	 */
	@RequestLine("GET /fine_tuning/jobs")
	ListResponse<FineTuningJob> list(@QueryMap Map<String, Object> queryParameters);

	/**
	 * List your organization's fine-tuning jobs
	 *
	 * @param after Identifier for the last job from the previous pagination request.
	 * Optional.
	 * @param limit Number of fine-tuning jobs to retrieve. Optional. Defaults to 20.
	 * @return A list of paginated fine-tuning job objects.
	 */
	default ListResponse<FineTuningJob> list(String after, Integer limit) {
		Map<String, Object> queryParameters = new HashMap<>();
		if (after != null && !after.isBlank()) {
			queryParameters.put("after", after);
		}
		if (limit != null) {
			queryParameters.put("limit", limit);
		}
		return this.list(queryParameters);
	}

	/**
	 * Get info about a fine-tuning job.
	 *
	 * @return The fine-tuning object with the given ID.
	 */
	@RequestLine("GET /fine_tuning/jobs/{fine_tuning_job_id}")
	FineTuningJob retrieve(@Param("fine_tuning_job_id") String fineTuningJobId);

	/**
	 * Immediately cancel a fine-tune job.
	 *
	 * @return The cancelled fine-tuning object.
	 */
	@RequestLine("POST /fine_tuning/jobs/{fine_tuning_job_id}/cancel")
	FineTuningJob cancel(@Param("fine_tuning_job_id") String fineTuningJobId);

	/**
	 * Get status updates for a fine-tuning job.
	 *
	 * @return A list of fine-tuning event objects.
	 */
	@RequestLine("GET /fine_tuning/jobs/{fine_tuning_job_id}/events")
	ListResponse<FineTuningJobEvent> listEvents(
			@Param("fine_tuning_job_id") String fineTuningJobId);

	/**
	 * Get status updates for a fine-tuning job.
	 *
	 * @return A list of fine-tuning event objects.
	 */
	@RequestLine("GET /fine_tuning/jobs/{fine_tuning_job_id}/events")
	ListResponse<FineTuningJobEvent> listEvents(
			@Param("fine_tuning_job_id") String fineTuningJobId,
			@QueryMap Map<String, Object> queryParameters);

	/**
	 * Get status updates for a fine-tuning job.
	 *
	 * @param after Identifier for the last event from the previous pagination request.
	 * Optional.
	 * @param limit Number of events to retrieve. Optional. Defaults to 20.
	 * @return A list of fine-tuning event objects.
	 */
	default ListResponse<FineTuningJobEvent> listEvents(String fineTuningJobId,
			String after, Integer limit) {
		Map<String, Object> queryParameters = new HashMap<>();
		if (after != null && !after.isBlank()) {
			queryParameters.put("after", after);
		}
		if (limit != null) {
			queryParameters.put("limit", limit);
		}
		return this.listEvents(fineTuningJobId, queryParameters);
	}

	@RequestLine("GET fine_tuning/jobs/{fine_tuning_job_id}/checkpoints")
	ListResponse<FineTuningJobCheckpoint> listCheckpoints(
			@Param("fine_tuning_job_id") String fineTuningJobId,
			@QueryMap Map<String, Object> queryParameters);

	/**
	 * List checkpoints for a fine-tuning job.
	 *
	 * @param fineTuningJobId The ID of the fine-tuning job to get checkpoints for.
	 * @param after Identifier for the last checkpoint ID from the previous pagination
	 * request. Optional.
	 * @param limit Number of checkpoints to retrieve. Optional. Defaults to 10.
	 * @return A list of fine-tuning checkpoint objects for a fine-tuning job.
	 */
	default ListResponse<FineTuningJobCheckpoint> listCheckpoints(String fineTuningJobId,
			String after, Integer limit) {
		Map<String, Object> queryParameters = new HashMap<>();
		if (after != null && !after.isBlank()) {
			queryParameters.put("after", after);
		}
		if (limit != null) {
			queryParameters.put("limit", limit);
		}
		return this.listCheckpoints(fineTuningJobId, queryParameters);
	}

}
