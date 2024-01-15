package ch.rasc.ab.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.ab.entity.LogEvent;
import ch.rasc.ab.entity.QLogEvent;
import ch.rasc.edsutil.QueryUtil;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class LogEventService {

	@Autowired
	private Environment environment;

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public ExtDirectStoreResult<LogEvent> read(ExtDirectStoreReadRequest request) {

		JPQLQuery query = new JPAQuery(entityManager).from(QLogEvent.logEvent);

		if (!request.getFilters().isEmpty()) {
			StringFilter levelFilter = (StringFilter) request.getFilters().iterator()
					.next();
			String levelValue = levelFilter.getValue();
			if (!levelValue.equals("ALL") && !levelValue.equals("OFF")) {
				query.where(QLogEvent.logEvent.level.eq(levelValue));
			}
		}

		QueryUtil.addPagingAndSorting(query, request, LogEvent.class, QLogEvent.logEvent);

		SearchResults<LogEvent> searchResult = query.listResults(QLogEvent.logEvent);
		return new ExtDirectStoreResult<>(searchResult.getTotal(),
				searchResult.getResults());

	}

	@ExtDirectMethod
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteAll(String level) {
		if (StringUtils.hasText(level)) {
			new JPADeleteClause(entityManager, QLogEvent.logEvent).where(
					QLogEvent.logEvent.level.eq(level)).execute();
		}
		else {
			new JPADeleteClause(entityManager, QLogEvent.logEvent).execute();

		}
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	public void addTestData() {
		if (!environment.acceptsProfiles("production")) {
			Logger log = LogManager.getLogger("ch.rasc.ab");
			log.debug("a simple debug log entry");
			log.info("this is a info log entry");
			log.warn("a warning", new IllegalArgumentException());
			log.error("a serious error", new NullPointerException());
		}
	}

}
