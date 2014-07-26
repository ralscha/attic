package ch.rasc.e4ds.config;

import org.springframework.data.mongodb.core.MongoTemplate;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.rasc.e4ds.domain.LoggingEvent;

public class MongoDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	private final MongoTemplate mongoTemplate;

	protected MongoDBAppender(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	protected void append(ILoggingEvent eventObject) {
		mongoTemplate.save(new LoggingEvent(eventObject));
	}

}