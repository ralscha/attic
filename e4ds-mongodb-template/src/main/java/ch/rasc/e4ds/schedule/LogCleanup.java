package ch.rasc.e4ds.schedule;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.rasc.e4ds.domain.LoggingEvent;

@Component
public class LogCleanup {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Scheduled(cron = "0 0 4 * * *")
	public void doCleanup() {
		// Delete all log entries that are older than 1 day
		DateTime yesterday = DateTime.now().minusDays(1);
		mongoTemplate.remove(Query.query(Criteria.where("timeStamp").lte(yesterday.toDate())), LoggingEvent.class);
	}

}
