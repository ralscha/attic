package ch.rasc.tailerhttplog;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Download {

	public static void main(String[] args) {
		String url = "http://geolite.maxmind.com/download/geoip/database/GeoLite2-City.mmdb.gz";
		Path path = Paths.get("e:/geocitylite.mmdb");

		if (!Files.exists(path)) {

			try (CloseableHttpClient backend = HttpClientBuilder.create().setUserAgent(
					"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.102 Safari/537.36")
					.build()) {

				HttpGet httpGet = new HttpGet(url);
				try (CloseableHttpResponse response = backend.execute(httpGet);
						InputStream is = response.getEntity().getContent();
						GZIPInputStream gis = new GZIPInputStream(is)) {
					Files.copy(gis, path);
				}

				// FileInputStream fin =
				// Files.newInputStream(Paths.get("archive.tar.gz"));
				// BufferedInputStream in = new BufferedInputStream(fin);
				// FileOutputStream out = Files.newOutputStream(Paths.get("archive.tar"));
				// GZipCompressorInputStream gzIn = new GZipCompressorInputStream(in);
				// final byte[] buffer = new byte[buffersize];
				// int n = 0;
				// while (-1 != (n = gzIn.read(buffer))) {
				// out.write(buffer, 0, n);
				// }
				// out.close();
				// gzIn.close();

			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}
		else {
			System.out.println("Database exists");
		}

	}

}
