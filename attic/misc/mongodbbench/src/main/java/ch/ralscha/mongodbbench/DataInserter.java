package ch.ralscha.mongodbbench;

import java.util.List;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class DataInserter {

	private Options options;
	private DB db;
	
	public DataInserter(Mongo mongo, Options options) throws MongoException {
		this.options = options;
		this.db = mongo.getDB(options.getDatabase());
	}

	private void dropCollection() {
		DBCollection collection = db.getCollection(options.getCollection());
		collection.drop();
	}
	
	public void doIt() {		
		
		DBCollection collection = db.getCollection(options.getCollection());	
		if (collection.count() == options.getNoOfDocuments()) {
			System.out.println("NO INSERT");
			return;
		}
		dropCollection();
		
		//collection.setWriteConcern(WriteConcern.SAFE);

		List<DBObject> objects = Lists.newArrayList();
		for (int i = 0; i < options.getNoOfDocuments(); i++) {
						
			BasicDBObject dbo = new BasicDBObject();
			dbo.put("strval", RandomDataUtil.getRandomAlphaString(24));
			dbo.put("long_val", RandomDataUtil.getRandomLong());
			
			BasicDBList level1 = new BasicDBList();
			for(int j = 0; j < 5; j++){
				BasicDBObject level1Object = new BasicDBObject();
				level1Object.put("first_name", RandomDataUtil.getRandomAlphaString(5));
				level1Object.put("last_name", RandomDataUtil.getRandomAlphaString(8));
				level1Object.put("email", RandomDataUtil.getRandomEmail());
				level1Object.put("ip_addr", RandomDataUtil.getRandomIpAddress());

				BasicDBList level2 = new BasicDBList();
				for(int k = 0; k < 15; k++){
					BasicDBObject level2Object = new BasicDBObject();
					level2Object.put("login_count", RandomDataUtil.getRandomLong());
					level2Object.put("referrer", RandomDataUtil.getRandomURL());
					level2.add(level2Object);
				}
				level1Object.put("activity", level2);
				level1.add(level1Object);
			}
			dbo.put("users", level1);
			
			objects.add(dbo);
			
			
			if (i % 5000 == 0) {
				collection.insert(objects);
				objects.clear();
			}
		}
		
		if (!objects.isEmpty()) {
			collection.insert(objects);
		}
		
		
		
		
	}
}
