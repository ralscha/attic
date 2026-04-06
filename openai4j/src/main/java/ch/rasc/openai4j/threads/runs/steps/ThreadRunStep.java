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
package ch.rasc.openai4j.threads.runs.steps;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

import ch.rasc.openai4j.common.Error;

/**
 * Represents a step in execution of a run.
 */
public record ThreadRunStep(String id, String object,
		@JsonProperty("created_at") int createdAt,
		@JsonProperty("assistant_id") String assistantId,
		@JsonProperty("thread_id") String threadId, @JsonProperty("run_id") String runId,
		ThreadRunStepType type, ThreadRunStepStatus status,
		@JsonProperty("step_details") StepDetail stepDetails,
		@JsonProperty("last_error") Error lastError,
		@JsonProperty("expired_at") Integer expiredAt,
		@JsonProperty("cancelled_at") Integer cancelledAt,
		@JsonProperty("failed_at") Integer failedAt,
		@JsonProperty("completed_at") Integer completedAt, Map<String, String> metadata,
		Usage usage) {

	/**
	 * The identifier of the run step, which can be referenced in API endpoints.
	 */
	@Override
	public String id() {
		return this.id;
	}

	/**
	 * The object type, which is always thread.run.step.
	 */
	@Override
	public String object() {
		return this.object;
	}

	/**
	 * The Unix timestamp (in seconds) for when the run step was created.
	 */
	@Override
	public int createdAt() {
		return this.createdAt;
	}

	/**
	 * The ID of the assistant associated with the run step.
	 */
	@Override
	public String assistantId() {
		return this.assistantId;
	}

	/**
	 * The ID of the thread that was run.
	 */
	@Override
	public String threadId() {
		return this.threadId;
	}

	/**
	 * The ID of the run that this run step is a part of.
	 */
	@Override
	public String runId() {
		return this.runId;
	}

	/**
	 * The type of run step, which can be either message_creation or tool_calls.
	 */
	@Override
	public ThreadRunStepType type() {
		return this.type;
	}

	/**
	 * The status of the run step, which can be either in_progress, cancelled, failed,
	 * completed, or expired.
	 */
	@Override
	public ThreadRunStepStatus status() {
		return this.status;
	}

	/**
	 * The details of the run step.
	 */
	@Override
	public StepDetail stepDetails() {
		return this.stepDetails;
	}

	/**
	 * The last error associated with this run step. Will be null if there are no errors.
	 */
	@Override
	public Error lastError() {
		return this.lastError;
	}

	/**
	 * The Unix timestamp (in seconds) for when the run step expired. A step is considered
	 * expired if the parent run is expired.
	 */
	@Override
	public Integer expiredAt() {
		return this.expiredAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the run step was cancelled.
	 */
	@Override
	public Integer cancelledAt() {
		return this.cancelledAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the run step failed.
	 */
	@Override
	public Integer failedAt() {
		return this.failedAt;
	}

	/**
	 * The Unix timestamp (in seconds) for when the run step completed.
	 */
	@Override
	public Integer completedAt() {
		return this.completedAt;
	}

	/**
	 * Set of 16 key-value pairs that can be attached to an object. This can be useful for
	 * storing additional information about the object in a structured format. Keys can be
	 * a maximum of 64 characters long and values can be a maxium of 512 characters long.
	 */
	@Override
	public Map<String, String> metadata() {
		return this.metadata;
	}

	/**
	 * Usage statistics related to the run step. This value will be null while the run
	 * step's status is in_progress.
	 */
	@Override
	public Usage usage() {
		return this.usage;
	}

	public enum ThreadRunStepType {
		MESSAGE_CREATION("message_creation"), TOOL_CALLS("tool_calls");

		private final String value;

		ThreadRunStepType(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	public enum ThreadRunStepStatus {
		IN_PROGRESS("in_progress"), CANCELLED("cancelled"), FAILED("failed"),
		COMPLETED("completed"), EXPIRED("expired");

		private final String value;

		ThreadRunStepStatus(String value) {
			this.value = value;
		}

		@JsonValue
		public String value() {
			return this.value;
		}
	}

	@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
	@JsonSubTypes({
			@Type(value = MessageCreationStepDetails.class, name = "message_creation"),
			@Type(value = ToolCallsStepDetails.class, name = "tool_calls") })
	public interface StepDetail {
	}

	record MessageCreationStepDetails(String type,
			@JsonProperty("message_creation") MessageCreation messageCreation)
			implements StepDetail {

		/**
		 * Always <code>message_creation</code>.
		 */
		@Override
		public String type() {
			return this.type;
		}

		/**
		 * The ID of the message that was created by this run step.
		 */
		@Override
		public MessageCreation messageCreation() {
			return this.messageCreation;
		}

		record MessageCreation(@JsonProperty("message_id") String messageId) {
			/**
			 * The ID of the message that was created by this run step.
			 */
			@Override
			public String messageId() {
				return this.messageId;
			}
		}
	}

	record ToolCallsStepDetails(String type,
			@JsonProperty("tool_calls") List<ToolCall> toolCalls) implements StepDetail {

		/**
		 * Always <code>tool_calls</code>.
		 */
		@Override
		public String type() {
			return this.type;
		}

		/**
		 * An array of tool calls the run step was involved in. These can be associated
		 * with one of three types of tools: code_interpreter, file_search, or function.
		 */
		@Override
		public List<ToolCall> toolCalls() {
			return this.toolCalls;
		}

		@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
		@JsonSubTypes({
				@Type(value = CodeInterpreterCall.class, name = "code_interpreter"),
				@Type(value = FileSearchCall.class, name = "file_search"),
				@Type(value = FunctionToolCall.class, name = "function") })
		public interface ToolCall {
		}

		record CodeInterpreterCall(String id, String type,
				@JsonProperty("code_interpreter") CodeInterpreter codeInterpreter)
				implements ToolCall {

			/**
			 * The ID of the tool call.
			 */
			@Override
			public String id() {
				return this.id;
			}

			/**
			 * The type of tool call. This is always going to be code_interpreter for this
			 * type of tool call.
			 */
			@Override
			public String type() {
				return this.type;
			}

			/**
			 * The Code Interpreter tool call definition.
			 */
			@Override
			public CodeInterpreter codeInterpreter() {
				return this.codeInterpreter;
			}

			record CodeInterpreter(String input, List<CodeInterpreterOutput> outputs) {

				/**
				 * The input to the Code Interpreter tool call.
				 */
				@Override
				public String input() {
					return this.input;
				}

				/**
				 * The outputs from the Code Interpreter tool call. Code Interpreter can
				 * output one or more items, including text (logs) or images (image). Each
				 * of these are represented by a different object type.
				 */
				@Override
				public List<CodeInterpreterOutput> outputs() {
					return this.outputs;
				}

				@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type",
						visible = true)
				@JsonSubTypes({
						@Type(value = ImageCodeInterpreterOutput.class, name = "image"),
						@Type(value = LogCodeInterpreterOutput.class, name = "logs") })
				public interface CodeInterpreterOutput {
				}

				record ImageCodeInterpreterOutput(String type, Image image)
						implements CodeInterpreterOutput {
					/**
					 * Always image.
					 */
					@Override
					public String type() {
						return this.type;
					}

					/**
					 * The file ID of the image.
					 */
					@Override
					public Image image() {
						return this.image;
					}
				}

				record Image(@JsonProperty("file_id") String fileId) {
					/**
					 * The file ID of the image.
					 */
					@Override
					public String fileId() {
						return this.fileId;
					}
				}

				record LogCodeInterpreterOutput(String type, String logs)
						implements CodeInterpreterOutput {
					/**
					 * Always logs.
					 */
					@Override
					public String type() {
						return this.type;
					}

					/**
					 * The text output from the Code Interpreter tool call.
					 */
					@Override
					public String logs() {
						return this.logs;
					}
				}
			}
		}

		record FileSearchCall(String id, String type,
				@JsonProperty("file_search") Map<String, Object> fileSearch)
				implements ToolCall {
			/**
			 * The ID of the tool call object.
			 */
			@Override
			public String id() {
				return this.id;
			}

			/**
			 * The type of tool call. This is always going to be file_search for this type
			 * of tool call.
			 */
			@Override
			public String type() {
				return this.type;
			}

			/**
			 * For now, this is always going to be an empty object.
			 */
			@Override
			public Map<String, Object> fileSearch() {
				return this.fileSearch;
			}
		}

		record FunctionToolCall(String id, String type, Function function)
				implements ToolCall {

			/**
			 * The ID of the tool call object.
			 */
			@Override
			public String id() {
				return this.id;
			}

			/**
			 * The type of tool call. This is always going to be function for this type of
			 * tool call.
			 */
			@Override
			public String type() {
				return this.type;
			}

			/**
			 * The definition of the function that was called.
			 */
			@Override
			public Function function() {
				return this.function;
			}

			record Function(String name, String arguments, String output) {
				/**
				 * The name of the function.
				 */
				@Override
				public String name() {
					return this.name;
				}

				/**
				 * The arguments passed to the function.
				 */
				@Override
				public String arguments() {
					return this.arguments;
				}

				/**
				 * The output of the function. This will be null if the outputs have not
				 * been submitted yet.
				 */
				@Override
				public String output() {
					return this.output;
				}
			}
		}

	}

	public record Usage(@JsonProperty("completion_tokens") int completionTokens,
			@JsonProperty("prompt_tokens") int promptTokens,
			@JsonProperty("total_tokens") int totalTokens) {
		/**
		 * Number of completion tokens used over the course of the run step.
		 */
		@Override
		public int completionTokens() {
			return this.completionTokens;
		}

		/**
		 * Number of prompt tokens used over the course of the run step.
		 */
		@Override
		public int promptTokens() {
			return this.promptTokens;
		}

		/**
		 * Total number of tokens used (prompt + completion).
		 */
		@Override
		public int totalTokens() {
			return this.totalTokens;
		}

	}

}
