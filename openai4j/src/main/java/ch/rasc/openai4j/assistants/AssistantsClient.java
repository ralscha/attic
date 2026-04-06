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
package ch.rasc.openai4j.assistants;

import java.util.Map;
import java.util.function.Function;

import ch.rasc.openai4j.Beta;
import ch.rasc.openai4j.common.DeletionStatus;
import ch.rasc.openai4j.common.ListRequest;
import ch.rasc.openai4j.common.ListResponse;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

@Beta
public interface AssistantsClient {

	/**
	 * Create an assistant with a model and instructions.
	 *
	 * @return An assistant object.
	 */
	@RequestLine("POST /assistants")
	@Headers("Content-Type: application/json")
	Assistant create(AssistantCreateRequest request);

	/**
	 * Create an assistant with a model and instructions.
	 *
	 * @return An assistant object.
	 */
	default Assistant create(
			Function<AssistantCreateRequest.Builder, AssistantCreateRequest.Builder> fn) {
		return this.create(fn.apply(AssistantCreateRequest.builder()).build());
	}

	/**
	 * Retrieves an assistant.
	 *
	 * @return The assistant object matching the specified ID.
	 */
	@RequestLine("GET /assistants/{assistant_id}")
	Assistant retrieve(@Param("assistant_id") String assistantId);

	/**
	 * Modifies an assistant.
	 *
	 * @return The modified assistant object.
	 */
	@RequestLine("POST /assistants/{assistant_id}")
	@Headers("Content-Type: application/json")
	Assistant modify(@Param("assistant_id") String assistantId,
			AssistantModifyRequest request);

	/**
	 * Modifies an assistant.
	 *
	 * @return The modified assistant object.
	 */
	default Assistant modify(@Param("assistant_id") String assistantId,
			Function<AssistantModifyRequest.Builder, AssistantModifyRequest.Builder> fn) {
		return this.modify(assistantId,
				fn.apply(AssistantModifyRequest.builder()).build());
	}

	/**
	 * Delete an assistant.
	 *
	 * @return Deletion status
	 */
	@RequestLine("DELETE /assistants/{assistant_id}")
	DeletionStatus delete(@Param("assistant_id") String assistantId);

	/**
	 * Returns a list of assistants.
	 *
	 * @return A list of assistant objects.
	 */
	@RequestLine("GET /assistants")
	ListResponse<Assistant> list();

	/**
	 * Returns a list of assistants.
	 *
	 * @return A list of assistant objects.
	 */
	@RequestLine("GET /assistants")
	ListResponse<Assistant> list(@QueryMap Map<String, Object> queryParameters);

	/**
	 * Returns a list of assistants.
	 *
	 * @param request A list request object with configuration for paging and ordering
	 * @return A list of assistant objects.
	 */
	default ListResponse<Assistant> list(ListRequest request) {
		return this.list(request.toMap());
	}

	/**
	 * Returns a list of assistants.
	 *
	 * @param fn A list request object with configuration for paging and ordering
	 * @return A list of assistant objects.
	 */
	default ListResponse<Assistant> list(
			Function<ListRequest.Builder, ListRequest.Builder> fn) {
		return this.list(fn.apply(ListRequest.builder()).build());
	}
}
