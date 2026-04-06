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
package ch.rasc.openai4j.batch;

import java.util.function.Function;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface BatchesClient {

	/**
	 * Creates and executes a batch from an uploaded file of requests
	 *
	 * @return Returns a batch object
	 */
	@RequestLine("POST /batches")
	@Headers("Content-Type: application/json")
	Batch create(BatchCreateRequest request);

	/**
	 * Creates and executes a batch from an uploaded file of requests
	 *
	 * @return Returns a batch object
	 */
	default Batch create(
			Function<BatchCreateRequest.Builder, BatchCreateRequest.Builder> fn) {
		return this.create(fn.apply(BatchCreateRequest.builder()).build());
	}

	/**
	 * Retrieves a batch.
	 * 
	 * @return Returns a batch object
	 */
	@RequestLine("GET /batches/{batchId}")
	Batch retrieve(@Param("batchId") String batchId);

	/**
	 * Cancels an in-progress batch.
	 * 
	 * @return Returns a batch object
	 */
	@RequestLine("POST /batches/{batchId}/cancel")
	Batch cancel(@Param("batchId") String batchId);
}
