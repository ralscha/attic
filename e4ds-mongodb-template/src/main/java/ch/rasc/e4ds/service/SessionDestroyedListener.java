package ch.rasc.e4ds.service;

import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import ch.rasc.e4ds.domain.AccessLog;

@Component
public class SessionDestroyedListener implements ApplicationContextAware, ApplicationListener<SessionDestroyedEvent> {

	private ApplicationContext applicationContext;

	// @Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	protected MongoTemplate getMongoTemplate() {
		if (mongoTemplate == null) {
			mongoTemplate = applicationContext.getBean(MongoTemplate.class);
		}
		return mongoTemplate;
	}

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		getMongoTemplate().updateFirst(Query.query(Criteria.where("sessionId").is(event.getId())),
				Update.update("logOut", DateTime.now()), AccessLog.class);
	}

}
