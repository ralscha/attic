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
package ch.rasc.openai4j.finetuningjobs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import ch.rasc.openai4j.common.Error;

/**
 * The fine_tuning.job object represents a fine-tuning job that has been created through
 * the API.
 */
public record FineTuningJob(String id, @JsonProperty("created_at") int createdAt,
		Error error, @JsonProperty("fine_tuned_model") String fineTunedModel,
		@JsonProperty("finished_at") Integer finishedAt, HyperParameters hyperparameters,
		String model, String object,
		@JsonProperty("organization_id") String organizationId,
		@JsonProperty("result_files") List<String> resultFiles, Status status,
		@JsonProperty("trained_tokens") Integer trainedTokens,
		@JsonProperty("training_file") String trainingFile,
		@JsonProperty("validation_file") String validationFile, Integer seed) {

	/**
	 * The object identifier, which can be referenced in the API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The Unix timestamp (in seconds) for when the fine-tuning job was created.
	 */
	@Override
	public int createdAt() {
		return this.createdAt;
	}

	/**
	 * For fine-tuning jobs that have failed, this will contain more information on the
	 * cause of the failure.
	 */
	@Override
	public Error error() {
		return this.error;
	}

	/**
	 * The name of the fine-tuned model that is being created. The value will be null if
	 * the fine-tuning job is still running.
	 */
	@Override
	public String fineTunedModel() {
		return this.fineTunedModel;
	}

	/**
	 * The Unix timestamp (in seconds) for when the fine-tuning job was finished. The
	 * value will be null if the fine-tuning job is still running.
	 */
	@Override
	public Integer finishedAt() {
		return this.finishedAt;
	}

	/**
	 * The hyperparameters used for the fine-tuning job.
	 */
	@Override
	public HyperParameters hyperparameters() {
		return this.hyperparameters;
	}

	/**
	 * The base model that is being fine-tuned.
	 */
	@Override
	public String model() {
		return this.model;
	}

	/**
	 * The object type, which is always "fine_tuning.job".
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The organization that owns the fine-tuning job.
	 */
	@Override
	public String organizationId() {
		return this.organizationId;
	}

	/**
	 * The compiled results file ID(s) for the fine-tuning job. You can retrieve the
	 * results with the Files API.
	 */
	@Override
	public List<String> resultFiles() {
		return this.resultFiles;
	}

	/**
	 * The current status of the fine-tuning job.
	 */
	@Override
	public Status status() {
		return this.status;
	}

	/**
	 * The total number of billable tokens processed by this fine-tuning job. The value
	 * will be null if the fine-tuning job is still running.
	 */
	@Override
	public Integer trainedTokens() {
		return this.trainedTokens;
	}

	/**
	 * The file ID used for training. You can retrieve the training data with the Files
	 * API.
	 */
	@Override
	public String trainingFile() {
		return this.trainingFile;
	}

	/**
	 * The file ID used for validation. You can retrieve the validation results with the
	 * Files API.
	 */
	@Override
	public String validationFile() {
		return this.validationFile;
	}

	/**
	 * The seed used for the fine-tuning job.
	 */
	@Override
	public Integer seed() {
		return this.seed;
	}

	public enum Status {
		VALIDATING_FILES("validating_files"), QUEUED("queued"), RUNNING("running"),
		SUCCEEDED("succeeded"), FAILED("failed"), CANCELLED("cancelled");

		private final String value;

		Status(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	public record HyperParameters(@JsonProperty("n_epochs") Object nEpochs,
			@JsonProperty("batch_size") Object batchSize,
			@JsonProperty("learning_rate_multiplier") Object learningRateMultiplier) {

		/**
		 * The number of epochs to train the model for. An epoch refers to one full cycle
		 * through the training dataset.
		 */
		@Override
		public Object nEpochs() {
			return this.nEpochs;
		}

		/**
		 * Number of examples in each batch.
		 */
		@Override
		public Object batchSize() {
			return this.batchSize;
		}

		/**
		 * Scaling factor for the learning rate.
		 */
		@Override
		public Object learningRateMultiplier() {
			return this.learningRateMultiplier;
		}
	}

}
