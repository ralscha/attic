package ch.rasc.messenger.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.messenger.MongoDb;
import ch.rasc.messenger.domain.Location;
import ch.rasc.messenger.domain.Message;
import ch.rasc.messenger.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageControllerTests {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(
			"target/generated-snippets");

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private MongoDb mongoDb;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;
	private RestDocumentationResultHandler document;

	@Before
	public void setUp() {
		this.mongoDb.getCollection(User.class).drop();
		this.mongoDb.getCollection(Message.class).drop();

		this.document = MockMvcRestDocumentation.document("{method-name}",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()));

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(MockMvcRestDocumentation
						.documentationConfiguration(this.restDocumentation))
				.alwaysDo(this.document).build();
	}

	@Test
	public void listMessages() throws Exception {
		createUser();

		Message msg1 = createMessage("foo", "swhite", null);
		Message msg2 = createMessage("bar", "swhite", new Location(0.0, 0.0));

		this.document.snippets(
				responseFields(fieldWithPath("[].id").description("The message ID"),
						fieldWithPath("[].content").description("The message content"),
						fieldWithPath("[].author")
								.description("The message author username"),
						fieldWithPath("[].location").optional().description(
								"Optional, the message location (latitude, longitude)")));

		MvcResult result = this.mockMvc
				.perform(get("/message").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andReturn();
		List<Message> msgs = this.objectMapper.readValue(
				result.getResponse().getContentAsString(),
				new TypeReference<List<Message>>() {
					// nothing here
				});

		assertThat(msgs).containsExactly(msg1, msg2);
	}

	@Test
	public void createMessage() throws JsonProcessingException, Exception {
		createUser();

		this.document.snippets(
				requestFields(fieldWithPath("content").description("The message content"),
						fieldWithPath("author")
								.description("The message author username"),
						fieldWithPath("location").optional().description(
								"Optional, the message location (latitude, longitude)")),
				responseFields(fieldWithPath("id").description("The message ID"),
						fieldWithPath("content").description("The message content"),
						fieldWithPath("author")
								.description("The message author username"),
						fieldWithPath("location").optional().description(
								"Optional, the message location (latitude, longitude)")));

		Message msg = new Message();
		msg.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
		msg.setAuthor("swhite");
		msg.setLocation(new Location(0.0, 0.0));

		this.mockMvc
				.perform(post("/message")
						.content(this.objectMapper.writeValueAsString(msg))
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());

		Message after = this.mongoDb.getCollection(Message.class).find().first();
		assertThat(after.getId()).isNotEmpty();
		after.setId(null); // ignore id
		assertThat(after).isEqualTo(msg);
	}

	@Test
	public void findMessagesByBoundingBox() throws Exception {
		createUser();
		Message msg1 = createMessage("foo", "swhite", new Location(0.0, 0.0));
		Message msg2 = createMessage("bar", "swhite", new Location(1.0, 1.0));
		this.document.snippets(
				pathParameters(
						parameterWithName("xMin")
								.description("The latitude of the lower-left corner"),
						parameterWithName("yMin")
								.description("The longitude of the lower-left corner"),
						parameterWithName("xMax")
								.description("The latitude of the upper-left corner"),
						parameterWithName("yMax")
								.description("The longitude of the upper-left corner")),
				responseFields(fieldWithPath("[].id").description("The message ID"),
						fieldWithPath("[].content").description("The message content"),
						fieldWithPath("[].author")
								.description("The message author username"),
						fieldWithPath("[].location").optional().description(
								"Optional, the message location (latitude, longitude)")));

		MvcResult result = this.mockMvc
				.perform(get("/message/bbox/{xMin},{yMin},{xMax},{yMax}", -1, -1, 2, 2)
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andReturn();

		List<Message> msgs = this.objectMapper.readValue(
				result.getResponse().getContentAsString(),
				new TypeReference<List<Message>>() {
					// nothing here
				});
		assertThat(msgs).containsExactly(msg1, msg2);

		result = this.mockMvc
				.perform(get("/message/bbox/{xMin},{yMin},{xMax},{yMax}", 1, 1, 1, 1)
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andReturn();

		msgs = this.objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<Message>>() {
					// nothing here
				});
		assertThat(msgs).containsExactly(msg2);

	}

	@Test
	public void subscribeMessages() throws Exception {
		this.mockMvc.perform(get("/message/subscribe")).andExpect(status().isOk());
	}

	private Message createMessage(String content, String author, Location location) {
		Message msg = new Message();
		msg.setContent(content);
		msg.setAuthor(author);
		msg.setLocation(location);
		this.mongoDb.getCollection(Message.class).insertOne(msg);
		return msg;
	}

	private void createUser() {
		User skyler = new User();
		skyler.setUserName("swhite");
		skyler.setFirstName("Skyler");
		skyler.setLastName("White");
		this.mongoDb.getCollection(User.class).insertOne(skyler);
	}
}
