package ch.ralscha.mongodbbench.obj;

import com.mongodb.BasicDBObject;

public class UserActivity {
	long loginCount;
	String referrer;

	public UserActivity(BasicDBObject dbo) {
		loginCount = dbo.getLong("login_count");
		referrer = dbo.getString("referrer");
	}

	public long getLoginCount() {
		return loginCount;
	}

	public String getReferrer() {
		return referrer;
	}

}
