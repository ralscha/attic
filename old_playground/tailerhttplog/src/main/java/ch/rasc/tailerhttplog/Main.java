package ch.rasc.tailerhttplog;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

public class Main {

	public static void main(String[] args) throws IOException {

		Executor executor = Executors.newFixedThreadPool(1);

		String path;
		if (args.length == 1) {
			path = args[0];
		}
		else {
			// path = "e:/u_ex130108.log";
			path = "e:/access.log";
		}
		// 0 1 2 3 4 5 6 7 8 9 10 11
		// 127.0.0.1 - - [13/Jan/2013:01:23:53 +0100]
		// "POST /wp-cron.php?doing_wp_cron=1358036633.5729498863220214843750 HTTP/1.0"
		// 200 211 "-" "WordPress/1988; http://blog.rasc.ch"

		final UserAgentStringParser parser = UADetectorServiceFactory
				.getResourceModuleParser();

		File database = new File("e:/_download/GeoLite2-City.mmdb");
		DatabaseReader reader = new DatabaseReader.Builder(database).build();

		Path p = Paths.get(path);
		Tailer tailer = new Tailer(p.toFile(), new TailerListenerAdapter() {

			@Override
			public void handle(String line) {
				List<String> splittedLine = Arrays.asList(line.split(" "));
				System.out.println(splittedLine.size());
				if (splittedLine.size() == 11/* 14 */) {
					String userAgent = splittedLine.get(9);
					ReadableUserAgent ua = parser.parse(userAgent);
					System.out.print(ua.getOperatingSystem().getFamilyName() + ":");

					String ip = splittedLine.get(8);
					System.out.println(ip);
					CityResponse response;
					try {
						response = reader.city(InetAddress.getByName(ip));

						if (response != null) {
							System.out.printf("%s=%s:%s:%f:%f", ip,
									response.getCountry().getName(),
									response.getCity().getName(),
									response.getLocation().getLatitude(),
									response.getLocation().getLongitude());
						}
						System.out.println();
					}
					catch (UnknownHostException e) {
						e.printStackTrace();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					catch (GeoIp2Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		executor.execute(tailer);

	}
}
