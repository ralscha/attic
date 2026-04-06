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
package ch.rasc.openai4j.threads;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public record TextMessageContent(String type, Text text) implements MessageContent {

	/**
	 * Always text.
	 */
	@Override
	public String type() {
		return this.type;
	}

	@Override
	public Text text() {
		return this.text;
	}

	public record Text(String value, List<Annotation> annotations) {

		/**
		 * The data that makes up the text.
		 */
		@Override
		public String value() {
			return this.value;
		}

		@Override
		public List<Annotation> annotations() {
			return this.annotations;
		}

		@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
		@JsonSubTypes({ @Type(value = FileCitation.class, name = "file_citation"),
				@Type(value = FilePath.class, name = "file_path") })
		public interface Annotation {
		}

		/**
		 * A citation within the message that points to a specific quote from a specific
		 * File associated with the assistant or the message. Generated when the assistant
		 * uses the "retrieval" tool to search files.
		 */
		public record FileCitation(String type, String text,
				@JsonProperty("file_citation") Citation fileCitation,
				@JsonProperty("start_index") int startIndex,
				@JsonProperty("end_index") int endIndex) implements Annotation {

			/**
			 * Always file_citation.
			 */
			@Override
			public String type() {
				return this.type;
			}

			/**
			 * The text in the message content that needs to be replaced.
			 */
			@Override
			public String text() {
				return this.text;
			}

			@Override
			public Citation fileCitation() {
				return this.fileCitation;
			}

			@Override
			public int startIndex() {
				return this.startIndex;
			}

			@Override
			public int endIndex() {
				return this.endIndex;
			}

		}

		public record Citation(@JsonProperty("file_id") String fileId, String quote) {
			/**
			 * The ID of the specific File the citation is from.
			 */
			@Override
			public String fileId() {
				return this.fileId;
			}

			/**
			 * The specific quote in the file.
			 */
			@Override
			public String quote() {
				return this.quote;
			}
		}

		public record FilePath(String type, String text,
				@JsonProperty("file_path") File filePath,
				@JsonProperty("start_index") int startIndex,
				@JsonProperty("end_index") int endIndex) implements Annotation {
			/**
			 * Always file_path.
			 */
			@Override
			public String type() {
				return this.type;
			}

			/**
			 * The text in the message content that needs to be replaced.
			 */
			@Override
			public String text() {
				return this.text;
			}

			@Override
			public File filePath() {
				return this.filePath;
			}

			@Override
			public int startIndex() {
				return this.startIndex;
			}

			@Override
			public int endIndex() {
				return this.endIndex;
			}
		}
	}
}