package ch.rasc.tailerhttplog;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

public class Main3 {

	public static void main(String[] args) throws IOException {
		File database = new File("e:/_download/GeoLite2-City.mmdb");
		DatabaseReader reader = new DatabaseReader.Builder(database).build();

		String[] ipAddresses = { "64.4.11.42", "173.194.113.145", "31.13.93.129",
				"23.67.141.15", "54.225.87.37" };

		Arrays.stream(ipAddresses).forEach(ip -> {
			try {
				CityResponse cr = reader.city(InetAddress.getByName(ip));
				System.out.println(ip + " " + cr);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		});

	}

}
