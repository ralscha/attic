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
package ch.rasc.openai4j.audio;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

@SuppressWarnings("hiding")
public class AudioTranscriptionRequest {

	private final Path file;
	private final AudioRecognitionModel model;
	private final String language;
	private final String prompt;
	private final AudioRecognitionResponseFormat responseFormat;
	private final Double temperature;
	private final List<TimestampGranularity> timestampGranularities;

	public enum TimestampGranularity {
		WORD("word"), SEGMENT("segment");

		private final String value;

		TimestampGranularity(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	private AudioTranscriptionRequest(Builder builder) {
		if (builder.file == null) {
			throw new IllegalArgumentException("file must not be null");
		}
		if (builder.model == null) {
			throw new IllegalArgumentException("model must not be null");
		}

		if (builder.timestampGranularities != null
				&& builder.timestampGranularities.size() > 0
				&& builder.responseFormat != AudioRecognitionResponseFormat.VERBOSE_JSON) {
			throw new IllegalArgumentException(
					"responseFormat must be set to verbose_json if timestampGranularities is set");
		}

		this.file = builder.file;
		this.model = builder.model;
		this.language = builder.language;
		this.prompt = builder.prompt;
		this.responseFormat = builder.responseFormat;
		this.temperature = builder.temperature;
		this.timestampGranularities = builder.timestampGranularities;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Path file;
		private AudioRecognitionModel model;
		private String language;
		private String prompt;
		private AudioRecognitionResponseFormat responseFormat;
		private Double temperature;
		private List<TimestampGranularity> timestampGranularities;

		private Builder() {
		}

		/**
		 * The audio file object to transcribe, in one of these formats: flac, mp3, mp4,
		 * mpeg, mpga, m4a, ogg, wav, or webm.
		 */
		public Builder file(Path file) {
			this.file = file;
			return this;
		}

		/**
		 * ID of the model to use. Only whisper-1 (which is powered by Whisper V2 model)
		 * is currently available.
		 */
		public Builder model(AudioRecognitionModel model) {
			this.model = model;
			return this;
		}

		/**
		 * The language of the input audio. Supplying the input language in ISO-639-1
		 * format will improve accuracy and latency.
		 */
		public Builder language(String language) {
			this.language = language;
			return this;
		}

		/**
		 * An optional text to guide the model's style or continue a previous audio
		 * segment. The prompt should match the audio language.
		 */
		public Builder prompt(String prompt) {
			this.prompt = prompt;
			return this;
		}

		/**
		 * The format of the transcript output. Defaults to json.
		 */
		public Builder responseFormat(AudioRecognitionResponseFormat responseFormat) {
			this.responseFormat = responseFormat;
			return this;
		}

		/**
		 * The sampling temperature, between 0 and 1. Higher values like 0.8 will make the
		 * output more random, while lower values like 0.2 will make it more focused and
		 * deterministic. If set to 0, the model will use log probability to automatically
		 * increase the temperature until certain thresholds are hit.
		 * <p>
		 * Defaults to 0.
		 */
		public Builder temperature(Double temperature) {
			this.temperature = temperature;
			return this;
		}

		/**
		 * The timestamp granularities to populate for this transcription. response_format
		 * must be set verbose_json to use timestamp granularities. Either or both of
		 * these options are supported: word, or segment. Note: There is no additional
		 * latency for segment timestamps, but generating word timestamps incurs
		 * additional latency.
		 */
		public Builder addTimestampGranularities(
				TimestampGranularity... timestampGranularities) {
			if (this.timestampGranularities == null) {
				this.timestampGranularities = new ArrayList<>();
			}
			this.timestampGranularities.addAll(List.of(timestampGranularities));
			this.timestampGranularities = this.timestampGranularities.stream().distinct()
					.toList();
			return this;
		}

		public AudioTranscriptionRequest build() {
			return new AudioTranscriptionRequest(this);
		}
	}

	public Path file() {
		return this.file;
	}

	public AudioRecognitionModel model() {
		return this.model;
	}

	public String language() {
		return this.language;
	}

	public String prompt() {
		return this.prompt;
	}

	public AudioRecognitionResponseFormat responseFormat() {
		return this.responseFormat;
	}

	public Double temperature() {
		return this.temperature;
	}

	public List<TimestampGranularity> timestampGranularities() {
		return this.timestampGranularities;
	}

}
