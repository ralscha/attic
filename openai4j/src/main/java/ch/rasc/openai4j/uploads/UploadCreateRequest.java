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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.files.Purpose;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class UploadCreateRequest {

	private final String filename;

	private final Purpose purpose;

	private final long bytes;

	@JsonProperty("mime_type")
	private final String mimeType;

	private UploadCreateRequest(Builder builder) {
		if (builder.filename == null || builder.filename.isEmpty()) {
			throw new IllegalArgumentException("filename must not be null or empty");
		}
		this.filename = builder.filename;

		if (builder.purpose == null) {
			throw new IllegalArgumentException("purpose must not be null or empty");
		}
		this.purpose = builder.purpose;

		if (builder.bytes <= 0) {
			throw new IllegalArgumentException("bytes must be greater than 0");
		}
		this.bytes = builder.bytes;

		if (builder.mimeType == null || builder.mimeType.isBlank()) {
			throw new IllegalArgumentException("mimeType must not be null or empty");
		}
		this.mimeType = builder.mimeType;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String filename;
		private Purpose purpose;
		private long bytes;
		private String mimeType;

		/**
		 * The name of the file to upload.
		 */
		public Builder filename(String filename) {
			this.filename = filename;
			return this;
		}

		/**
		 * The intended purpose of the uploaded file.
		 */
		public Builder purpose(Purpose purpose) {
			this.purpose = purpose;
			return this;
		}

		/**
		 * The number of bytes in the file you are uploading.
		 */
		public Builder bytes(long bytes) {
			this.bytes = bytes;
			return this;
		}

		/**
		 * The MIME type of the file.
		 * <p>
		 * This must fall within the supported MIME types for your file purpose.
		 */
		public Builder mimeType(String mimeType) {
			this.mimeType = mimeType;
			return this;
		}

		public UploadCreateRequest build() {
			return new UploadCreateRequest(this);
		}
	}

}
