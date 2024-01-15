package ch.rasc.todo.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;

import ch.rasc.todo.Application;
import ch.rasc.todo.config.AppConfig;
import ch.rasc.todo.config.Db;
import ch.rasc.todo.dto.CrudResponse;
import ch.rasc.todo.dto.EntityChangeEvent;
import ch.rasc.todo.dto.EntityChangeType;
import ch.rasc.todo.dto.ExtRequest;
import ch.rasc.todo.dto.Filter;
import ch.rasc.todo.entity.QTodo;
import ch.rasc.todo.entity.Todo;
import ch.rasc.todo.util.QuerydslUtil;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import net.coobird.thumbnailator.Thumbnails;

@RestController
@Transactional
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private QuerydslUtil queryUtil;
	
	private final Db db;

	private final ObjectMapper om;

	private final ApplicationEventPublisher publisher;

	private final AppConfig appConfig;

	public TodoController(Db db, ObjectMapper om, ApplicationEventPublisher publisher, AppConfig appConfig) {
		this.db = db;
		this.om = om;
		this.publisher = publisher;
		this.appConfig = appConfig;
	}

	@GetMapping("/images/{filename:.+}")
	public void downloadImage(@PathVariable("filename") String filename, HttpServletResponse response) {
		String user = appConfig.getSmbuser();
		String pass = appConfig.getSmbpwd();
		String path = URI.create(appConfig.getImagepath()).toString() + "/" + filename;

		try {
			if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(pass) && !StringUtils.isEmpty(path)
					&& pass.contains("smb:")) {
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", user, pass);
				SmbFile smbFile = new SmbFile(path, auth);
				try (SmbFileInputStream in = new SmbFileInputStream(smbFile)) {
					response.setContentType("image/jpeg");
					response.setBufferSize((int) smbFile.length());
					StreamUtils.copy(in, response.getOutputStream());
				}
			} else {
				Path local = Paths.get(path);
				File image = local.toFile();
				try (FileInputStream in = new FileInputStream(image)) {
					response.setContentType("image/jpeg");
					response.setBufferSize((int) image.length());
					StreamUtils.copy(in, response.getOutputStream());
				}
			}
		} catch (IOException e) {
			Application.logger.error("get todo/images/ " + filename, e);
		}
	}

	@PostMapping
	public CrudResponse<Todo> create(@RequestBody @Valid Todo newTodo, BindingResult bindingResult)
			throws JsonParseException, JsonMappingException, IOException {
		CrudResponse<Todo> resp = updateOrInsert(newTodo, bindingResult);
		Todo todo = resp.getRoot().get(0);
		todo.setThumbnail64(newTodo.getThumbnail64());
		return update(todo, bindingResult);
	}

	@GetMapping
	@Transactional(readOnly = true)
	public CrudResponse<Todo> read(@RequestHeader(value="Accept-Language") String language, ExtRequest request) {
		String filterType = null;
		String filterValue = null;

		List<Filter> filters = request.getFilterObjects(this.om);
		for (Filter filter : filters) {
			if ("filterType".equals(filter.getProperty())) {
				filterType = StringUtils.trimWhitespace(filter.getValue());
			} else if ("filterValue".equals(filter.getProperty())) {
				filterValue = StringUtils.trimWhitespace(filter.getValue());
			}
		}

		if ("fulltext".equals(filterType)) {
			return fulltextSearch(request, filterValue);
		}

		JPQLQuery<Todo> query = this.db.selectFrom(QTodo.todo);

		if (StringUtils.hasText(filterValue)) {
			if ("description".equals(filterType)) {
				query.where(QTodo.todo.description.containsIgnoreCase(filterValue));
			} else {
				query.where(QTodo.todo.title.containsIgnoreCase(filterValue));
			}
		}

		queryUtil.addPagingAndSorting(query, request, Todo.class, QTodo.todo);
		QueryResults<Todo> searchResult = query.fetchResults();
		return new CrudResponse<>(searchResult.getTotal(),
				searchResult.getResults().stream().peek(Todo::updateThumbnail64).collect(Collectors.toList()));
	}

	private CrudResponse<Todo> fulltextSearch(ExtRequest request, String filter) {
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(db.em());

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Todo.class)
				.get();

		org.apache.lucene.search.Query query;
		if (filter != null) {
			// a very basic query by keywords
			query = queryBuilder.keyword().wildcard().onFields("title", "description").matching(filter).createQuery();
		} else {
			query = queryBuilder.all().createQuery();
		}

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Todo.class);

		// Pagination
		if (request.getStart() != null && request.getLimit() != null && request.getLimit() > 0) {
			jpaQuery.setFirstResult(request.getStart()); // start from the 15th element
			jpaQuery.setMaxResults(request.getLimit()); // return 10 elements
		}

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<Todo> results = jpaQuery.getResultList();
		return new CrudResponse<>(results.size(), results.stream().peek(Todo::updateThumbnail64).collect(Collectors.toList()));
	}

	@PutMapping("/{id}")
	public CrudResponse<Todo> update(@RequestBody @Valid Todo updatedTodo, BindingResult bindingResult)
			throws JsonParseException, JsonMappingException, IOException {
		if (updatedTodo.getThumbnail64() != null) {
			String[] parts = updatedTodo.getThumbnail64().split(",");
			if (parts.length > 1) {
				String content = parts[1];
				String format = "jpg";
				parts = parts[0].split("/");
				if (parts.length > 1) {
					parts = parts[1].split(";");
					format = parts[0];
				}

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(content));
				try {
					Thumbnails.of(stream).height(200).outputFormat(format).toOutputStream(baos);
					updatedTodo.setThumbnail(baos.toByteArray());
					stream.reset();
					updatedTodo.setImageName(updatedTodo.getId() + "." + format);
					saveFile(stream, updatedTodo.getId() + "." + format);
				} catch (Exception e) {
					Application.logger.error("uploaded", e);
				}
			} else {
				byte[] imageData = this.db.select(QTodo.todo.thumbnail).from(QTodo.todo).where(QTodo.todo.eq(updatedTodo)).fetchFirst();
				updatedTodo.setThumbnail(imageData);
			}
		}
		return updateOrInsert(updatedTodo, bindingResult);

	}

	private void saveFile(InputStream stream, String fileName) {
		String user = appConfig.getSmbuser();
		String pass = appConfig.getSmbpwd();
		String path = URI.create(appConfig.getImagepath()).toString();

		try {
			if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(pass) && !StringUtils.isEmpty(path)
					&& pass.contains("smb:")) {
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, user, pass);
				SmbFile smb = new SmbFile(path + fileName, auth);
				try (SmbFileOutputStream out = new SmbFileOutputStream(smb, true)) {
					StreamUtils.copy(stream, out);
					stream.close();
				}
			} else {
				try (FileOutputStream out = new FileOutputStream(path + fileName)) {
					StreamUtils.copy(stream, out);
					stream.close();
				}
			}
		} catch (Exception e) {
			System.out.println("save uplead file --> " + e.getMessage());
		}
	}

	@DeleteMapping
	public void deleteAll() {
		this.db.delete(QTodo.todo).execute();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") long id) {
		Todo todo = this.db.em().find(Todo.class, id);
		if (todo != null) {
			this.publisher.publishEvent(EntityChangeEvent.of(EntityChangeType.DELETE, "author", todo));
			this.db.em().remove(todo);
		}
	}

	private CrudResponse<Todo> updateOrInsert(Todo changedTodo, BindingResult bindingResult)
			throws JsonParseException, JsonMappingException, IOException {
		if (!bindingResult.hasErrors()) {
			Todo merged = this.db.em().merge(changedTodo);					
			if (changedTodo.getId() < 0) {
				this.publisher.publishEvent(EntityChangeEvent.of(EntityChangeType.INSERT, "author", merged));
			} else {
				this.publisher.publishEvent(EntityChangeEvent.of(EntityChangeType.UPDATE, "author", merged));
			}
			merged.updateThumbnail64();
			return new CrudResponse<>(merged);
		}

		CrudResponse<Todo> result = new CrudResponse<>(changedTodo);
		result.setFieldErrors(bindingResult.getFieldErrors());
		result.setSuccess(false);
		return result;
	}
}
