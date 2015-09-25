#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ${package}.dto.LoggingEventDto;
import ${package}.entity.LoggingEvent;
import ${package}.entity.QLoggingEvent;
import ch.rasc.edsutil.QueryUtil;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class LoggingEventService {

	private final Environment environment;

	private final EntityManager entityManager;

	@Autowired
	public LoggingEventService(Environment environment, EntityManager entityManager) {
		this.environment = environment;
		this.entityManager = entityManager;
	}

	private static final ImmutableMap<String, String> mapGuiColumn2DbField = new ImmutableMap.Builder<String, String>()
			.put("dateTime", "timestmp").put("message", "formattedMessage")
			.put("level", "levelString").build();

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public ExtDirectStoreResult<LoggingEventDto> read(ExtDirectStoreReadRequest request) {

		JPQLQuery query = new JPAQuery(entityManager).from(QLoggingEvent.loggingEvent);

		if (!request.getFilters().isEmpty()) {
			StringFilter levelFilter = (StringFilter) request.getFilters().iterator()
					.next();
			String levelValue = levelFilter.getValue();
			query.where(QLoggingEvent.loggingEvent.levelString.eq(levelValue));
		}

		QueryUtil.addPagingAndSorting(query, request, LoggingEvent.class,
				QLoggingEvent.loggingEvent, mapGuiColumn2DbField,
				Collections.<String> emptySet());

		SearchResults<LoggingEvent> searchResult = query
				.listResults(QLoggingEvent.loggingEvent);

		List<LoggingEventDto> loggingEventList = new ArrayList<>();
		for (LoggingEvent event : searchResult.getResults()) {
			loggingEventList.add(new LoggingEventDto(event));
		}

		return new ExtDirectStoreResult<>(searchResult.getTotal(), loggingEventList);
	}

	@ExtDirectMethod
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteAll(String level) {
		if (StringUtils.hasText(level)) {
			for (LoggingEvent le : new JPAQuery(entityManager)
					.from(QLoggingEvent.loggingEvent)
					.where(QLoggingEvent.loggingEvent.levelString.eq(level))
					.list(QLoggingEvent.loggingEvent)) {
				entityManager.remove(le);
			}
		}
		else {
			for (LoggingEvent le : new JPAQuery(entityManager).from(
					QLoggingEvent.loggingEvent).list(QLoggingEvent.loggingEvent)) {
				entityManager.remove(le);
			}
		}
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	public void addTestData() {
		if (!environment.acceptsProfiles("production")) {
			Logger logger = LoggerFactory.getLogger(getClass());

			logger.debug("a simple debug log entry");
			logger.info("this is a info log entry");
			logger.warn("a warning", new IllegalArgumentException());
			logger.error("a serious error", new NullPointerException());
		}
	}

}
