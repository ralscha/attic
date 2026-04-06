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
package ch.rasc.openai4j.files;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

import ch.rasc.openai4j.common.DeletionStatus;
import ch.rasc.openai4j.common.ListResponse;
import ch.rasc.openai4j.common.PollConfig;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface FilesClient {

	/**
	 * Returns a list of files that belong to the user's organization.
	 *
	 * @return A list of File objects.
	 */
	@RequestLine("GET /files")
	ListResponse<FileObject> list();

	/**
	 * Returns a list of files that belong to the user's organization. Only return files
	 * with the given purpose.
	 *
	 * @return A list of File objects.
	 */
	@RequestLine("GET /files?purpose={purpose}")
	ListResponse<FileObject> list(@Param("purpose") String purpose);

	/**
	 * Returns a list of files that belong to the user's organization. Only return files
	 * with the given purpose.
	 *
	 * @return A list of File objects.
	 */
	default ListResponse<FileObject> list(Purpose purpose) {
		return this.list(purpose.value());
	}

	/**
	 * Upload a file that can be used across various endpoints. The size of all the files
	 * uploaded by one organization can be up to 100 GB.
	 * <p>
	 * The size of individual files can be a maximum of 512 MB or 2 million tokens for
	 * Assistants. The Fine-tuning API only supports .jsonl files.
	 *
	 * @return The uploaded File object.
	 */
	default FileObject upload(Path file, Purpose purpose) {
		return this.upload(file.toFile(), purpose.value());
	}

	/**
	 * Upload a file that can be used across various endpoints. The size of all the files
	 * uploaded by one organization can be up to 100 GB.
	 * <p>
	 * The size of individual files can be a maximum of 512 MB or 2 million tokens for
	 * Assistants.
	 *
	 * @return The uploaded File object.
	 */
	default FileObject createForAssistants(Path file) {
		return this.upload(file, Purpose.ASSISTANTS);
	}

	/**
	 * Upload a file that can be used across various endpoints. The size of all the files
	 * uploaded by one organization can be up to 100 GB.
	 * <p>
	 * The size of individual files can be a maximum of 512 MB or 2 million tokens for
	 * Assistants.
	 *
	 * @return The uploaded File object.
	 */
	default FileObject createForVision(Path file) {
		return this.upload(file, Purpose.VISION);
	}

	/**
	 * Upload a file that can be used across various endpoints. The size of all the files
	 * uploaded by one organization can be up to 100 GB.
	 * <p>
	 * The size of individual files can be a maximum of 512 MB or 2 million tokens for
	 * Assistants.
	 *
	 * @return The uploaded File object.
	 */
	default FileObject createForBatch(Path file) {
		return this.upload(file, Purpose.BATCH);
	}

	/**
	 * Upload a file that can be used across various endpoints. The size of all the files
	 * uploaded by one organization can be up to 100 GB.
	 * <p>
	 * The size of individual files can be a maximum of 512 MB or 2 million tokens for
	 * Assistants. The Fine-tuning API only supports .jsonl files.
	 *
	 * @return The uploaded File object.
	 */
	default FileObject createForFineTune(Path file) {
		return this.upload(file, Purpose.FINE_TUNE);
	}

	/**
	 * Upload a file that can be used across various endpoints. The size of all the files
	 * uploaded by one organization can be up to 100 GB.
	 * <p>
	 * The size of individual files can be a maximum of 512 MB or 2 million tokens for
	 * Assistants. The Fine-tuning API only supports .jsonl files.
	 *
	 * @return The uploaded File object.
	 */
	@RequestLine("POST /files")
	@Headers("Content-Type: multipart/form-data")
	FileObject upload(@Param("file") File file, @Param("purpose") String purpose);

	/**
	 * Delete a file
	 *
	 * @return Deletion status.
	 */
	@RequestLine("DELETE /files/{file_id}")
	DeletionStatus delete(@Param("file_id") String fileId);

	/**
	 * Returns information about a specific file.
	 *
	 * @return The File object matching the specified ID.
	 */
	@RequestLine("GET /files/{file_id}")
	FileObject retrieve(@Param("file_id") String fileId);

	/**
	 * Returns the contents of the specified file.
	 *
	 * @return The file content.
	 */
	@RequestLine("GET /files/{file_id}/content")
	Response retrieveContent(@Param("file_id") String fileId);

	default FileObject waitForProcessing(String fileId) {
		return this.waitForProcessing(fileId, pollConfig -> pollConfig);
	}

	default FileObject waitForProcessing(String fileId,
			Function<PollConfig.Builder, PollConfig.Builder> fn) {

		PollConfig pollConfig = fn.apply(PollConfig.builder()).build();
		long start = System.currentTimeMillis();
		long maxWaitInMillis = pollConfig.maxWaitTimeUnit()
				.toMillis(pollConfig.maxWait());
		long waitUntil = start + maxWaitInMillis;

		FileObject file = this.retrieve(fileId);
		while (!file.status().isTerminal()) {
			try {
				pollConfig.pollIntervalTimeUnit().sleep(pollConfig.pollInterval());
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}

			if (System.currentTimeMillis() > waitUntil) {
				throw new RuntimeException("Giving up on waiting for file " + fileId
						+ " to finish processing after " + pollConfig.maxWait() + " "
						+ pollConfig.maxWaitTimeUnit());
			}

			file = this.retrieve(fileId);
		}

		return file;
	}

}
