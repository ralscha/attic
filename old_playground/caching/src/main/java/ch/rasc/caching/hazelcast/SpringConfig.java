package ch.rasc.caching.hazelcast;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Configuration
@ComponentScan(basePackages = { "ch.rasc.caching.hazelcast" })
@EnableCaching
public class SpringConfig {

	@Bean
	public HazelcastInstance hazelcastInstance() {

		Config config = new Config();
		config.getGroupConfig().setName("user").setPassword("password");
		// config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

		// config.getNetworkConfig().getJoin().getTcpIpConfig()
		// .setMembers(ImmutableList.of("192.168.20.153", "192.168.20.150"))
		// .setEnabled(true);

		return Hazelcast.newHazelcastInstance(config);

	}

	@Bean
	public CacheManager cacheManager() {
		return new HazelcastCacheManager(hazelcastInstance());
	}

}
