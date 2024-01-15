package ch.rasc.messenger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import ch.rasc.messenger.domain.CMessage;
import ch.rasc.messenger.domain.CUser;
import ch.rasc.messenger.domain.Message;
import ch.rasc.messenger.domain.User;

@Component
public class MongoDb {

	private final MongoDatabase mongoDatabase;

	public MongoDb(final MongoDatabase mongoDatabase) {
		this.mongoDatabase = mongoDatabase;
	}

	@PostConstruct
	public void createIndexes() {

		if (!indexExists(User.class, CUser.userName)) {
			this.getCollection(User.class).createIndex(Indexes.ascending(CUser.userName),
					new IndexOptions().unique(true));
		}

		if (!indexExists(User.class, CUser.location)) {
			this.getCollection(User.class)
					.createIndex(Indexes.geo2dsphere(CUser.location));
		}

		if (!indexExists(Message.class, CMessage.location)) {
			this.getCollection(Message.class)
					.createIndex(Indexes.geo2dsphere(CMessage.location));
		}
	}

	public boolean indexExists(Class<?> clazz, String indexName) {
		return indexExists(this.getCollection(clazz), indexName);
	}

	public boolean indexExists(MongoCollection<?> collection, String indexName) {
		for (Document doc : collection.listIndexes()) {
			Document key = (Document) doc.get("key");
			if (key != null) {
				if (key.containsKey(indexName)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean collectionExists(final Class<?> clazz) {
		return collectionExists(getCollectionName(clazz));
	}

	public boolean collectionExists(final String collectionName) {
		return this.getMongoDatabase().listCollections()
				.filter(Filters.eq("name", collectionName)).first() != null;
	}

	public MongoDatabase getMongoDatabase() {
		return this.mongoDatabase;
	}

	public <T> MongoCollection<T> getCollection(Class<T> documentClass) {
		return this.getMongoDatabase().getCollection(getCollectionName(documentClass),
				documentClass);
	}

	private static String getCollectionName(Class<?> documentClass) {
		return StringUtils.uncapitalize(documentClass.getSimpleName());
	}

	public <T> MongoCollection<T> getCollection(String collectionName,
			Class<T> documentClass) {
		return this.getMongoDatabase().getCollection(collectionName, documentClass);
	}

	public MongoCollection<Document> getCollection(String collectionName) {
		return this.getMongoDatabase().getCollection(collectionName);
	}

	public long count(Class<?> documentClass) {
		return this.getCollection(documentClass).count();
	}

	public static <T> List<T> toList(FindIterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false)
				.collect(Collectors.toList());
	}

	public static <T> Stream<T> stream(FindIterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}

}
