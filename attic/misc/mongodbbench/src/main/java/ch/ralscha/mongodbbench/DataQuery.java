package ch.ralscha.mongodbbench;

import java.util.concurrent.Callable;

import ch.ralscha.mongodbbench.obj.TestObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class DataQuery implements Callable<Long> {

	private DB database;
	private Options options;

	public DataQuery(Mongo mongo, Options options) {
		this.options = options;
		this.database = mongo.getDB(options.getDatabase());
	}

	@Override
	public Long call() throws Exception {
		long count = 0;

		count = fetchFullObject();
		count += fetchOneLevelObject();
		count += fetchTopLevelObject();

		return count;
	}

	private long fetchFullObject() throws Exception {
		DBCursor cur = database.getCollection(options.getCollection()).find();
		long count = 0;
		while (cur.hasNext()) {
			++count;
			DBObject obj = cur.next();
			deserialize((BasicDBObject) obj);

			if (count >= options.getNoOfQueries()) {
				break;
			}
		}
		cur.close();
		return count;
	}

	private long fetchOneLevelObject() throws Exception {
		DBCursor cur = database.getCollection(options.getCollection()).find(null,
				new BasicDBObject("users.activity", 0));
		long count = 0;
		while (cur.hasNext()) {
			++count;
			DBObject obj = cur.next();
			deserialize((BasicDBObject) obj);

			if (count >= options.getNoOfQueries()) {
				break;
			}
		}
		cur.close();
		return count;
	}

	private long fetchTopLevelObject() throws Exception {
		DBCursor cur = database.getCollection(options.getCollection()).find(null, new BasicDBObject("users", 0));
		long count = 0;
		while (cur.hasNext()) {
			++count;
			DBObject obj = cur.next();
			deserialize((BasicDBObject) obj);

			if (count >= options.getNoOfQueries()) {
				break;
			}
		}
		cur.close();
		return count;
	}

	private void deserialize(BasicDBObject dbo) {
		TestObject o = new TestObject(dbo);
		
	}

}
