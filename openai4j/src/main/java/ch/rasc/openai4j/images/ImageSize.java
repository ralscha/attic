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

import com.fasterxml.jackson.annotation.JsonValue;

public enum ImageSize {
	S_256("256x256"), S_512("512x512"), S_1024("1024x1024"), S_1792("1792x1024"),
	S_1024_1792("1024x1792");

	private final String value;

	ImageSize(String value) {
		this.value = value;
	}

	@JsonValue
	public String value() {
		return this.value;
	}
}