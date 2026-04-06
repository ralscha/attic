package ch.rasc.nosql.redis;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import redis.clients.jedis.Jedis;

public class HyperLogLog {

	public static void main(String[] args) throws XMLStreamException, IOException {
		try (Jedis jedis = new Jedis("192.168.178.35")) {
			// jedis.pfadd("users", "1", "2", "3", "4", "4", "4", "1");
			// System.out.println(jedis.pfcount("users"));
			//
			// System.out.println(jedis.del("users"));
			// jedis.disconnect();

			jedis.del("userIdsHLL");
			jedis.del("userIds");

			// Path path = Paths.get("e:/stackexchange/stackoverflow.com-Posts.7z");
			// try (SevenZFile sevenZFile = new SevenZFile(path.toFile())) {
			// SevenZArchiveEntry entry = null;
			// while ((entry = sevenZFile.getNextEntry()) != null) {
			// if ("Posts.xml".equals(entry.getName())) {
			//
			// uniqueUsers(new SevenZFileInputStream(sevenZFile), jedis);
			// System.out.println("Unique Users with HLL: "
			// + jedis.pfcount("userIdsHLL"));
			// System.out.println("Unique Users with Set: "
			// + jedis.scard("userIds"));
			//
			// break;
			// }
			// }
			// }

			Path path = Paths.get("e:/stackexchange/Posts.xml");
			uniqueUsers(new BufferedInputStream(Files.newInputStream(path)), jedis);
			System.out.println("Unique Users with HLL: " + jedis.pfcount("userIdsHLL"));
			System.out.println("Unique Users with Set: " + jedis.scard("userIds"));

			jedis.disconnect();
		}

	}

	private static void uniqueUsers(InputStream is, Jedis jedis)
			throws XMLStreamException {

		com.clearspring.analytics.stream.cardinality.HyperLogLog hyperLogLog = new com.clearspring.analytics.stream.cardinality.HyperLogLog(
				16);

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(is,
				StandardCharsets.UTF_8.name());

		long grandeTotal = 0;
		int ix = 0;
		String[] array = new String[10000];

		while (reader.hasNext()) {
			int event = reader.next();
			if (XMLStreamConstants.START_ELEMENT == event) {
				if ("row".equals(reader.getLocalName())) {

					String attributeValue = reader.getAttributeValue(null, "OwnerUserId");
					if (attributeValue != null) {
						array[ix++] = attributeValue;
						grandeTotal++;
						hyperLogLog.offer(attributeValue);

						if (ix % 10000 == 0) {
							jedis.pfadd("userIdsHLL", array);
							jedis.sadd("userIds", array);
							ix = 0;
						}
					}
				}
			}
		}
		if (ix > 0) {
			jedis.pfadd("userIdsHLL", Arrays.copyOfRange(array, 0, ix));
			jedis.sadd("userIds", array);
		}

		reader.close();

		System.out.println("Unique Users with stream-lib: " + hyperLogLog.cardinality());
		System.out.println("Total Ids: " + grandeTotal);
	}
}
