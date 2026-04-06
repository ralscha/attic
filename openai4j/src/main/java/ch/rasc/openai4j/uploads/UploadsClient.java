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
package ch.rasc.openai4j.uploads;

import java.io.File;
import java.util.function.Function;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface UploadsClient {

	/**
	 * Creates an intermediate Upload object that you can add Parts to. Currently, an
	 * Upload can accept at most 8 GB in total and expires after an hour after you create
	 * it.
	 * <p>
	 * Once you complete the Upload, we will create a File object that contains all the
	 * parts you uploaded. This File is usable in the rest of our platform as a regular
	 * File object.
	 * <p>
	 * For certain purposes, the correct mime_type must be specified
	 * 
	 * @return The Upload object with status pending.
	 */
	@RequestLine("POST /uploads")
	@Headers("Content-Type: application/json")
	Upload create(UploadCreateRequest request);

	/**
	 * Creates an intermediate Upload object that you can add Parts to. Currently, an
	 * Upload can accept at most 8 GB in total and expires after an hour after you create
	 * it.
	 * <p>
	 * Once you complete the Upload, we will create a File object that contains all the
	 * parts you uploaded. This File is usable in the rest of our platform as a regular
	 * File object.
	 * <p>
	 * For certain purposes, the correct mime_type must be specified
	 * 
	 * @return The Upload object with status pending.
	 */
	default Upload create(
			Function<UploadCreateRequest.Builder, UploadCreateRequest.Builder> fn) {
		return this.create(fn.apply(UploadCreateRequest.builder()).build());
	}

	/**
	 * Adds a Part to an Upload object. A Part represents a chunk of bytes from the file
	 * you are trying to upload.
	 * <p>
	 * Each Part can be at most 64 MB, and you can add Parts until you hit the Upload
	 * maximum of 8 GB.
	 * <p>
	 * It is possible to add multiple Parts in parallel. You can decide the intended order
	 * of the Parts when you complete the Upload.
	 * 
	 * @return The upload Part object.
	 */
	@RequestLine("POST /uploads/{upload_id}/parts")
	@Headers("Content-Type: multipart/form-data")
	UploadPart addPart(@Param("upload_id") String uploadId, @Param("data") File data);

	/**
	 * Completes the Upload.
	 * <p>
	 * Within the returned Upload object, there is a nested File object that is ready to
	 * use in the rest of the platform.
	 * <p>
	 * You can specify the order of the Parts by passing in an ordered list of the Part
	 * IDs.
	 * <p>
	 * The number of bytes uploaded upon completion must match the number of bytes
	 * initially specified when creating the Upload object. No Parts may be added after an
	 * Upload is completed.
	 * 
	 * @return The Upload object with status completed with an additional file property
	 * containing the created usable File object.
	 */
	@RequestLine("POST /uploads/{upload_id}/complete")
	@Headers("Content-Type: application/json")
	Upload complete(@Param("upload_id") String uploadId, UploadCompleteRequest request);

	/**
	 * Completes the Upload.
	 * <p>
	 * Within the returned Upload object, there is a nested File object that is ready to
	 * use in the rest of the platform.
	 * <p>
	 * You can specify the order of the Parts by passing in an ordered list of the Part
	 * IDs.
	 * <p>
	 * The number of bytes uploaded upon completion must match the number of bytes
	 * initially specified when creating the Upload object. No Parts may be added after an
	 * Upload is completed.
	 * 
	 * @return The Upload object with status completed with an additional file property
	 * containing the created usable File object.
	 */
	default Upload complete(String uploadId,
			Function<UploadCompleteRequest.Builder, UploadCompleteRequest.Builder> fn) {
		return this.complete(uploadId, fn.apply(UploadCompleteRequest.builder()).build());
	}

	/**
	 * Cancels the Upload. No Parts may be added after an Upload is cancelled.
	 * 
	 * @return The Upload object with status cancelled.
	 */
	@RequestLine("POST /uploads/{upload_id}/cancel")
	@Headers("Content-Type: application/json")
	Upload cancel(@Param("upload_id") String uploadId);
}
