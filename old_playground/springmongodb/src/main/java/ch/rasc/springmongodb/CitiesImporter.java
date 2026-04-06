package ch.rasc.springmongodb;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

import com.google.common.base.Charsets;

import ch.rasc.springmongodb.domain.City;

public class CitiesImporter {

	public static void main(String[] args) throws IOException {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
			ctx.register(AppConfig.class);
			ctx.refresh();

			final MongoOperations mongoOps = ctx.getBean(MongoTemplate.class);

			if (mongoOps.collectionExists(City.class)) {
				System.out.println("DROP");
				mongoOps.dropCollection(City.class);
			}
			else {
				System.out.println("NO DROP");
			}

			Path path = Paths.get("./worldcitiespop.txt.gz");
			if (!java.nio.file.Files.exists(path)) {
				URL website = new URL(
						"http://www.maxmind.com/download/worldcities/worldcitiespop.txt.gz");
				try (ReadableByteChannel rbc = Channels.newChannel(website.openStream());
						FileOutputStream fos = new FileOutputStream(path.toFile())) {
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
			}

			try (InputStream fileStream = Files.newInputStream(path);
					InputStream gzipStream = new GZIPInputStream(fileStream);
					Reader decoder = new InputStreamReader(gzipStream,
							Charsets.ISO_8859_1);
					BufferedReader buffered = new BufferedReader(decoder)) {

				String line = null;
				List<City> cities = new ArrayList<>();
				while ((line = buffered.readLine()) != null) {
					if (!line.startsWith("Country,City")) {
						String[] splittedItems = StringUtils
								.commaDelimitedListToStringArray(line);
						if (splittedItems.length == 7) {

							City newCity = new City();
							newCity.setCountry(splittedItems[0]);
							newCity.setAsciiCityName(splittedItems[1]);
							newCity.setCityName(splittedItems[2]);
							newCity.setRegion(splittedItems[3]);

							String populationString = splittedItems[4];
							if (StringUtils.hasText(populationString)) {
								newCity.setPopulation(Integer.valueOf(populationString));
							}

							String latitudeStr = splittedItems[5];
							String longitudeStr = splittedItems[6];

							if (latitudeStr.equals("180.0")) {
								latitudeStr = "179.9999";
							}

							if (longitudeStr.equals("180.0")) {
								longitudeStr = "179.9999";
							}

							newCity.setLocation(new Point(Double.parseDouble(latitudeStr),
									Double.parseDouble(longitudeStr)));

							cities.add(newCity);

							if (cities.size() > 500) {
								mongoOps.insert(cities, City.class);
								cities.clear();
							}
						}
					}
				}

				if (!cities.isEmpty()) {
					mongoOps.insert(cities, City.class);
				}

			}

		}

	}

}
