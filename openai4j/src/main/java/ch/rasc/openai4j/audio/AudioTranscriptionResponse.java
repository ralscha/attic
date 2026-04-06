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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AudioTranscriptionResponse(String language, String duration, String text,
		List<Word> words, List<Segment> segments) {

	public record Word(String word, double start, double end) {

		/**
		 * The text content of the word.
		 */
		@Override
		public String word() {
			return this.word;
		}

		/**
		 * Start time of the word in seconds.
		 */
		@Override
		public double start() {
			return this.start;
		}

		/**
		 * End time of the word in seconds.
		 */
		@Override
		public double end() {
			return this.end;
		}
	}

	public record Segment(int id, int seek, double start, double end, String text,
			List<Integer> tokens, double temperature,
			@JsonProperty("avg_logprob") double avgLogprob,
			@JsonProperty("compression_ratio") double compressionRatio,
			@JsonProperty("no_speech_prob") double noSpeechProb) {

		/**
		 * Unique identifier of the segment.
		 */
		@Override
		public int id() {
			return this.id;
		}

		/**
		 * Seek offset of the segment.
		 */
		@Override
		public int seek() {
			return this.seek;
		}

		/**
		 * Start time of the segment in seconds.
		 */
		@Override
		public double start() {
			return this.start;
		}

		/**
		 * End time of the segment in seconds.
		 */
		@Override
		public double end() {
			return this.end;
		}

		/**
		 * Text content of the segment.
		 */
		public String text() {
			return this.text;
		}

		/**
		 * Array of token IDs for the text content.
		 */
		@Override
		public List<Integer> tokens() {
			return this.tokens;
		}

		/**
		 * Temperature parameter used for generating the segment.
		 */
		@Override
		public double temperature() {
			return this.temperature;
		}

		/**
		 * Average logprob of the segment. If the value is lower than -1, consider the
		 * logprobs failed.
		 */
		@Override
		public double avgLogprob() {
			return this.avgLogprob;
		}

		/**
		 * Compression ratio of the segment. If the value is greater than 2.4, consider
		 * the compression failed.
		 */
		@Override
		public double compressionRatio() {
			return this.compressionRatio;
		}

		/**
		 * Probability of no speech in the segment. If the value is higher than 1.0 and
		 * the avg_logprob is below -1, consider this segment silent.
		 */
		@Override
		public double noSpeechProb() {
			return this.noSpeechProb;
		}
	}

	/**
	 * The language of the input audio.
	 */
	@Override
	public String language() {
		return this.language;
	}

	/**
	 * The duration of the input audio.
	 */
	@Override
	public String duration() {
		return this.duration;
	}

	/**
	 * The transcribed text.
	 */
	@Override
	public String text() {
		return this.text;
	}

	/**
	 * Extracted words and their corresponding timestamps.
	 */
	@Override
	public List<Word> words() {
		return this.words;
	}

	/**
	 * Segments of the transcribed text and their corresponding details.
	 */
	@Override
	public List<Segment> segments() {
		return this.segments;
	}

}
