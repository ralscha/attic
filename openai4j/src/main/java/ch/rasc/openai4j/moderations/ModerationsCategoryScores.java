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

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModerationsCategoryScores(Double hate,
		@JsonProperty("hate/threatening") Double hateThreatening, Double harassment,
		@JsonProperty("harassment/threatening") Double harassmentThreatening,
		@JsonProperty("self-harm") Double selfHarm,
		@JsonProperty("self-harm/intent") Double selfHarmIntent,
		@JsonProperty("self-harm/instructions") Double selfHarmInstructions,
		Double sexual, @JsonProperty("sexual/minors") Double sexualMinors,
		Double violence, @JsonProperty("violence/graphic") Double violenceGraphic) {

	/**
	 * The score for the category 'hate'.
	 */
	@Override
	public Double hate() {
		return this.hate;
	}

	/**
	 * The score for the category 'hate/threatening'.
	 */
	@Override
	public Double hateThreatening() {
		return this.hateThreatening;
	}

	/**
	 * The score for the category 'harassment'.
	 */
	@Override
	public Double harassment() {
		return this.harassment;
	}

	/**
	 * The score for the category 'harassment/threatening'.
	 */
	@Override
	public Double harassmentThreatening() {
		return this.harassmentThreatening;
	}

	/**
	 * The score for the category 'self-harm'.
	 */
	@Override
	public Double selfHarm() {
		return this.selfHarm;
	}

	/**
	 * The score for the category 'self-harm/intent'.
	 */
	@Override
	public Double selfHarmIntent() {
		return this.selfHarmIntent;
	}

	/**
	 * The score for the category 'self-harm/instructions'.
	 */
	@Override
	public Double selfHarmInstructions() {
		return this.selfHarmInstructions;
	}

	/**
	 * The score for the category 'sexual'.
	 */
	@Override
	public Double sexual() {
		return this.sexual;
	}

	/**
	 * The score for the category 'sexual/minors'.
	 */
	@Override
	public Double sexualMinors() {
		return this.sexualMinors;
	}

	/**
	 * The score for the category 'violence'.
	 */
	@Override
	public Double violence() {
		return this.violence;
	}

	/**
	 * The score for the category 'violence/graphic'.
	 */
	@Override
	public Double violenceGraphic() {
		return this.violenceGraphic;
	}

}
