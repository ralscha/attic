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

import java.util.List;

/**
 * Represents policy compliance report by OpenAI's content moderation model against a
 * given input.
 */
public record ModerationCreateResponse(String id, String model,
		List<Moderations> results) {

	/**
	 * The unique identifier for the moderation request.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The model used to generate the moderation results.
	 */
	@Override
	public String model() {
		return this.model;
	}

	/**
	 * A list of moderation objects.
	 */
	@Override
	public List<Moderations> results() {
		return this.results;
	}

}
