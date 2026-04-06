package ch.rasc.mongodb.gridfs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

public class Big {

	public static void main(String[] args) throws MongoException, IOException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) throws IOException {
		MongoDatabase db = mongo.getDatabase("testdb");

		GridFSBucket gridFSBucket = GridFSBuckets.create(db);

		// byte[] data = "Data to upload into GridFS".getBytes(StandardCharsets.UTF_8);
		// GridFSUploadStream uploadStream = gridFSBucket.openUploadStream("sampleData",
		// options);
		// uploadStream.write(data);
		// uploadStream.close();
		// System.out.println("The fileId of the uploaded file is: " +
		// uploadStream.getFileId().toHexString());

		Path currentDir = Paths.get(".");

		Files.list(currentDir).filter(Files::isRegularFile).forEach(p -> {

			try {
				String contentType = Files.probeContentType(p);

				try (InputStream is = Files.newInputStream(p)) {

					GridFSUploadOptions options = new GridFSUploadOptions()
							.chunkSizeBytes(1024)
							.metadata(new Document("contentType", contentType));

					ObjectId fileId = gridFSBucket
							.uploadFromStream(p.getFileName().toString(), is, options);
					System.out.println(p);
					System.out.println(fileId);
					System.out.println();
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}

		});

	}

}
