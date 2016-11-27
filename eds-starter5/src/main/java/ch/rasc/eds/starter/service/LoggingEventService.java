package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.eds.starter.dto.LoggingEventDto;
import ch.rasc.eds.starter.entity.LoggingEvent;
import ch.rasc.eds.starter.entity.QLoggingEvent;
import ch.rasc.eds.starter.entity.QLoggingEventException;
import ch.rasc.eds.starter.entity.QLoggingEventProperty;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;

@Service
public class LoggingEventService {

	private final Environment environment;

	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public LoggingEventService(Environment environment, JPAQueryFactory jpaQueryFactory) {
		this.environment = environment;
		this.jpaQueryFactory = jpaQueryFactory;
	}

	private static final ImmutableMap<String, String> mapGuiColumn2DbField = new ImmutableMap.Builder<String, String>()
			.put("dateTime", "timestmp").put("message", "formattedMessage")
			.put("level", "levelString").build();

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public ExtDirectStoreResult<LoggingEventDto> read(ExtDirectStoreReadRequest request) {

		JPAQuery<LoggingEvent> query = this.jpaQueryFactory
				.selectFrom(QLoggingEvent.loggingEvent);

		if (!request.getFilters().isEmpty()) {
			StringFilter levelFilter = (StringFilter) request.getFilters().iterator()
					.next();
			String levelValue = levelFilter.getValue();
			query.where(QLoggingEvent.loggingEvent.levelString.eq(levelValue));
		}

		QuerydslUtil.addPagingAndSorting(query, request, LoggingEvent.class,
				QLoggingEvent.loggingEvent, mapGuiColumn2DbField,
				Collections.<String> emptySet());

		QueryResults<LoggingEvent> searchResult = query.fetchResults();

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
			for (LoggingEvent le : this.jpaQueryFactory
					.selectFrom(QLoggingEvent.loggingEvent)
					.where(QLoggingEvent.loggingEvent.levelString.eq(level)).fetch()) {
				this.jpaQueryFactory.getEntityManager().remove(le);
			}
			this.jpaQueryFactory.delete(QLoggingEvent.loggingEvent)
					.where(QLoggingEvent.loggingEvent.levelString.eq(level)).execute();
		}
		else {
			this.jpaQueryFactory.delete(QLoggingEventException.loggingEventException)
					.execute();
			this.jpaQueryFactory.delete(QLoggingEventProperty.loggingEventProperty)
					.execute();
			this.jpaQueryFactory.delete(QLoggingEvent.loggingEvent).execute();
		}
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	public void addTestData() {
		if (!this.environment.acceptsProfiles("default")) {
			Logger logger = LoggerFactory.getLogger(getClass());

			logger.debug("a simple debug log entry");
			logger.info("this is a info log entry");
			logger.warn("a warning", new IllegalArgumentException());
			logger.error("a serious error", new NullPointerException());
		}
	}

}
