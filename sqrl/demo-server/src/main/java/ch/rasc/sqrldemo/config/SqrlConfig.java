package ch.rasc.sqrldemo.config;

import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.rasc.sqrl.SqrlManager;
import ch.rasc.sqrl.auth.SqrlIdentityService;
import ch.rasc.sqrl.cache.CaffeineNutCache;
import ch.rasc.sqrl.cache.NutCache;
import ch.rasc.sqrl.nut.JettyNutGenerator;
import ch.rasc.sqrl.nut.NutGenerator;

@Configuration
public class SqrlConfig {

	@Bean
	public SqrlManager sqrlManager(AppProperties appProperties, NutCache nutCache,
			NutGenerator nutGenerator, SqrlIdentityService sqrlIdentityService)
			throws NoSuchAlgorithmException {
		return new SqrlManager(appProperties.getHost(), "/sqrl", sqrlIdentityService,
				nutCache, nutGenerator);
	}

	@Bean
	public NutCache nutCache(AppProperties appProperties) {
		return new CaffeineNutCache(appProperties.getNutExpiration());
	}

	@Bean
	public NutGenerator nutGenerator() {
		return new JettyNutGenerator();
	}

}
