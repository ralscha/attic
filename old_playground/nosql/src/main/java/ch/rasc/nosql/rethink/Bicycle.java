package ch.rasc.nosql.rethink;

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
import java.time.ZoneOffset;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

public class Bicycle {

	public static final RethinkDB r = RethinkDB.r;

	public static void main(String[] args)
			throws IOException, NumberFormatException, XMLStreamException {

		try (Connection conn = r.connection().hostname("localhost").port(28015)
				.connect()) {
			try {
				r.dbDrop("test").run(conn);
			}
			catch (Exception e) {
				// ignore this
			}
			r.dbCreate("test").run(conn);
			r.tableCreate("posts").run(conn);

			Long count = r.table("posts").count().run(conn, Long.class);

			if (count == 0) {
				StopWatch sw = new StopWatch();
				sw.start();
				readDataIntoDatabase(conn);
				sw.stop();
				System.out.println(sw.getTime() + " ms");
			}

		}

	}

	private static void readDataIntoDatabase(Connection conn)
			throws MalformedURLException, IOException, FileNotFoundException,
			XMLStreamException {
		Path path = Paths.get("./bicycles.stackexchange.com.7z");
		if (!Files.exists(path)) {
			URL website = new URL(
					"https://archive.org/download/stackexchange/bicycles.stackexchange.com.7z");
			try (ReadableByteChannel rbc = Channels.newChannel(website.openStream());
					FileOutputStream fos = new FileOutputStream(path.toFile())) {
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			}
		}

		try (SevenZFile sevenZFile = new SevenZFile(path.toFile())) {
			SevenZArchiveEntry entry = null;
			while ((entry = sevenZFile.getNextEntry()) != null) {
				if ("Posts.xml".equals(entry.getName())) {
					importPosts(sevenZFile, conn);
					break;
				}
			}
		}
	}

	private static void importPosts(SevenZFile sevenZFile, Connection conn)
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
							post.setCreationDate(LocalDateTime.parse(attributeValue)
									.atOffset(ZoneOffset.UTC));
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
									.filter(StringUtils::isNotBlank)
									.collect(Collectors.toList()));
							break;
						default:
							break;
						}
					}

					posts.add(post);

					if (posts.size() > 10000) {
						r.table("posts").insert(posts).run(conn);
						posts.clear();
					}

				}
			}

		}

		if (!posts.isEmpty()) {
			r.table("posts").insert(posts).run(conn);
		}

	}

}
