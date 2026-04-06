package ch.rasc.springmongodb.fulltext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@Configuration
@EnableMongoRepositories
public class Main extends AbstractMongoConfiguration {

	// http://spring.io/blog/2014/07/17/text-search-your-documents-with-spring-data-mongodb

	public static void main(String[] args)
			throws IOException, NumberFormatException, XMLStreamException {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
		MongoTemplate mongoTemplate = ctx.getBean(MongoTemplate.class);
		// PostRepository postRepo = ctx.getBean(PostRepository.class);

		if (mongoTemplate.count(null, Post.class) == 0) {
			readDataIntoMongoDb(mongoTemplate);
		}

		// Search
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matching("hops");
		Query query = TextQuery.queryText(criteria).sortByScore()
				.with(new PageRequest(0, 5));

		List<Post> posts = mongoTemplate.find(query, Post.class);
		for (Post post : posts) {
			System.out.println("SCORE: " + post.getScore());
			if (post.getTitle() != null) {
				System.out.println("TITLE: " + post.getTitle());
			}
			System.out.println(post.getBody());
			System.out.println("==================================");
		}

	}

	private static void readDataIntoMongoDb(MongoTemplate mongoTemplate)
			throws MalformedURLException, IOException, FileNotFoundException,
			XMLStreamException {
		Path path = Paths.get("./beer.stackexchange.com.7z");
		if (!Files.exists(path)) {
			URL website = new URL(
					"https://archive.org/download/stackexchange/beer.stackexchange.com.7z");
			try (ReadableByteChannel rbc = Channels.newChannel(website.openStream());
					FileOutputStream fos = new FileOutputStream(path.toFile())) {
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			}
		}

		try (SevenZFile sevenZFile = new SevenZFile(path.toFile())) {
			SevenZArchiveEntry entry = null;
			while ((entry = sevenZFile.getNextEntry()) != null) {
				if ("Posts.xml".equals(entry.getName())) {

					importPosts(sevenZFile, mongoTemplate);

					break;
				}
			}
		}
	}

	@Override
	protected String getDatabaseName() {
		return "stackexchange";
	}

	@Override
	public MongoClient mongo() throws Exception {
		MongoClient mongoClient = new MongoClient("localhost");
		mongoClient.setWriteConcern(WriteConcern.UNACKNOWLEDGED);
		return mongoClient;
	}

	@Override
	public CustomConversions customConversions() {
		return new CustomConversions(
				Arrays.asList(LocalDateTimeToStringConverter.INSTANCE,
						StringToLocalDateTimeConverter.INSTANCE));
	}

	private static void importPosts(SevenZFile sevenZFile, MongoTemplate mongoTemplate)
			throws NumberFormatException, XMLStreamException {

		List<Post> posts = new ArrayList<>();
		Post post = null;

		XMLInputFactory factory = XMLInputFactory.newInstance();

		@SuppressWarnings("resource")
		XMLStreamReader reader = factory.createXMLStreamReader(
				new SevenZFileInputStream(sevenZFile), StandardCharsets.UTF_8.name());

		while (reader.hasNext()) {
			int event = reader.next();
			if (XMLStreamConstants.START_ELEMENT == event) {
				if ("row".equals(reader.getLocalName())) {
					post = new Post();

					for (int i = 0; i < reader.getAttributeCount(); i++) {
						String attributeName = reader.getAttributeLocalName(i);
						String attributeValue = reader.getAttributeValue(i);

						switch (attributeName) {
						case "Id":
							post.setStackexchangeId(Long.parseLong(attributeValue));
							break;
						case "PostTypeId":
							post.setPostTypeId(Long.parseLong(attributeValue));
							break;
						case "CreationDate":
							post.setCreationDate(LocalDateTime.parse(attributeValue));
							break;
						case "Body":
							post.setBody(attributeValue);
							break;
						case "AcceptedAnswerId":
							post.setAcceptedAnswerId(Long.parseLong(attributeValue));
							break;
						case "ViewCount":
							post.setViewCount(Long.parseLong(attributeValue));
							break;
						case "Score":
							post.setStackexchangeScore(Long.parseLong(attributeValue));
							break;
						case "Title":
							post.setTitle(attributeValue);
							break;
						case "Tags":
							post.setTags(Arrays.stream(attributeValue.split("<|>"))
									.filter(StringUtils::hasText)
									.collect(Collectors.toList()));
							break;
						default:
							break;
						}
					}

					posts.add(post);

					if (posts.size() > 10000) {
						mongoTemplate.insert(posts, Post.class);
						posts.clear();
					}

				}
			}

		}

		if (!posts.isEmpty()) {
			mongoTemplate.insert(posts, Post.class);
		}

	}

}
