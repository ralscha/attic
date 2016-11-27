package ch.rasc.eds.starter.service;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import ch.rasc.eds.starter.config.AppProperties;

@Service
public class GeoIPCityService {

	private final static Logger logger = LoggerFactory.getLogger(GeoIPCityService.class);

	private DatabaseReader reader = null;

	@Autowired
	public GeoIPCityService(AppProperties appProperties) {
		String databaseFile = appProperties.getGeoIp2CityDatabaseFile();
		if (databaseFile != null) {
			Path database = Paths.get(databaseFile);
			if (Files.exists(database)) {
				try {
					this.reader = new DatabaseReader.Builder(database.toFile()).build();
				}
				catch (IOException e) {
					logger.error("GeoIPCityService init", e);
				}
			}
		}
	}

	public String lookupCity(String ip) {
		if (this.reader != null) {
			CityResponse response;
			try {
				try {
					response = this.reader.city(InetAddress.getByName(ip));
				}
				catch (AddressNotFoundException e) {
					return null;
				}
				if (response != null) {
					String city = response.getCity().getName();
					String country = response.getCountry().getName();
					return Arrays.asList(city, country).stream().filter(Objects::nonNull)
							.collect(Collectors.joining(","));
				}
			}
			catch (IOException | GeoIp2Exception e) {
				logger.error("lookupCity", e);
			}
		}

		return null;
	}

}
