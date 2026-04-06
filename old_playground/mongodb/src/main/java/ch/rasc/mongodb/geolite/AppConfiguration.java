package ch.rasc.mongodb.geolite;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;

@Configuration
public class AppConfiguration {

	@Bean(destroyMethod = "close")
	public MongoClient mongoClient() throws MongoException {
		return new MongoClient("localhost");
	}

	@Bean
	public Datastore datastore() throws MongoException {
		Morphia morphia = new Morphia();
		morphia.map(Geolite.class);

		Datastore ds = morphia.createDatastore(mongoClient(), "mydb");
		ds.ensureIndexes();
		return ds;
	}

}
