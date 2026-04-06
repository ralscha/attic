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

import java.nio.file.Path;

@SuppressWarnings("hiding")
public class ImageEditRequest {

	private final Path image;
	private final String prompt;
	private final Path mask;
	private final ImageModel model;
	private final Integer n;
	private final ImageSize size;
	private final ImageResponseFormat responseFormat;
	private final String user;

	private ImageEditRequest(Builder builder) {
		if (builder.image == null) {
			throw new IllegalArgumentException("image cannot be null");
		}
		if (builder.prompt == null || builder.prompt.isEmpty()) {
			throw new IllegalArgumentException("prompt cannot be null");
		}
		this.image = builder.image;
		this.prompt = builder.prompt;
		this.mask = builder.mask;
		this.model = builder.model;
		this.n = builder.n;
		this.size = builder.size;
		this.responseFormat = builder.responseFormat;
		this.user = builder.user;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Path image;
		private String prompt;
		private Path mask;
		private ImageModel model;
		private Integer n;
		private ImageSize size;
		private ImageResponseFormat responseFormat;
		private String user;

		private Builder() {
		}

		/**
		 * The image to edit. Must be a valid PNG file, less than 4MB, and square. If mask
		 * is not provided, image must have transparency, which will be used as the mask.
		 */
		public Builder image(Path image) {
			this.image = image;
			return this;
		}

		/**
		 * A text description of the desired image(s). The maximum length is 1000
		 * characters.
		 */
		public Builder prompt(String prompt) {
			this.prompt = prompt;
			return this;
		}

		/**
		 * An additional image whose fully transparent areas (e.g. where alpha is zero)
		 * indicate where image should be edited. Must be a valid PNG file, less than 4MB,
		 * and have the same dimensions as image.
		 */
		public Builder mask(Path mask) {
			this.mask = mask;
			return this;
		}

		/**
		 * The model to use for image generation. Only dall-e-2 is supported at this time.
		 * Defaults to dall-e-2.
		 */
		public Builder model(ImageModel model) {
			this.model = model;
			return this;
		}

		/**
		 * The number of images to generate. Must be between 1 and 10. Defaults to 1.
		 */
		public Builder n(Integer n) {
			this.n = n;
			return this;
		}

		/**
		 * The size of the generated images. Must be one of 256x256, 512x512, or
		 * 1024x1024. Defaults to 1024x1024.
		 */
		public Builder size(ImageSize size) {
			this.size = size;
			return this;
		}

		/**
		 * The format in which the generated images are returned. Must be one of url or
		 * b64_json. URLs are only valid for 60 minutes after the image has been
		 * generated. Defaults to url.
		 */
		public Builder responseFormat(ImageResponseFormat responseFormat) {
			this.responseFormat = responseFormat;
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

		public ImageEditRequest build() {
			return new ImageEditRequest(this);
		}
	}

	public Path image() {
		return this.image;
	}

	public String prompt() {
		return this.prompt;
	}

	public Path mask() {
		return this.mask;
	}

	public ImageModel model() {
		return this.model;
	}

	public Integer n() {
		return this.n;
	}

	public ImageSize size() {
		return this.size;
	}

	public ImageResponseFormat responseFormat() {
		return this.responseFormat;
	}

	public String user() {
		return this.user;
	}
}
