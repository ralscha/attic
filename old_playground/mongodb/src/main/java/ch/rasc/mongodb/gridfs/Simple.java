package ch.rasc.mongodb.gridfs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Simple {

	public static void main(String[] args) throws MongoException, IOException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) throws IOException {
		MongoDatabase db = mongo.getDatabase("testdb");
		MongoCollection<Document> collection = db.getCollection("files");
		collection.drop();

		File currentDir = new File(".");
		File[] files = currentDir.listFiles();
		for (File file : files) {

			if (file.isFile()) {
				System.out.println(file.getName());
				Document fileObject = new Document();
				fileObject.append("fileName", file.getName());
				fileObject.append("content", FileUtils.readFileToByteArray(file));
				collection.insertOne(fileObject);
			}
		}
	}

}
