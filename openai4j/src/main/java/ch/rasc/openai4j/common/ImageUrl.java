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
package ch.rasc.openai4j.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageUrl {

	private final String url;
	private final ImageDetail detail;

	public ImageUrl(String url, ImageDetail detail) {
		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException("url cannot be null");
		}
		this.url = url;
		this.detail = detail;
	}

	/**
	 * Either a URL of the image or the base64 encoded image data.
	 * <p>
	 * The external URL of the image, must be a supported image types: jpeg, jpg, png,
	 * gif, webp
	 */
	@JsonProperty
	public String url() {
		return this.url;
	}

	/**
	 * Specifies the detail level of the image. low uses fewer tokens, you can opt in to
	 * high resolution using high. Default value is auto
	 */
	@JsonProperty
	public ImageDetail detail() {
		return this.detail;
	}
}