package ch.rasc.mongodb.author;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ch.rasc.mongodb.author.morphia.Word12;
import ch.rasc.mongodb.author.raw.RawAuthor;
import ch.rasc.mongodb.author.raw.RawTextImporter;

@Configuration
public class AppConfiguration {

	@Bean(destroyMethod = "close")
	public MongoClient mongoClient() throws MongoException {
		MongoClient mongo = new MongoClient("localhost");
		return mongo;
	}

	@Bean
	public MongoDatabase db() throws MongoException {
		return mongoClient().getDatabase("mydb");
	}

	@Bean
	public MongoCollection<Document> collection() throws MongoException {
		return db().getCollection("text");
	}

	@Bean
	public Datastore datastore() throws MongoException {
		Morphia morphia = new Morphia();
		morphia.map(Word12.class);

		Datastore ds = morphia.createDatastore(mongoClient(), "mydb");
		ds.ensureIndexes();
		return ds;
	}

	@Bean
	public Author author() {
		return new RawAuthor();
		// return new MorphiaAuthor();
	}

	@Bean
	public TextImporter textImporter() {
		return new RawTextImporter();
		// return new MorphiaTextImporter();
	}

}
