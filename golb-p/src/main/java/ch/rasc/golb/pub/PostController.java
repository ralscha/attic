package ch.rasc.golb.pub;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.entity.CPost;
import ch.rasc.golb.entity.CTag;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.entity.Tag;

@Controller
public class PostController {

	final MongoDb mongoDb;

	private final Template postTemplate;

	public PostController(MongoDb mongoDb, Mustache.Compiler mustacheCompiler)
			throws IOException {
		this.mongoDb = mongoDb;

		ClassPathResource cpr = new ClassPathResource("/templates/post.mustache");
		try (InputStream is = cpr.getInputStream();
				InputStreamReader isr = new InputStreamReader(is,
						StandardCharsets.UTF_8);) {
			this.postTemplate = mustacheCompiler.withFormatter(new Mustache.Formatter() {
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

	@GetMapping("/p/{slug}")
	public ResponseEntity<?> fetchPost(@PathVariable("slug") String slug) {

		Post postEntity = this.mongoDb.getCollection(Post.class)
				.find(Filters.and(Filters.eq(CPost.slug, slug),
						Filters.exists(CPost.published)))
				.projection(Projections.include(CPost.bodyHtml, CPost.title, CPost.slug,
						CPost.published, CPost.updated, CPost.tags))
				.first();
		if (postEntity != null) {
			String postHtml = this.postTemplate.execute(new Object() {
				@SuppressWarnings("unused")
				String body = postEntity.getBodyHtml();
				@SuppressWarnings("unused")
				String title = postEntity.getTitle();
				@SuppressWarnings({ "unused", "hiding" })
				String slug = postEntity.getSlug();
				@SuppressWarnings("unused")
				List<String> tags = postEntity.getTags();
				@SuppressWarnings("unused")
				Date published = postEntity.getPublished();
				@SuppressWarnings("unused")
				Mustache.Lambda tagName = (frag, out) -> {
					String tagId = frag.execute();
					Tag tagEntity = PostController.this.mongoDb.findFirst(Tag.class,
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
					.lastModified(postEntity.getUpdated().getTime())
					.cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS).cachePublic())
					.body(postHtml);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/preview/{slug}")
	public ResponseEntity<?> previewPost(@PathVariable("slug") String slug) {

		Post postEntity = this.mongoDb.getCollection(Post.class)
				.find(Filters.eq(CPost.slug, slug))
				.projection(Projections.include(CPost.bodyHtml, CPost.title, CPost.slug,
						CPost.published, CPost.updated, CPost.tags))
				.first();
		if (postEntity != null) {
			String postHtml = this.postTemplate.execute(new Object() {
				@SuppressWarnings("unused")
				String body = postEntity.getBodyHtml();
				@SuppressWarnings("unused")
				String title = postEntity.getTitle();
				@SuppressWarnings({ "unused", "hiding" })
				String slug = postEntity.getSlug();
				@SuppressWarnings("unused")
				List<String> tags = postEntity.getTags();
				@SuppressWarnings("unused")
				Date published = new Date();
				@SuppressWarnings("unused")
				Mustache.Lambda tagName = (frag, out) -> {
					String tagId = frag.execute();
					Tag tagEntity = PostController.this.mongoDb.findFirst(Tag.class,
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
			headers.set("X-XSS-Protection", "1; mode=block");
			headers.set("X-Content-Type-Options", "nosniff");
			headers.set("X-Frame-Options", "DENY");
			headers.set("Content-Security-Policy",
					"default-src 'self'; object-src 'none'");
			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(postHtml);
		}
		return ResponseEntity.notFound().build();
	}

}
