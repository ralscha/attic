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

import java.io.File;
import java.util.function.Function;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ImagesClient {

	/**
	 * Creates an image given a prompt.
	 *
	 * @return Returns a list of image objects.
	 */
	@RequestLine("POST /images/generations")
	@Headers("Content-Type: application/json")
	ImageResponse generate(ImageGenerationRequest request);

	/**
	 * Creates an image given a prompt.
	 *
	 * @return Returns a list of image objects.
	 */
	default ImageResponse generate(
			Function<ImageGenerationRequest.Builder, ImageGenerationRequest.Builder> fn) {
		return this.generate(fn.apply(ImageGenerationRequest.builder()).build());
	}

	/**
	 * Creates an edited or extended image given an original image and a prompt.
	 *
	 * @return Returns a list of image objects.
	 */
	default ImageResponse edit(
			Function<ImageEditRequest.Builder, ImageEditRequest.Builder> fn) {
		return this.edit(fn.apply(ImageEditRequest.builder()).build());
	}

	/**
	 * Creates an edited or extended image given an original image and a prompt.
	 *
	 * @return Returns a list of image objects.
	 */
	default ImageResponse edit(ImageEditRequest request) {
		return this.edit(request.image().toFile(), request.prompt(),
				request.mask() != null ? request.mask().toFile() : null,
				request.model() != null ? request.model().value() : null, request.n(),
				request.size() != null ? request.size().value() : null,
				request.responseFormat() != null ? request.responseFormat().toValue()
						: null,
				request.user());
	}

	/**
	 * Creates an edited or extended image given an original image and a prompt.
	 *
	 * @return Returns a list of image objects.
	 */
	@RequestLine("POST /images/edits")
	@Headers("Content-Type: multipart/form-data")
	ImageResponse edit(@Param("image") File image, @Param("prompt") String prompt,
			@Param("mask") File mask, @Param("model") String model, @Param("n") Integer n,
			@Param("size") String size, @Param("response_format") String responseFormat,
			@Param("user") String user);

	/**
	 * Creates a variation of a given image.
	 *
	 * @return Returns a list of image objects.
	 */
	default ImageResponse createVariation(
			Function<ImageVariationRequest.Builder, ImageVariationRequest.Builder> fn) {
		return this.createVariation(fn.apply(ImageVariationRequest.builder()).build());
	}

	/**
	 * Creates a variation of a given image.
	 *
	 * @return Returns a list of image objects.
	 */
	default ImageResponse createVariation(ImageVariationRequest request) {
		return this.createVariation(request.image().toFile(),
				request.model() != null ? request.model().value() : null, request.n(),
				request.responseFormat() != null ? request.responseFormat().toValue()
						: null,
				request.size() != null ? request.size().value() : null, request.user());
	}

	/**
	 * Creates a variation of a given image.
	 *
	 * @return Returns a list of image objects.
	 */
	@RequestLine("POST /images/variations")
	@Headers("Content-Type: multipart/form-data")
	ImageResponse createVariation(@Param("image") File image,
			@Param("model") String model, @Param("n") Integer n,
			@Param("response_format") String responseFormat, @Param("size") String size,
			@Param("user") String user);
}
