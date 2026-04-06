package ch.rasc.tailerhttplog;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class Main2 {

	public static void main(String[] args) throws IOException {

		Executor executor = Executors.newFixedThreadPool(1);

		String path = "e:/access.log";

		// final UserAgentStringParser parser =
		// UADetectorServiceFactory.getResourceModuleParser();

		File database = new File("e:/_download/GeoLite2-City.mmdb");
		DatabaseReader reader = new DatabaseReader.Builder(database).build();

		// SimpleDateFormat accesslogDateFormat = new
		// SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");

		final Pattern accessLogPattern = Pattern.compile(getAccessLogRegex(),
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

		Path p = Paths.get(path);
		Tailer tailer = new Tailer(p.toFile(), new TailerListenerAdapter() {

			@Override
			public void handle(String line) {

				Matcher accessLogEntryMatcher = accessLogPattern.matcher(line);

				if (!accessLogEntryMatcher.matches()) {
					System.out.println(line);
					return;
				}

				// String userAgent = accessLogEntryMatcher.group(9);
				// UserAgent ua = parser.parse(userAgent);
				// System.out.print(ua.getOperatingSystem().getFamilyName() +
				// ":");

				String ip = accessLogEntryMatcher.group(1);
				if (!"-".equals(ip) && !"127.0.0.1".equals(ip)) {

					CityResponse response;
					try {
						response = reader.city(InetAddress.getByName(ip));

						if (response != null) {
							System.out.printf("%s=%s:%s:%f:%f\n", ip,
									response.getCountry().getName(),
									response.getCity().getName(),
									response.getLocation().getLatitude(),
									response.getLocation().getLongitude());
						}
						else {
							System.out.println(ip + " not found");
						}
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

	private static String getAccessLogRegex() {
		String regex1 = "^([\\d.-]+)"; // Client IP
		String regex2 = " (\\S+)"; // -
		String regex3 = " (\\S+)"; // -
		String regex4 = " \\[([\\w:/]+\\s[+\\-]\\d{4})\\]"; // Date
		String regex5 = " \"(.*?)\""; // request method and url
		String regex6 = " (\\d{3})"; // HTTP code
		String regex7 = " (\\d+|(.+?))"; // Number of bytes
		String regex8 = " \"([^\"]+|(.+?))\""; // Referer
		String regex9 = " \"([^\"]+|(.+?))\""; // Agent

		return regex1 + regex2 + regex3 + regex4 + regex5 + regex6 + regex7 + regex8
				+ regex9;
	}
}
