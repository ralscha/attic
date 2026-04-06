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
package ch.rasc.openai4j.vectorstores.files;

import java.util.Map;
import java.util.function.Function;

import ch.rasc.openai4j.Beta;
import ch.rasc.openai4j.common.DeletionStatus;
import ch.rasc.openai4j.common.ListResponse;
import ch.rasc.openai4j.common.PollConfig;
import ch.rasc.openai4j.vectorstores.files.VectorStoreFile.VectorStoreFileStatus;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

@Beta
public interface VectorStoresFilesClient {

	/**
	 * Returns a list of vector store files.
	 *
	 * @return A list of vector store objects.
	 */
	@RequestLine("GET /vector_stores/{vector_store_id}/files")
	ListResponse<VectorStoreFile> list(@Param("vector_store_id") String vectorStoreId);

	/**
	 * Returns a list of vector store files.
	 *
	 * @return A list of vector store objects.
	 */
	@RequestLine("GET /vector_stores/{vector_store_id}/files")
	ListResponse<VectorStoreFile> list(@Param("vector_store_id") String vectorStoreId,
			@QueryMap Map<String, Object> queryParameters);

	/**
	 * Returns a list of vector store files.
	 *
	 * @param request A list request object with configuration for paging and ordering
	 * @return A list of vector store objects.
	 */
	default ListResponse<VectorStoreFile> list(
			@Param("vector_store_id") String vectorStoreId,
			VectorStoresFilesListRequest request) {
		return this.list(vectorStoreId, request.toMap());
	}

	/**
	 * Returns a list of vector store files.
	 *
	 * @param fn A list request object with configuration for paging and ordering
	 * @return A list of vector store objects.
	 */
	default ListResponse<VectorStoreFile> list(
			@Param("vector_store_id") String vectorStoreId,
			Function<VectorStoresFilesListRequest.Builder, VectorStoresFilesListRequest.Builder> fn) {
		return this.list(vectorStoreId,
				fn.apply(VectorStoresFilesListRequest.builder()).build());
	}

	/**
	 * Create a vector store file by attaching a File to a vector store.
	 *
	 * @return A vector store file object.
	 */
	@RequestLine("POST /vector_stores/{vector_store_id}/files")
	@Headers("Content-Type: application/json")
	VectorStoreFile create(@Param("vector_store_id") String vectorStoreId,
			VectorStoreFileCreateRequest request);

	/**
	 * Create a vector store file by attaching a File to a vector store.
	 *
	 * @return A vector store file object.
	 */
	default VectorStoreFile create(String vectorStoreId, String fileId) {
		return this.create(vectorStoreId, VectorStoreFileCreateRequest.of(fileId));
	}

	/**
	 * Adding file to vector stores is an async operation. This method will poll the
	 * server every 1 second until file has been processed or 2 minutes have passed.
	 *
	 * @return The latest VectorStore object
	 */
	default VectorStoreFile waitForProcessing(VectorStoreFile vectorStoreFile) {
		return waitForProcessing(vectorStoreFile, pollConfig -> pollConfig);
	}

	/**
	 * Adding file to vector stores is an async operation. This method will poll the
	 * server every pollInterval until file has been processed or maxWait has passed.
	 *
	 * @return The latest VectorStore object
	 */
	default VectorStoreFile waitForProcessing(VectorStoreFile vectorStoreFile,
			Function<PollConfig.Builder, PollConfig.Builder> fn) {
		PollConfig pollConfig = fn.apply(PollConfig.builder()).build();

		long start = System.currentTimeMillis();
		long maxWaitInMillis = pollConfig.maxWaitTimeUnit()
				.toMillis(pollConfig.maxWait());
		long waitUntil = start + maxWaitInMillis;

		VectorStoreFile currentVectorStoreFile = this
				.retrieve(vectorStoreFile.vectorStoreId(), vectorStoreFile.id());
		while (currentVectorStoreFile.status() == VectorStoreFileStatus.IN_PROGRESS) {
			try {
				pollConfig.pollIntervalTimeUnit().sleep(pollConfig.pollInterval());
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}

			if (System.currentTimeMillis() > waitUntil) {
				throw new RuntimeException("Giving up on waiting for vector store file "
						+ currentVectorStoreFile.id() + " to finish processing after "
						+ pollConfig.maxWait() + " " + pollConfig.maxWaitTimeUnit());
			}
			currentVectorStoreFile = this.retrieve(currentVectorStoreFile.vectorStoreId(),
					currentVectorStoreFile.id());
		}

		return currentVectorStoreFile;
	}

	/**
	 * Retrieves a vector store file.
	 *
	 * @param vectorStoreId The ID of the vector store that the file belongs to.
	 * @param fileId The ID of the file being retrieved.
	 *
	 * @return The vector store file object.
	 */
	@RequestLine("GET /vector_stores/{vector_store_id}/files/{file_id}")
	VectorStoreFile retrieve(@Param("vector_store_id") String vectorStoreId,
			@Param("file_id") String fileId);

	/**
	 * Delete a vector store file. This will remove the file from the vector store but the
	 * file itself will not be deleted. To delete the file, use the delete file endpoint.
	 *
	 * @param vectorStoreId The ID of the vector store that the file belongs to.
	 * @param fileId The ID of the file to delete.
	 *
	 * @return Deletion status
	 */
	@RequestLine("DELETE /vector_stores/{vector_store_id}/files/{file_id}")
	@Headers("Content-Type: application/json")
	DeletionStatus delete(@Param("vector_store_id") String vectorStoreId,
			@Param("file_id") String fileId);

}
