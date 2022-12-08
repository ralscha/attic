package ch.rasc.sqrl;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@Component
@Validated
public class AppProperties {

	private Duration nutExpiration = Duration.ofMinutes(10);

	private String appUrl;

	public Duration getNutExpiration() {
		return this.nutExpiration;
	}

	public void setNutExpiration(Duration nutExpiration) {
		this.nutExpiration = nutExpiration;
	}

	public String getAppUrl() {
		return this.appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

}
