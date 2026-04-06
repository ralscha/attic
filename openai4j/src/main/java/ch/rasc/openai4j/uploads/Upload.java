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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import ch.rasc.openai4j.files.FileObject;
import ch.rasc.openai4j.files.Purpose;

/**
 * The Upload object can accept byte chunks in the form of Parts.
 */
public record Upload(String id, @JsonProperty("created_at") int createdAt,
		String filename, long bytes, Purpose purpose, Status status,
		@JsonProperty("expires_at") int expiresAt, String object, FileObject file) {

	public enum Status {
		PENDING("pending"), COMPLETED("completed"), CANCELLED("cancelled");

		private final String value;

		Status(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	/**
	 * The Upload unique identifier, which can be referenced in API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The Unix timestamp (in seconds) for when the Upload was created.
	 */
	@Override
	public int createdAt() {
		return this.createdAt;
	}

	/**
	 * The name of the file to be uploaded.
	 */
	@Override
	public String filename() {
		return this.filename;
	}

	/**
	 * The intended number of bytes to be uploaded.
	 */
	@Override
	public long bytes() {
		return this.bytes;
	}

	/**
	 * The intended purpose of the file. Please refer here for acceptable values.
	 */
	@Override
	public Purpose purpose() {
		return this.purpose;
	}

	/**
	 * The status of the Upload.
	 */
	@Override
	public Status status() {
		return this.status;
	}

	/**
	 * The Unix timestamp (in seconds) for when the Upload was created.
	 */
	@Override
	public int expiresAt() {
		return this.expiresAt;
	}

	/**
	 * The object type, which is always "upload".
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The file object created after the upload is completed.
	 */
	@Override
	public FileObject file() {
		return this.file;
	}
}
