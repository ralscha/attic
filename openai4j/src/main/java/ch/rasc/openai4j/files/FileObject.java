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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public record FileObject(String id, Integer bytes,
		@JsonProperty("created_at") Integer createdAt, String filename, String object,
		Purpose purpose, Status status,
		@JsonProperty("status_details") String statusDetails) {

	public enum Status {
		UPLOADED("uploaded"), PROCESSED("processed"), ERROR("error"), DELETED("deleted");

		private final String value;

		Status(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}

		public boolean isTerminal() {
			return this == PROCESSED || this == ERROR || this == DELETED;
		}
	}

	/**
	 * The file identifier, which can be referenced in the API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The size of the file, in bytes.
	 */
	@Override
	public Integer bytes() {
		return this.bytes;
	}

	/**
	 * The Unix timestamp (in seconds) for when the file was created.
	 */
	@Override
	public Integer createdAt() {
		return this.createdAt;
	}

	/**
	 * The name of the file.
	 */
	@Override
	public String filename() {
		return this.filename;
	}

	/**
	 * The object type, which is always <code>file</code>.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The intended purpose of the file.
	 */
	@Override
	public Purpose purpose() {
		return this.purpose;
	}

	/**
	 * Deprecated. The current status of the file.
	 */
	@Override
	@Deprecated
	public Status status() {
		return this.status;
	}

	/**
	 * Deprecated. The details on why a fine-tuning training file failed validation.
	 */
	@Override
	@Deprecated
	public String statusDetails() {
		return this.statusDetails;
	}

}
