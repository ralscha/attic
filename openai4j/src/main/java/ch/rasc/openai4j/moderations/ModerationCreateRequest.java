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
package ch.rasc.openai4j.moderations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class ModerationCreateRequest {

	private final String input;
	private final ModerationModel model;

	private ModerationCreateRequest(Builder builder) {
		if (builder.input == null || builder.input.isBlank()) {
			throw new IllegalArgumentException("input is required");
		}
		this.input = builder.input;
		this.model = builder.model;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String input;
		private ModerationModel model;

		private Builder() {
		}

		/**
		 * The input text to classify
		 */
		public Builder input(String input) {
			this.input = input;
			return this;
		}

		/**
		 * Two content moderations models are available: text-moderation-stable and
		 * text-moderation-latest.
		 * <p>
		 * The default is text-moderation-latest which will be automatically upgraded over
		 * time. This ensures you are always using our most accurate model. If you use
		 * text-moderation-stable, we will provide advanced notice before updating the
		 * model. Accuracy of text-moderation-stable may be slightly lower than for
		 * text-moderation-latest
		 */
		public Builder model(ModerationModel model) {
			this.model = model;
			return this;
		}

		public ModerationCreateRequest build() {
			return new ModerationCreateRequest(this);
		}
	}
}
