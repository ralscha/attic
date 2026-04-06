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

import java.io.File;
import java.util.List;
import java.util.function.Function;

import ch.rasc.openai4j.audio.AudioTranscriptionRequest.TimestampGranularity;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface AudioClient {

	/**
	 * Generates audio from the input text.
	 *
	 * @return The audio file content.
	 */
	@RequestLine("POST /audio/speech")
	@Headers("Content-Type: application/json")
	Response create(AudioSpeechRequest request);

	/**
	 * Generates audio from the input text.
	 *
	 * @return The audio file content.
	 */
	default Response create(
			Function<AudioSpeechRequest.Builder, AudioSpeechRequest.Builder> fn) {
		return this.create(fn.apply(AudioSpeechRequest.builder()).build());
	}

	/**
	 * Transcribes audio into the input language.
	 *
	 * @return The transcribed text.
	 */
	default AudioTranscriptionResponse transcriptionsCreate(
			Function<AudioTranscriptionRequest.Builder, AudioTranscriptionRequest.Builder> fn) {
		return this.transcriptionsCreate(
				fn.apply(AudioTranscriptionRequest.builder()).build());
	}

	/**
	 * Transcribes audio into the input language.
	 *
	 * @return The transcribed text.
	 */
	default AudioTranscriptionResponse transcriptionsCreate(
			AudioTranscriptionRequest request) {

		return this.transcriptionsCreate(request.file().toFile(), request.model().value(),
				request.language(), request.prompt(),
				request.responseFormat() != null ? request.responseFormat().value()
						: null,
				request.temperature(),
				request.timestampGranularities() != null
						? request.timestampGranularities().stream()
								.map(TimestampGranularity::value).toList()
						: null);
	}

	/**
	 * Transcribes audio into the input language.
	 *
	 * @return The transcribed text.
	 */
	@RequestLine("POST /audio/transcriptions")
	@Headers("Content-Type: multipart/form-data")
	AudioTranscriptionResponse transcriptionsCreate(@Param("file") File file,
			@Param("model") String model, @Param("language") String language,
			@Param("prompt") String prompt,
			@Param("response_format") String responseFormat,
			@Param("temperature") Double temperature,
			@Param("timestamp_granularities[]") List<String> timestampGranularities);

	/**
	 * Translates audio into English.
	 *
	 * @return The translated text.
	 */
	default AudioTranslationResponse translationsCreate(
			Function<AudioTranslationRequest.Builder, AudioTranslationRequest.Builder> fn) {
		return this
				.translationsCreate(fn.apply(AudioTranslationRequest.builder()).build());
	}

	/**
	 * Translates audio into English.
	 *
	 * @return The translated text.
	 */
	default AudioTranslationResponse translationsCreate(AudioTranslationRequest request) {
		return this.translationsCreate(request.file().toFile(), request.model().value(),
				request.prompt(),
				request.responseFormat() != null ? request.responseFormat().value()
						: null,
				request.temperature());
	}

	/**
	 * Translates audio into English.
	 *
	 * @return The translated text.
	 */
	@RequestLine("POST /audio/translations")
	@Headers("Content-Type: multipart/form-data")
	AudioTranslationResponse translationsCreate(@Param("file") File file,
			@Param("model") String model, @Param("prompt") String prompt,
			@Param("response_format") String responseFormat,
			@Param("temperature") Double temperature);

}
