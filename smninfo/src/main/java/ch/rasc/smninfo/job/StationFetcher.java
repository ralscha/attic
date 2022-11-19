package ch.rasc.smninfo.job;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.rasc.smninfo.Application;
import ch.rasc.smninfo.domain.Station;
import ch.rasc.smninfo.xodus.ExodusManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Component
public class StationFetcher {

	private final static String STATION_INFO_URL = "http://data.geo.admin.ch/ch.meteoschweiz.swissmetnet/info/VQHA69_EN.txt";

	private final static String INFO_LINE_REGEX = "([A-Z]{3})([^\\d]+)(\\d{1,2})°(\\d{1,2})'/(\\d{1,2})°(\\d{1,2})'[^\\d]+(\\d+)/(\\d+)[^\\d]+(\\d+)";

	private final OkHttpClient httpClient;

	private final ExodusManager exodusManager;

	private final Pattern infoLinePattern;

	public StationFetcher(OkHttpClient httpClient, ExodusManager exodusManager) {
		this.httpClient = httpClient;
		this.exodusManager = exodusManager;
		this.infoLinePattern = Pattern.compile(INFO_LINE_REGEX);
	}

	@Scheduled(cron = "0 30 1 * * SUN")
	public void fetchData() {

		Request request = new Request.Builder().url(STATION_INFO_URL).build();

		try (Response response = this.httpClient.newCall(request).execute();
				ResponseBody responseBody = response.body()) {
			if (responseBody != null) {
				byte[] content = responseBody.bytes();
				responseBody.close();
				String[] lines = new String(content, StandardCharsets.ISO_8859_1)
						.split("\r\n");

				List<Station> infos = new ArrayList<>();
				for (String line : lines) {
					Matcher matcher = this.infoLinePattern.matcher(line);

					if (matcher.matches()) {
						Station info = new Station();
						info.setCode(matcher.group(1));
						info.setName(matcher.group(2).trim());

						// int lngDeg = Integer.parseInt(matcher.group(3));
						// int lngMin = Integer.parseInt(matcher.group(4));
						// int latDeg = Integer.parseInt(matcher.group(5));
						// int latMin = Integer.parseInt(matcher.group(6));

						int[] kmCoordinates = new int[] {
								Integer.parseInt(matcher.group(7)),
								Integer.parseInt(matcher.group(8)) };
						info.setKmCoordinates(kmCoordinates);
						info.setLngLat(ch1903_wgs84(kmCoordinates));
						info.setAltitude(Integer.parseInt(matcher.group(9)));

						infos.add(info);
					}
				}

				if (!infos.isEmpty()) {
					this.exodusManager.deleteAllStations();
					this.exodusManager.insertStations(infos);

				}
			}
		}
		catch (IOException e) {
			Application.logger.error("fetching station info data", e);
		}

	}

	// private static double convertLocation(int lngDeg, int lngMin) {
	// double converted = Math.signum(lngDeg) * (Math.abs(lngDeg) + (lngMin / 60.0));
	// return (int) Math.round(converted * 100_000) / 100_000.0;
	// }

	private static double[] ch1903_wgs84(int[] ch1903) {
		double east = ch1903[0];
		double north = ch1903[1];
		east -= 600000d;
		north -= 200000d;
		east /= 1e6d;
		north /= 1e6d;

		double lng = 2.6779094;
		lng += 4.728982 * east;
		lng += 0.791484 * east * north;
		lng += 0.1306 * east * north * north;
		lng -= 0.0436 * east * east * east;

		double lat = 16.9023892;
		lat += 3.238272 * north;
		lat -= 0.270978 * east * east;
		lat -= 0.002528 * north * north;
		lat -= 0.0447 * east * east * north;
		lat -= 0.0140 * north * north * north;

		lng *= 100d / 36d;
		lat *= 100d / 36d;

		lng = BigDecimal.valueOf(lng).setScale(5, RoundingMode.HALF_UP).doubleValue();
		lat = BigDecimal.valueOf(lat).setScale(5, RoundingMode.HALF_UP).doubleValue();

		return new double[] { lng, lat };
	}
}
