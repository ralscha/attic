package ch.rasc.openai4j.example.assistants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.Configuration;
import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.assistants.CodeInterpreterTool;
import ch.rasc.openai4j.assistants.FunctionTool;
import ch.rasc.openai4j.common.FunctionParameters;
import ch.rasc.openai4j.common.JsonSchemaService;
import ch.rasc.openai4j.common.SortOrder;
import ch.rasc.openai4j.example.Util;
import ch.rasc.openai4j.threads.TextMessageContent;
import ch.rasc.openai4j.threads.messages.ThreadMessage;
import ch.rasc.openai4j.threads.runs.ThreadRun.Status;
import ch.rasc.openai4j.threads.runs.ToolOutput;

public class AssistantsToolQuizExample {
	public static void main(String[] args)
			throws JsonMappingException, JsonProcessingException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(Configuration.builder().apiKey(apiKey).build());

		var assistants = client.assistants.list();
		for (var a : assistants.data()) {
			client.assistants.delete(a.id());
		}

		JsonSchemaService jsonSchemaService = new JsonSchemaService();
		var jsonSchema = jsonSchemaService.generateStrictSchema(DisplayQuiz.class);

		var assistant = client.assistants.create(r -> r.name("Math Tutor")
				.instructions("You are a personal math tutor")
				.addTools(CodeInterpreterTool.of(), FunctionTool.of(FunctionParameters.of(
						"display_quiz",
						"Displays a quiz to the student, and returns the student's response. A single quiz can have multiple questions.",
						jsonSchema, true)))
				.model("gpt-4o"));

		String userMessage = "Make a quiz with 2 questions: One open ended, one multiple choice. Then, give me feedback for the responses.";
		var thread = client.threads.create();
		client.threadsMessages.create(thread.id(),
				r -> r.userRole().content(userMessage));

		var run = client.threadsRuns.create(thread.id(),
				r -> r.assistantId(assistant.id()));
		run = client.threadsRuns.waitForProcessing(run);

		if (run.status() == Status.REQUIRES_ACTION) {
			List<ToolOutput> outputs = new ArrayList<>();
			for (var toolCall : run.requiredAction().submitToolOutputs().toolCalls()) {
				String arguments = toolCall.function().arguments();
				ObjectMapper om = new ObjectMapper();
				om.findAndRegisterModules();
				DisplayQuiz dq = om.readValue(arguments, DisplayQuiz.class);
				List<String> userResponse = displayQuiz(dq);
				String userResponseString = om.writeValueAsString(userResponse);
				ToolOutput output = new ToolOutput(toolCall.id(), userResponseString);
				outputs.add(output);
			}
			run = client.threadsRuns.submitToolOutputs(thread.id(), run.id(),
					r -> r.toolOutputs(outputs));
			run = client.threadsRuns.waitForProcessing(run);

			var messages = client.threadsMessages.list(run.threadId(),
					r -> r.order(SortOrder.ASC));
			prettyPrint(messages.data());

		}
		else {
			System.out.println("Something went wrong: " + run);
		}

	}

	private static void prettyPrint(List<ThreadMessage> messages) {
		System.out.println("# Messages");
		for (var msg : messages) {
			var content = msg.content().get(0);
			if (content instanceof TextMessageContent text) {
				System.out.println(msg.role() + ": " + text.text().value());
			}
		}
		System.out.println();
	}

	record DisplayQuiz(@JsonProperty(required = true) String title,
			@JsonPropertyDescription("An array of questions, each with a title and potentially options (if multiple choice).") @JsonProperty(
					required = true) List<Question> questions) {

	}

	private static List<String> displayQuiz(DisplayQuiz displayQuiz) {
		System.out.println("Quiz: " + displayQuiz.title());
		System.out.println();
		List<String> responses = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);

		for (Question q : displayQuiz.questions()) {
			System.out.println(q.questionText());
			String response = "";

			// If multiple choice, print options
			if (q.questionType() == QuestionType.MULTIPLE_CHOICE
					&& q.choices().isPresent()) {
				List<String> choices = q.choices().get();
				for (int i = 0; i < choices.size(); i++) {
					System.out.println(i + ". " + choices.get(i));
				}
				response = scanner.nextLine();
				try {
					int responseInt = Integer.parseInt(response);
					if (responseInt >= 0 && responseInt < choices.size()) {
						response = choices.get(responseInt);
					}
				}
				catch (NumberFormatException e) {
					// do nothing
				}
			}

			// Otherwise, just get response
			else if (q.questionType() == QuestionType.FREE_RESPONSE) {
				response = scanner.nextLine();
			}

			responses.add(response);
			System.out.println();
		}

		return responses;
	}

	private enum QuestionType {
		MULTIPLE_CHOICE, FREE_RESPONSE
	}

	// Question class as a record
	private record Question(@JsonProperty(required = true) String questionText,
			@JsonProperty(required = true) QuestionType questionType,
			@JsonProperty(required = true) Optional<List<String>> choices) {
	}
}
