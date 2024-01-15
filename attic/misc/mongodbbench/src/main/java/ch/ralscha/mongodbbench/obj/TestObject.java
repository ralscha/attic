package ch.ralscha.mongodbbench.obj;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class TestObject {
	String stringVal;
	long longVal;
	List<User> users;

	public TestObject(BasicDBObject dbo) {

		stringVal = dbo.getString("strval");
		longVal = dbo.getLong("long_val");

		BasicDBList level2 = (BasicDBList) dbo.get("users");
		if (level2 != null) {
			users = Lists.newArrayList();
			for (Iterator<Object> iterator = level2.iterator(); iterator.hasNext();) {
				users.add(new User((BasicDBObject)iterator.next()));				
			}
		}
	}

	public String getStringVal() {
		return stringVal;
	}

	public long getLongVal() {
		return longVal;
	}

	public List<User> getUsers() {
		return users;
	}

}