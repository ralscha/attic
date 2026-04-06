package ch.rasc.mongodb.gridfs;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;

public class Read {

	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");

		GridFSBucket gridFSBucket = GridFSBuckets.create(db);

		gridFSBucket.find().forEach((Block<GridFSFile>) gridFSFile -> System.out
				.println(gridFSFile.getFilename()));

		System.out.println("---");

		gridFSBucket.find(Filters.eq("metadata.contentType", "text/xml"))
				.forEach((Block<GridFSFile>) gridFSFile -> System.out
						.println(gridFSFile.getFilename()));

	}

}
