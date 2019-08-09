package ch.rasc.golb.pub;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.redfin.sitemapgenerator.WebSitemapGenerator;

import ch.rasc.golb.Application;
import ch.rasc.golb.config.AppProperties;
import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.entity.CPost;
import ch.rasc.golb.entity.Post;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Controller
public class SitemapController {

	private final MongoDb mongoDb;

	private final AppProperties appProperties;

	public SitemapController(MongoDb mongoDb, AppProperties appProperties) {
		this.mongoDb = mongoDb;
		this.appProperties = appProperties;
	}

	@GetMapping("/sitemap.xml")
	public ResponseEntity<String> serveSiteIndex() throws MalformedURLException {
		WebSitemapGenerator wsg = new WebSitemapGenerator(this.appProperties.getUrl());
		wsg.addUrl(this.appProperties.getUrl() + "/index.html");

		for (Post post : this.mongoDb.getCollection(Post.class)
				.find(Filters.exists(CPost.published))
				.projection(Projections.include(CPost.slug))) {
			wsg.addUrl(this.appProperties.getUrl() + "/p/" + post.getSlug());
		}

		String result = wsg.writeAsStrings().stream().collect(Collectors.joining("\n"));
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML)
				.cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS).cachePublic())
				.body(result);

	}

	public void pingSearchEngines() {
		String sitemapUrl = this.appProperties.getUrl() + "/sitemap.xml";
		OkHttpClient httpClient = new OkHttpClient();

		HttpUrl googlePingUrl = new HttpUrl.Builder().scheme("https").host("google.com")
				.addPathSegment("ping").addQueryParameter("sitemap", sitemapUrl).build();

		HttpUrl bingPingUrl = new HttpUrl.Builder().scheme("https").host("www.bing.com")
				.addPathSegment("webmaster").addPathSegment("ping.aspx")
				.addQueryParameter("siteMap", sitemapUrl).build();

		ping(httpClient, googlePingUrl);
		ping(httpClient, bingPingUrl);
	}

	private static void ping(OkHttpClient httpClient, HttpUrl pingUrl) {
		Request request = new Request.Builder().url(pingUrl).build();
		httpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Application.logger.error("sitemap controller", e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {
					Application.logger.error("Unexpected code " + response);
				}
			}
		});
	}

}
