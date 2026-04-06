package ch.rasc.mongodb.geolite;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.mongodb.morphia.Datastore;
import org.zeroturnaround.zip.ZipUtil;

import au.com.bytecode.opencsv.CSVReader;

@Named
public class GeoliteImporter {

	@Inject
	private Datastore datastore;

	public void importData() throws IOException {
		Path locationPath = Paths.get("./testdata/GeoLiteCity-Location.csv");
		Path blockPath = Paths.get("./testdata/GeoLiteCity-Blocks.csv");

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			Path zipFile = Paths.get("./testdata/GeoLiteCity-latest.zip");
			if (!Files.exists(locationPath)) {
				try (CloseableHttpResponse response = client.execute(
						new HttpGet("http://rasc.ch/testdata/GeoLiteCity-latest.zip"))) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						try (OutputStream outstream = Files.newOutputStream(zipFile)) {
							entity.writeTo(outstream);
						}
					}
				}

				// unzip
				ZipUtil.unpackEntry(zipFile.toFile(),
						"GeoLiteCity_20150804/GeoLiteCity-Location.csv",
						locationPath.toFile());
				ZipUtil.unpackEntry(zipFile.toFile(),
						"GeoLiteCity_20150804/GeoLiteCity-Blocks.csv",
						blockPath.toFile());
				Files.delete(zipFile);
			}
		}

		Map<Integer, Geolite> locationMap = new HashMap<>();

		try (CSVReader reader = new CSVReader(new FileReader(locationPath.toFile()))) {
			reader.readNext();
			reader.readNext();
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				// locId,country,region,city,postalCode,latitude,longitude,metroCode,areaCode
				int locId = Integer.parseInt(nextLine[0]);
				Geolite geolite = new Geolite();
				geolite.setCountry(nextLine[1]);
				geolite.setRegion(nextLine[2]);
				geolite.setCity(nextLine[3]);
				geolite.setPostalCode(nextLine[4]);
				geolite.setLatitude(Double.parseDouble(nextLine[5]));
				geolite.setLongitude(Double.parseDouble(nextLine[6]));
				geolite.setMetroCode(nextLine[7]);
				geolite.setAreaCode(nextLine[8]);

				locationMap.put(locId, geolite);
			}
		}

		try (CSVReader reader = new CSVReader(new FileReader(blockPath.toFile()))) {
			reader.readNext();
			reader.readNext();
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				long startIp = Long.parseLong(nextLine[0]);
				long endIp = Long.parseLong(nextLine[1]);
				int locId = Integer.parseInt(nextLine[2]);

				Geolite geolite = locationMap.get(locId);
				geolite.setStartIpNum(startIp);
				geolite.setEndIpNum(endIp);
				geolite.setId(null);
				this.datastore.save(geolite);
			}
		}

	}
}
