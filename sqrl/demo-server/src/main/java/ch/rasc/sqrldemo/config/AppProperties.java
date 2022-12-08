package ch.rasc.sqrldemo.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@Component
@Validated
public class AppProperties {

	private Duration nutExpiration = Duration.ofMinutes(10);

	private String host;
	
	public Duration getNutExpiration() {
		return this.nutExpiration;
	}

	public void setNutExpiration(Duration nutExpiration) {
		this.nutExpiration = nutExpiration;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
