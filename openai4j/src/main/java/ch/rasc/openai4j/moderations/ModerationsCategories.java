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

public record ModerationsCategories(boolean hate,
		@JsonProperty("hate/threatening") boolean hateThreatening, boolean harassment,
		@JsonProperty("harassment/threatening") boolean harassmentThreatening,
		@JsonProperty("self-harm") boolean selfHarm,
		@JsonProperty("self-harm/intent") boolean selfHarmIntent,
		@JsonProperty("self-harm/instructions") boolean selfHarmInstructions,
		boolean sexual, @JsonProperty("sexual/minors") boolean sexualMinors,
		boolean violence, @JsonProperty("violence/graphic") boolean violenceGraphic) {

	/**
	 * Content that expresses, incites, or promotes hate based on race, gender, ethnicity,
	 * religion, nationality, sexual orientation, disability status, or caste. Hateful
	 * content aimed at non-protected groups (e.g., chess players) is harassment.
	 */
	@Override
	public boolean hate() {
		return this.hate;
	}

	/**
	 * Hateful content that also includes violence or serious harm towards the targeted
	 * group based on race, gender, ethnicity, religion, nationality, sexual orientation,
	 * disability status, or caste.
	 */
	@Override
	public boolean hateThreatening() {
		return this.hateThreatening;
	}

	/**
	 * Content that expresses, incites, or promotes harassing language towards any target.
	 */
	@Override
	public boolean harassment() {
		return this.harassment;
	}

	/**
	 * Harassment content that also includes violence or serious harm towards any target.
	 */
	@Override
	public boolean harassmentThreatening() {
		return this.harassmentThreatening;
	}

	/**
	 * Content that promotes, encourages, or depicts acts of self-harm, such as suicide,
	 * cutting, and eating disorders.
	 */
	@Override
	public boolean selfHarm() {
		return this.selfHarm;
	}

	/**
	 * Content where the speaker expresses that they are engaging or intend to engage in
	 * acts of self-harm, such as suicide, cutting, and eating disorders.
	 */
	@Override
	public boolean selfHarmIntent() {
		return this.selfHarmIntent;
	}

	/**
	 * Content that encourages performing acts of self-harm, such as suicide, cutting, and
	 * eating disorders, or that gives instructions or advice on how to commit such acts.
	 */
	@Override
	public boolean selfHarmInstructions() {
		return this.selfHarmInstructions;
	}

	/**
	 * Content meant to arouse sexual excitement, such as the description of sexual
	 * activity, or that promotes sexual services (excluding sex education and wellness).
	 */
	@Override
	public boolean sexual() {
		return this.sexual;
	}

	/**
	 * Sexual content that includes an individual who is under 18 years old.
	 */
	@Override
	public boolean sexualMinors() {
		return this.sexualMinors;
	}

	/**
	 * Content that depicts death, violence, or physical injury.
	 */
	@Override
	public boolean violence() {
		return this.violence;
	}

	/**
	 * Content that depicts death, violence, or physical injury in graphic detail.
	 */
	@Override
	public boolean violenceGraphic() {
		return this.violenceGraphic;
	}

}
