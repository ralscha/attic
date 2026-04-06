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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class UploadCompleteRequest {

	@JsonProperty("part_ids")
	private final List<String> partIds;
	private final String md5;

	private UploadCompleteRequest(Builder builder) {
		if (builder.partIds == null || builder.partIds.isEmpty()) {
			throw new IllegalArgumentException("partIds must not be null or empty");
		}
		this.partIds = builder.partIds;
		this.md5 = builder.md5;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<String> partIds;
		private String md5;

		/**
		 * The ordered list of Part IDs.
		 */
		public Builder partIds(List<String> partIds) {
			this.partIds = new ArrayList<>(partIds);
			return this;
		}

		public Builder addPartId(String... partId) {
			if (this.partIds == null) {
				this.partIds = new ArrayList<>();
			}
			this.partIds.addAll(List.of(partId));
			return this;
		}

		/**
		 * The optional md5 checksum for the file contents to verify if the bytes uploaded
		 * matches what you expect.
		 */
		public Builder md5(String md5) {
			this.md5 = md5;
			return this;
		}

		public UploadCompleteRequest build() {
			return new UploadCompleteRequest(this);
		}
	}

}
