package ch.rasc.e4ds.schedule;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.rasc.e4ds.domain.AccessLog;

@Component
public class AccessLogCleanup {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Scheduled(cron = "0 0 5 * * *")
	public void doCleanup() {
		// Delete all access logs that are older than 1 month
		DateTime oneMonthAgo = DateTime.now().minusMonths(1);
		ObjectId oneMonthAgoObjectId = new ObjectId(oneMonthAgo.toDate(), 0, 0);
		mongoTemplate.remove(Query.query(Criteria.where("id").lt(oneMonthAgoObjectId)), AccessLog.class);
	}

}
