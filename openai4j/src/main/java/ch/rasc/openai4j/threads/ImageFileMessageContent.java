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

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.rasc.openai4j.common.ImageFile;

public record ImageFileMessageContent(String type,
		@JsonProperty("image_file") ImageFile imageFile) implements MessageContent {

	/**
	 * Always image_file.
	 */
	@Override
	public String type() {
		return this.type;
	}

	@Override
	public ImageFile imageFile() {
		return this.imageFile;
	}

}