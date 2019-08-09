package ch.rasc.golb.pub;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.entity.CPost;
import ch.rasc.golb.entity.CTag;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.entity.Tag;
import ch.rasc.golb.util.QueryUtil;
import ch.rasc.golb.util.Util;

@Controller
public class IndexController {

	final MongoDb mongoDb;

	private final Template indexTemplate;

	public IndexController(MongoDb mongoDb, Mustache.Compiler mustacheCompiler)
			throws IOException {
		this.mongoDb = mongoDb;

		ClassPathResource cpr = new ClassPathResource("/templates/index.mustache");
		try (InputStream is = cpr.getInputStream();
				InputStreamReader isr = new InputStreamReader(is,
						StandardCharsets.UTF_8);) {
			this.indexTemplate = mustacheCompiler.withFormatter(new Mustache.Formatter() {
				@Override
				public String format(Object value) {
					if (value instanceof Date) {
						return this._fmt.format((Date) value);
					}
					return String.valueOf(value);
				}

				protected DateFormat _fmt = new SimpleDateFormat("MMMM dd, yyyy");
			}).compile(isr);
		}
	}

	@GetMapping({ "/", "/index.html" })
	public ResponseEntity<?> index(
			@RequestParam(name = "tag", required = false) String tag) {

		MongoCollection<Post> collection = this.mongoDb.getCollection(Post.class);
		FindIterable<Post> find;

		if (StringUtils.hasText(tag)) {
			List<String> tagIds = Arrays.stream(tag.split(",")).map(name -> {
				try {
					return this.mongoDb.findFirst(Tag.class, CTag.name, name).getId();
				}
				catch (Exception e) {
					return "";
				}
			}).collect(Collectors.toList());
			find = collection.find(Filters.and(Filters.exists(CPost.published),
					Filters.in(CPost.tags, tagIds)));
		}
		else {
			find = collection.find(Filters.exists(CPost.published));
		}
		List<Post> postsList = QueryUtil
				.toList(find.sort(Sorts.descending(CPost.published))
						.projection(Projections.include(CPost.bodyHtml, CPost.title,
								CPost.slug, CPost.summary, CPost.published, CPost.tags)));

		if (postsList != null) {
			String indexHtml = this.indexTemplate.execute(new Object() {
				@SuppressWarnings("unused")
				String query = tag;
				@SuppressWarnings("unused")
				List<Post> posts = postsList;

				@SuppressWarnings("unused")
				Mustache.Lambda tagName = (frag, out) -> {
					String tagId = frag.execute();
					Tag tagEntity = IndexController.this.mongoDb.findFirst(Tag.class,
							CTag.id, tagId);
					if (tagEntity != null) {
						out.write(tagEntity.getName());
					}
					else {
						out.write("???");
					}
				};
			});

			HttpHeaders headers = new HttpHeaders();
			headers.setExpires(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3));
			headers.set("X-XSS-Protection", "1; mode=block");
			headers.set("X-Content-Type-Options", "nosniff");
			headers.set("X-Frame-Options", "DENY");
			headers.set("Content-Security-Policy",
					"default-src 'self'; object-src 'none'");
			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
					.eTag(Util.generateEtag(indexHtml)).headers(headers)
					.cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS).cachePublic())
					.body(indexHtml);
		}
		return ResponseEntity.notFound().build();
	}
}
