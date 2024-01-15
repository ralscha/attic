package ch.rasc.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.todo.config.Db;
import ch.rasc.todo.dto.CrudResponse;
import ch.rasc.todo.entity.QTodo;
import ch.rasc.todo.entity.Todo;
import ch.rasc.todo.entity.Type;
import net.coobird.thumbnailator.Thumbnails;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class TodoServiceTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private Db db;

	@Before
	public void setup() {
		this.restTemplate.exchange("/todo", HttpMethod.DELETE, null, Void.class);
	}

	@Test
	public void create() throws IOException {
		Todo newTodo = createTodo();
		ResponseEntity<CrudResponse<Todo>> response = this.restTemplate.exchange("/todo",
				HttpMethod.POST, new HttpEntity<>(newTodo),
				new ParameterizedTypeReference<CrudResponse<Todo>>() {
					/* nothing_here */});
		Todo insertedTodo = response.getBody().getRoot().iterator().next();

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(insertedTodo.getId()).isNotNull();

		compareWithDatabase(insertedTodo);
	}

	@Test
	public void read() throws IOException {
		Todo newTodo = createTodo();
		ResponseEntity<CrudResponse<Todo>> response = this.restTemplate.exchange("/todo",
				HttpMethod.POST, new HttpEntity<>(newTodo),
				new ParameterizedTypeReference<CrudResponse<Todo>>() {
					/* nothing_here */});
		Todo insertedTodo = response.getBody().getRoot().iterator().next();

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		response = this.restTemplate.exchange("/todo", HttpMethod.GET, null,
				new ParameterizedTypeReference<CrudResponse<Todo>>() {
					/* nothing_here */});
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getFieldErrors()).isNull();
		assertThat(response.getBody().getTotal()).isEqualTo(1);
		assertThat(response.getBody().getMessage()).isNull();

		List<Todo> todos = response.getBody().getRoot();
		assertThat(todos).containsExactly(insertedTodo);
	}

	@Test
	public void update() throws IOException {
		Todo newTodo = createTodo();
		ResponseEntity<CrudResponse<Todo>> response = this.restTemplate.exchange("/todo",
				HttpMethod.POST, new HttpEntity<>(newTodo),
				new ParameterizedTypeReference<CrudResponse<Todo>>() {
					/* nothing_here */});
		Todo insertedTodo = response.getBody().getRoot().iterator().next();
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		insertedTodo.setTitle("new title");
		response = this.restTemplate.exchange("/todo/" + insertedTodo.getId(),
				HttpMethod.PUT, new HttpEntity<>(insertedTodo),
				new ParameterizedTypeReference<CrudResponse<Todo>>() {
					/* nothing_here */});
		Todo updatedTodo = response.getBody().getRoot().iterator().next();
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		assertThat(insertedTodo).isEqualToIgnoringGivenFields(updatedTodo, "title");
		assertThat(updatedTodo.getTitle()).isEqualTo("new title");

		compareWithDatabase(updatedTodo);
	}

	@Test
	public void delete() throws IOException {
		Todo newTodo = createTodo();
		ResponseEntity<CrudResponse<Todo>> response = this.restTemplate.exchange("/todo",
				HttpMethod.POST, new HttpEntity<>(newTodo),
				new ParameterizedTypeReference<CrudResponse<Todo>>() {
					/* nothing_here */});
		Todo insertedTodo = response.getBody().getRoot().iterator().next();
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		ResponseEntity<Void> deleteResponse = this.restTemplate.exchange(
				"/todo/" + insertedTodo.getId(), HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode().is2xxSuccessful()).isTrue();

		List<Todo> todos = this.db.selectFrom(QTodo.todo).fetch();
		assertThat(todos).isEmpty();
	}

	private Todo createTodo() throws IOException {
		Todo newTodo = new Todo();
		newTodo.setDue(Date.from(LocalDateTime.of(2017, 6, 23, 10, 5)
				.atZone(ZoneId.systemDefault()).toInstant()));
		newTodo.setDescription("the description");
		newTodo.setTitle("the title");
		newTodo.setType(Type.PRIVATE);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Thumbnails.of(getClass().getResourceAsStream("/images/1.jpg")).size(80, 80)
				.outputFormat("jpg").toOutputStream(baos);
		newTodo.setThumbnail(baos.toByteArray());
		return newTodo;
	}

	private void compareWithDatabase(Todo todo) {
		List<Todo> todos = this.db.selectFrom(QTodo.todo).fetch();
		assertThat(todos).hasSize(1);
		Todo dbTodo = todos.iterator().next();
		assertThat(dbTodo).isEqualToIgnoringGivenFields(todo, "due");
		assertThat(dbTodo.getDue()).isInSameDayAs(todo.getDue());
	}
}