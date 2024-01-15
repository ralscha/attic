package ch.rasc.messenger.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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
public class UserControllerTests {
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
	public void createUser() throws JsonProcessingException, Exception {
		this.document.snippets(
				requestFields(fieldWithPath("userName").description("The user username"),
						fieldWithPath("firstName").description("The user first name"),
						fieldWithPath("lastName").description("The user last name"),
						fieldWithPath("location").optional().description(
								"Optional, the user location (latitude, longitude)")));
		User skyler = new User();
		skyler.setUserName("swhite");
		skyler.setFirstName("Skyler");
		skyler.setLastName("White");
		skyler.setLocation(new Location(0.0, 0.0));

		this.mockMvc
				.perform(post("/user")
						.content(this.objectMapper.writeValueAsString(skyler))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());

		User after = this.mongoDb.getCollection(User.class).find().first();
		assertThat(after.getId()).isNotEmpty();
		after.setId(null); // ignore id
		assertThat(after).isEqualTo(skyler);
	}

	@Test
	public void listUsers() throws Exception {

		User skyler = new User();
		skyler.setUserName("swhite");
		skyler.setFirstName("Skyler");
		skyler.setLastName("White");
		this.mongoDb.getCollection(User.class).insertOne(skyler);

		User jpinkman = new User();
		jpinkman.setUserName("jpinkman");
		jpinkman.setFirstName("Jesse");
		jpinkman.setLastName("Pinkman");
		jpinkman.setLocation(new Location(0.0, 0.0));
		this.mongoDb.getCollection(User.class).insertOne(jpinkman);

		this.document.snippets(
				responseFields(fieldWithPath("[].id").description("The primary key"),
						fieldWithPath("[].userName").description("The user username"),
						fieldWithPath("[].firstName").description("The user first name"),
						fieldWithPath("[].lastName").description("The user last name"),
						fieldWithPath("[].location").optional().description(
								"Optional, the user location (latitude, longitude)")));
		MvcResult result = this.mockMvc
				.perform(get("/user").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andReturn();

		List<User> users = this.objectMapper.readValue(
				result.getResponse().getContentAsString(),
				new TypeReference<List<User>>() {
					// nothing here
				});

		assertThat(users).containsExactly(skyler, jpinkman);
	}

	@Test
	public void findUsersByBoundingBox() throws Exception {

		User skyler = new User();
		skyler.setUserName("swhite");
		skyler.setFirstName("Skyler");
		skyler.setLastName("White");
		skyler.setLocation(new Location(0.0, 0.0));
		this.mongoDb.getCollection(User.class).insertOne(skyler);

		User jpinkman = new User();
		jpinkman.setUserName("jpinkman");
		jpinkman.setFirstName("Jesse");
		jpinkman.setLastName("Pinkman");
		jpinkman.setLocation(new Location(1.0, 1.0));
		this.mongoDb.getCollection(User.class).insertOne(jpinkman);

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
				responseFields(fieldWithPath("[].id").description("The primary key"),
						fieldWithPath("[].userName").description("The user username"),
						fieldWithPath("[].firstName").description("The user first name"),
						fieldWithPath("[].lastName").description("The user last name"),
						fieldWithPath("[].location").optional().description(
								"Optional, the user location (latitude, longitude)")));
		MvcResult result = this.mockMvc
				.perform(get("/user/bbox/{xMin},{yMin},{xMax},{yMax}", -1, -1, 2, 2)
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andReturn();

		List<User> users = this.objectMapper.readValue(
				result.getResponse().getContentAsString(),
				new TypeReference<List<User>>() {
					// nothing here
				});
		assertThat(users).containsExactly(skyler, jpinkman);

		result = this.mockMvc
				.perform(get("/user/bbox/{xMin},{yMin},{xMax},{yMax}", 0, 0, 0, 0)
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andReturn();

		users = this.objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<User>>() {
					// nothing here
				});
		assertThat(users).containsExactly(skyler);

	}

	@Test
	public void updateUserLocation() throws Exception {

		User skyler = new User();
		skyler.setUserName("swhite");
		skyler.setFirstName("Skyler");
		skyler.setLastName("White");
		skyler.setLocation(new Location(0.0, 0.0));
		this.mongoDb.getCollection(User.class).insertOne(skyler);

		this.document.snippets(pathParameters(
				parameterWithName("userName").description("The user username"),
				parameterWithName("x").description("The new location latitude"),
				parameterWithName("y").description("The new location latitude")));
		this.mockMvc.perform(put("/user/{userName}/location/{x},{y}", "swhite", 1.0, 1.0))
				.andExpect(status().isNoContent());

		User after = this.mongoDb.getCollection(User.class).find().first();
		skyler.setLocation(new Location(1.0, 1.0));
		assertThat(after).isEqualTo(skyler);
	}

}
