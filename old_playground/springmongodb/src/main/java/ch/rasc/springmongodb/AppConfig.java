package ch.rasc.springmongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories
public class AppConfig extends AbstractMongoConfiguration {

	@Override
	public MongoClient mongo() throws Exception {

		// MongoClientOptions options = MongoClientOptions.builder()
		// .connectionsPerHost(8)
		// .threadsAllowedToBlockForConnectionMultiplier(4)
		// .connectTimeout(1000)
		// .maxWaitTime(1500)
		// .socketKeepAlive(true)
		// .socketTimeout(1500)
		// .build();

		return new MongoClient("localhost"/* , options */);
	}

	@Override
	public String getDatabaseName() {
		return "linkedin";
	}

	@Override
	public String getMappingBasePackage() {
		return "ch.rasc.springmongodb.domain";
	}

}