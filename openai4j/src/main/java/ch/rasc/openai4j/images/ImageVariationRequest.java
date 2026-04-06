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
public class ImageVariationRequest {

	private final Path image;
	private final ImageModel model;
	private final Integer n;
	private final ImageResponseFormat responseFormat;
	private final ImageSize size;
	private final String user;

	private ImageVariationRequest(Builder builder) {
		if (builder.image == null) {
			throw new IllegalArgumentException("image cannot be null");
		}
		this.image = builder.image;
		this.model = builder.model;
		this.n = builder.n;
		this.responseFormat = builder.responseFormat;
		this.size = builder.size;
		this.user = builder.user;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Path image;
		private ImageModel model;
		private Integer n;
		private ImageResponseFormat responseFormat;
		private ImageSize size;
		private String user;

		private Builder() {
		}

		/**
		 * The image to use as the basis for the variation(s). Must be a valid PNG file,
		 * less than 4MB, and square.
		 */
		public Builder image(Path image) {
			this.image = image;
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
		 * The number of images to generate. Must be between 1 and 10. For dall-e-3, only
		 * n=1 is supported. Defaults to 1
		 */
		public Builder n(Integer n) {
			this.n = n;
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
		 * The size of the generated images. Must be one of 256x256, 512x512, or
		 * 1024x1024. Defaults to 1024x1024.
		 */
		public Builder size(ImageSize size) {
			this.size = size;
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

		public ImageVariationRequest build() {
			return new ImageVariationRequest(this);
		}
	}

	public Path image() {
		return this.image;
	}

	public ImageModel model() {
		return this.model;
	}

	public Integer n() {
		return this.n;
	}

	public ImageResponseFormat responseFormat() {
		return this.responseFormat;
	}

	public ImageSize size() {
		return this.size;
	}

	public String user() {
		return this.user;
	}
}
