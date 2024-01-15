package ch.rasc.ab.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.ab.entity.AccessLog;
import ch.rasc.ab.entity.QAccessLog;
import ch.rasc.edsutil.QueryUtil;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class AccessLogService {

	@Autowired
	private Environment environment;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MessageSource messageSource;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<AccessLog> read(ExtDirectStoreReadRequest request,
			Locale locale) {

		JPQLQuery query = new JPAQuery(entityManager).from(QAccessLog.accessLog);

		if (!request.getFilters().isEmpty()) {
			StringFilter userNameFilter = (StringFilter) request.getFilters().iterator()
					.next();
			String userName = userNameFilter.getValue();
			query.where(QAccessLog.accessLog.userName.contains(userName));
		}

		QueryUtil.addPagingAndSorting(query, request, AccessLog.class,
				QAccessLog.accessLog, Collections.<String, String> emptyMap(),
				Collections.singleton("browser"));

		SearchResults<AccessLog> searchResult = query.listResults(QAccessLog.accessLog);

		PeriodFormatter minutesAndSeconds = new PeriodFormatterBuilder()
				.appendMinutes()
				.appendSuffix(
						" " + messageSource.getMessage("accesslog_minute", null, locale),
						" " + messageSource.getMessage("accesslog_minutes", null, locale))
				.appendSeparator(
						" " + messageSource.getMessage("accesslog_and", null, locale)
								+ " ")
				.printZeroRarelyLast()
				.appendSeconds()
				.appendSuffix(
						" " + messageSource.getMessage("accesslog_second", null, locale),
						" " + messageSource.getMessage("accesslog_seconds", null, locale))
				.toFormatter();

		for (AccessLog accessLog : searchResult.getResults()) {
			if (accessLog.getLogIn() != null && accessLog.getLogOut() != null) {
				Duration duration = new Duration(accessLog.getLogIn(),
						accessLog.getLogOut());
				Period period = new Period(
						duration,
						PeriodType.forFields(new DurationFieldType[] {
								DurationFieldType.minutes(), DurationFieldType.seconds() }));
				accessLog.setDuration(minutesAndSeconds.print(period));
			}

		}

		return new ExtDirectStoreResult<>(searchResult.getTotal(),
				searchResult.getResults());

	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void deleteAll() {
		new JPADeleteClause(entityManager, QAccessLog.accessLog).execute();
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void addTestData(HttpServletRequest request) {
		if (!environment.acceptsProfiles("production")) {

			String[] users = { "admin", "user" };
			Random random = new Random();
			String userAgent = request.getHeader("User-Agent");

			for (int i = 0; i < 1000; i++) {
				try {
					AccessLog accessLog = new AccessLog();
					accessLog.setUserName(users[random.nextInt(2)]);
					accessLog.setSessionId(RandomStringUtils.randomAlphanumeric(16));

					DateTime logIn = new DateTime(2013, random.nextInt(12) + 1,
							random.nextInt(31) + 1, random.nextInt(24),
							random.nextInt(60), random.nextInt(60));
					accessLog.setLogIn(logIn);
					accessLog.setLogOut(logIn.plusMinutes(random.nextInt(120)));
					accessLog.setUserAgent(userAgent);

					entityManager.persist(accessLog);
				}
				catch (IllegalArgumentException iae) {
					// do nothing here
				}
			}
		}
	}

}
