package ch.rasc.golb.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.config.security.RequireAdminAuthority;
import ch.rasc.golb.dto.UrlCheck;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.util.QueryUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class UrlCheckService {

	private final MongoDb mongoDb;

	private final List<UrlCheck> result = Collections.synchronizedList(new ArrayList<>());

	public UrlCheckService(MongoDb mongoDb) {
		this.mongoDb = mongoDb;
	}

	@ExtDirectMethod(STORE_READ)
	@RequireAdminAuthority
	public List<UrlCheck> read() {
		return this.result;
	}

	@ExtDirectMethod
	@Async
	public void check() {
		this.result.clear();

		List<Post> posts = QueryUtil
				.toList(this.mongoDb.getCollection(Post.class).find());

		LinkExtractor linkExtractor = LinkExtractor.builder()
				.linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW)).build();

		OkHttpClient client = new OkHttpClient.Builder().followRedirects(false)
				.followSslRedirects(false).build();

		for (Post post : posts) {
			String body = post.getBody();
			Set<String> urls = new HashSet<>();
			for (LinkSpan link : linkExtractor.extractLinks(body)) {
				String url = body.substring(link.getBeginIndex(), link.getEndIndex());
				urls.add(url);
			}
			if (!urls.isEmpty()) {
				urls.parallelStream().forEach(url -> checkUrl(client, post, url));
			}
		}

	}

	private void checkUrl(OkHttpClient client, Post post, String url) {

		if (url.startsWith("http://localhost")) {
			return;
		}

		Request request = new Request.Builder().url(url).header("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
				.build();
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				UrlCheck urlCheck = new UrlCheck(url, post.getTitle(), response.code(),
						response.isSuccessful());
				this.result.add(urlCheck);
			}
		}
		catch (Exception e) {
			UrlCheck urlCheck = new UrlCheck(url, post.getTitle(), -1,
					false);
			this.result.add(urlCheck);
		}
	}

}
