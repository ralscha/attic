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
package ch.rasc.openai4j.images;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@SuppressWarnings({ "unused", "hiding" })
public class ImageGenerationRequest {

	private final String prompt;
	private final ImageModel model;
	private final Integer n;
	private final Quality quality;
	@JsonProperty("response_format")
	private final ImageResponseFormat responseFormat;
	private final ImageSize size;
	private final Style style;
	private final String user;

	private ImageGenerationRequest(Builder builder) {
		if (builder.prompt == null || builder.prompt.isEmpty()) {
			throw new IllegalArgumentException("prompt cannot be null");
		}

		this.prompt = builder.prompt;
		this.model = builder.model;
		this.n = builder.n;
		this.quality = builder.quality;
		this.responseFormat = builder.responseFormat;
		this.size = builder.size;
		this.style = builder.style;
		this.user = builder.user;
	}

	public enum Quality {
		STANDARD("standard"), HD("hd");

		private final String value;

		Quality(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	public enum Style {
		VIVID("vivid"), NATURAL("natural");

		private final String value;

		Style(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String prompt;
		private ImageModel model;
		private Integer n;
		private Quality quality;
		private ImageResponseFormat responseFormat;
		private ImageSize size;
		private Style style;
		private String user;

		private Builder() {
		}

		/**
		 * A text description of the desired image(s). The maximum length is 1000
		 * characters for dall-e-2 and 4000 characters for dall-e-3.
		 */
		public Builder prompt(String prompt) {
			this.prompt = prompt;
			return this;
		}

		/**
		 * The model to use for image generation. Defaults to dall-e-2
		 */
		public Builder model(ImageModel model) {
			this.model = model;
			return this;
		}

		/**
		 * The number of images to generate. Must be between 1 and 10. For dall-e-3, only
		 * n=1 is supported. Defaults to 1
		 */
		public Builder n(Integer n) {
			this.n = n;
			return this;
		}

		/**
		 * The quality of the image that will be generated. hd creates images with finer
		 * details and greater consistency across the image. This param is only supported
		 * for dall-e-3. Defaults to standard
		 */
		public Builder quality(Quality quality) {
			this.quality = quality;
			return this;
		}

		/**
		 * The format in which the generated images are returned. Must be one of url or
		 * b64_json. URLs are only valid for 60 minutes after the image has been
		 * generated.
		 */
		public Builder responseFormat(ImageResponseFormat responseFormat) {
			this.responseFormat = responseFormat;
			return this;
		}

		/**
		 * The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024
		 * for dall-e-2. Must be one of 1024x1024, 1792x1024, or 1024x1792 for dall-e-3
		 * models. Defaults to 1024x1024
		 */
		public Builder size(ImageSize size) {
			this.size = size;
			return this;
		}

		/**
		 * The style of the generated images. Must be one of vivid or natural. Vivid
		 * causes the model to lean towards generating hyper-real and dramatic images.
		 * Natural causes the model to produce more natural, less hyper-real looking
		 * images. This param is only supported for dall-e-3. Defaults to vivid
		 */
		public Builder style(Style style) {
			this.style = style;
			return this;
		}

		/**
		 * A unique identifier representing your end-user, which can help OpenAI to
		 * monitor and detect abuse.
		 */
		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public ImageGenerationRequest build() {
			return new ImageGenerationRequest(this);
		}
	}
}
