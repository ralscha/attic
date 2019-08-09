package ch.rasc.golb.pub;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.entity.CPost;
import ch.rasc.golb.entity.Feedback;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.util.Util;

@Controller
public class FeedbackController {

	private final MongoDb mongoDb;

	private final Template feedbackTemplate;
	private final Template feedbackOkTemplate;

	public FeedbackController(MongoDb mongoDb, Mustache.Compiler mustacheCompiler)
			throws IOException {
		this.mongoDb = mongoDb;

		ClassPathResource cpr = new ClassPathResource("/templates/feedback.mustache");
		try (InputStream is = cpr.getInputStream();
				InputStreamReader isr = new InputStreamReader(is,
						StandardCharsets.UTF_8);) {
			this.feedbackTemplate = mustacheCompiler.compile(isr);
		}

		cpr = new ClassPathResource("/templates/feedback_ok.mustache");
		try (InputStream is = cpr.getInputStream();
				InputStreamReader isr = new InputStreamReader(is,
						StandardCharsets.UTF_8);) {
			this.feedbackOkTemplate = mustacheCompiler.compile(isr);
		}
	}

	@PostMapping("/submitFeedback")
	public ResponseEntity<?> submitFeedback(
			@RequestParam(name = "slug", required = false) String slug,
			@RequestParam(name = "feedback", required = false) String feedbackStr,
			@RequestParam(name = "email", required = false) String email) {

		if (StringUtils.hasText(feedbackStr) && StringUtils.hasText(slug)) {
			Post post = this.mongoDb.findFirst(Post.class, CPost.slug, slug);
			if (post != null) {
				Feedback feedback = new Feedback();
				feedback.setCreated(new Date());
				feedback.setBody(feedbackStr);
				feedback.setReplyEmailAddress(email);
				feedback.setPostId(post.getId());
				this.mongoDb.getCollection(Feedback.class).insertOne(feedback);
			}
		}

		String feedbackOkHtml = this.feedbackOkTemplate.execute(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setExpires(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
		headers.set("X-XSS-Protection", "1; mode=block");
		headers.set("X-Content-Type-Options", "nosniff");
		headers.set("X-Frame-Options", "DENY");
		headers.set("Content-Security-Policy", "default-src 'self'; object-src 'none'");
		return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
				.eTag(Util.generateEtag(feedbackOkHtml)).headers(headers)
				.cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic())
				.body(feedbackOkHtml);
	}

	@GetMapping("/feedback/{slug}")
	public ResponseEntity<?> feedback(@PathVariable("slug") String slug) {

		MongoCollection<Post> collection = this.mongoDb.getCollection(Post.class);
		final Post post;

		if (StringUtils.hasText(slug)) {
			post = collection.find(Filters.eq(CPost.slug, slug))
					.projection(Projections.include(CPost.slug, CPost.title)).first();
		}
		else {
			post = null;
		}

		String feedbackHtml = this.feedbackTemplate.execute(new Object() {
			@SuppressWarnings({ "unused" })
			String postTitle = post != null ? post.getTitle() : "";
			@SuppressWarnings({ "unused" })
			String postSlug = post != null ? post.getSlug() : "";
		});

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-XSS-Protection", "1; mode=block");
		headers.set("X-Content-Type-Options", "nosniff");
		headers.set("X-Frame-Options", "DENY");
		headers.set("Content-Security-Policy", "default-src 'self'; object-src 'none'");
		return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
				.cacheControl(CacheControl.noCache()).body(feedbackHtml);

	}
}
