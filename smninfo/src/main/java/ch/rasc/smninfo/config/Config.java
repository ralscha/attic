package ch.rasc.smninfo.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class Config {

	@Bean
	public OkHttpClient httpClient() {
		OkHttpClient client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
				.build();
		return client;
	}

}
