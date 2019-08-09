package ch.rasc.golb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndCategoryImpl;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;

import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.entity.CPost;
import ch.rasc.golb.entity.CTag;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.entity.Tag;
import ch.rasc.golb.feed.AtomNSModule;
import ch.rasc.golb.feed.AtomNSModuleImpl;
import ch.rasc.golb.feed.CustomFeedEntry;
import ch.rasc.golb.feed.CustomSyndEntry;
import ch.rasc.golb.util.QueryUtil;

@RestController
public class FeedService {

	private final MongoDb mongoDb;

	public FeedService(MongoDb mongoDb) {
		this.mongoDb = mongoDb;
	}

	@GetMapping(path = "/rss2")
	public Channel rss2() {
		return (Channel) createWireFeed("rss_2.0");
	}

	@GetMapping(path = "/atom1")
	public Feed atom1() {
		return (Feed) createWireFeed("atom_1.0");
	}

	private WireFeed createWireFeed(String feedType) {

		MongoCollection<Post> collection = this.mongoDb.getCollection(Post.class);
		List<Post> posts = QueryUtil
				.toList(collection.find(Filters.exists(CPost.published))
						.sort(Sorts.descending(CPost.published))
						.projection(Projections.include(CPost.title, CPost.summary,
								CPost.slug, CPost.updated, CPost.published, CPost.tags)));

		SyndFeed feed;
		if (feedType.equals("rss_2.0")) {
			feed = new CustomFeedEntry();
		}
		else {
			feed = new SyndFeedImpl();
		}

		feed.setFeedType(feedType);

		feed.setTitle("My Blog");
		feed.setDescription("Blog about this and that");
		feed.setLink("https://www.myblog.com/");
		feed.setAuthor("The Author");
		feed.setUri("https://www.myblog.com/");

		AtomNSModule atomNSModule = new AtomNSModuleImpl();
		String link = feedType.equals("rss_2.0") ? "/rss2" : "/atom1";
		atomNSModule.setLink("https://www.myblog.com" + link);
		feed.getModules().add(atomNSModule);

		Date latestPublishedDate = null;

		List<SyndEntry> entries = new ArrayList<>();
		for (Post post : posts) {
			SyndEntry entry;
			if (feedType.equals("rss_2.0")) {
				entry = new CustomSyndEntry();
			}
			else {
				entry = new SyndEntryImpl();
			}
			entry.setTitle(post.getTitle());
			entry.setAuthor("The Author");
			entry.setLink("https://www.myblog.com/p/" + post.getSlug());
			entry.setUri("https://www.myblog.com/p/" + post.getSlug());
			entry.setPublishedDate(post.getPublished());
			entry.setUpdatedDate(post.getUpdated());

			if (latestPublishedDate == null
					|| post.getPublished().getTime() > latestPublishedDate.getTime()) {
				latestPublishedDate = post.getPublished();
			}

			List<SyndCategory> categories = new ArrayList<>();
			if (post.getTags() != null) {
				for (String tagId : post.getTags()) {
					Tag tagEntity = this.mongoDb.findFirst(Tag.class, CTag.id, tagId);
					if (tagEntity != null) {
						SyndCategory category = new SyndCategoryImpl();
						// category.setTaxonomyUri(tagId);
						category.setName(tagEntity.getName());
						categories.add(category);
					}
				}
			}

			if (!categories.isEmpty()) {
				entry.setCategories(categories);
			}

			if (StringUtils.hasText(post.getSummary())) {
				SyndContent description = new SyndContentImpl();
				description.setType("text/plain");
				description.setValue(post.getSummary());
				entry.setDescription(description);
			}
			else {
				SyndContent description = new SyndContentImpl();
				description.setType("text/plain");
				description.setValue(post.getTitle());
				entry.setDescription(description);

			}

			entries.add(entry);
		}

		if (latestPublishedDate != null) {
			feed.setPublishedDate(latestPublishedDate);
		}

		feed.setEntries(entries);
		return feed.createWireFeed();
	}
}
