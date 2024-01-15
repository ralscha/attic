package ch.ralscha.mongodbbench.obj;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String ipAddress;
	private List<UserActivity> activity;

	public User(BasicDBObject dbo) {

		firstName = dbo.getString("first_name");
		lastName = dbo.getString("last_name");
		email = dbo.getString("email");
		ipAddress = dbo.getString("ip_addr");

		BasicDBList activities = (BasicDBList) dbo.get("activity");
		if (activities != null) {
			activity = Lists.newArrayList();
			
			for (Iterator<Object> iterator = activities.iterator(); iterator.hasNext();) {
				activity.add(new UserActivity((BasicDBObject)iterator.next()));
			}
		}

	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public List<UserActivity> getActivity() {
		return activity;
	}

}
