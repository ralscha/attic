package ch.rasc.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;
import static ch.rasc.e4ds.entity.QAccessLog.accessLog;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

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
import ch.rasc.e4ds.entity.AccessLog;
import ch.rasc.edsutil.QueryUtil;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicLongMap;
import com.mysema.query.SearchResults;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class AccessLogService {

	private final static BigDecimal ONE_HUNDRED = new BigDecimal("100");

	private final Environment environment;

	private final EntityManager entityManager;

	private final MessageSource messageSource;

	@Autowired
	public AccessLogService(Environment environment, EntityManager entityManager,
			MessageSource messageSource) {
		this.environment = environment;
		this.entityManager = entityManager;
		this.messageSource = messageSource;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<AccessLog> read(ExtDirectStoreReadRequest request,
			Locale locale) {

		JPQLQuery query = new JPAQuery(this.entityManager).from(accessLog);

		if (!request.getFilters().isEmpty()) {
			StringFilter userNameFilter = (StringFilter) request.getFilters().iterator()
					.next();
			String userName = userNameFilter.getValue();
			query.where(accessLog.userName.contains(userName));
		}

		QueryUtil
				.addPagingAndSorting(query, request, AccessLog.class, accessLog,
						Collections.<String, String> emptyMap(),
						Collections.singleton("browser"));

		SearchResults<AccessLog> searchResult = query.listResults(accessLog);

		String minutesText = this.messageSource.getMessage("accesslog_minutes", null,
				locale);
		String andText = this.messageSource.getMessage("accesslog_and", null, locale);
		String secondsText = this.messageSource.getMessage("accesslog_seconds", null,
				locale);

		for (AccessLog log : searchResult.getResults()) {
			if (log.getLogIn() != null && log.getLogOut() != null) {
				Duration duration = Duration.between(log.getLogIn(), log.getLogOut());
				log.setDuration(duration.toMinutes() + " " + minutesText + " " + andText
						+ " " + duration.getSeconds() % 60 + " " + secondsText);
			}

		}

		return new ExtDirectStoreResult<>(searchResult.getTotal(),
				searchResult.getResults());
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public Collection<Map<String, Integer>> readAccessLogYears() {
		JPQLQuery query = new JPAQuery(this.entityManager).from(accessLog);
		List<Integer> years = query.distinct().list(accessLog.logIn.year());
		return years.stream().map(year -> Collections.singletonMap("year", year))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void deleteAll() {
		new JPADeleteClause(this.entityManager, accessLog).execute();
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public List<Map<String, Object>> readUserAgentsStats(int queryYear) {
		JPQLQuery query = new JPAQuery(this.entityManager).from(accessLog);
		query.where(accessLog.logIn.year().eq(queryYear));
		query.groupBy(accessLog.logIn.year(), accessLog.logIn.month(),
				accessLog.userAgentName);
		query.orderBy(accessLog.logIn.year().asc(), accessLog.logIn.month().asc());
		List<Tuple> queryResult = query.list(accessLog.logIn.year(),
				accessLog.logIn.month(), accessLog.userAgentName, accessLog.count());

		String[] browsers = new String[] { "Chrome", "Firefox", "IE", "Opera", "Safari" };

		AtomicLongMap<String> monthYearTotal = AtomicLongMap.create();
		Map<String, AtomicLongMap<String>> monthYearUACount = Maps.newTreeMap();

		for (Tuple tuple : queryResult) {
			Integer year = tuple.get(accessLog.logIn.year());
			Integer month = tuple.get(accessLog.logIn.month());
			Long count = tuple.get(accessLog.count());
			String userAgentName = tuple.get(accessLog.userAgentName);
			String key = year + "/" + (month < 10 ? "0" : "") + month;

			monthYearTotal.addAndGet(key, count);

			AtomicLongMap<String> uas = monthYearUACount.get(key);
			if (uas == null) {
				uas = AtomicLongMap.create();
				monthYearUACount.put(key, uas);
			}

			if (Arrays.binarySearch(browsers, userAgentName) >= 0) {
				uas.addAndGet(userAgentName, count);
			}
			else if (userAgentName.equals("Mobile Safari")) {
				uas.addAndGet("Safari", count);
			}
			else {
				uas.addAndGet("Other", count);
			}
		}

		List<Map<String, Object>> result = new ArrayList<>();

		for (String yearMonth : monthYearUACount.keySet()) {
			long total = monthYearTotal.get(yearMonth);
			ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
			builder.put("yearMonth", yearMonth);

			for (Map.Entry<String, Long> entry : monthYearUACount.get(yearMonth).asMap()
					.entrySet()) {
				builder.put(
						entry.getKey(),
						new BigDecimal(entry.getValue()).multiply(ONE_HUNDRED).divide(
								new BigDecimal(total), 2, BigDecimal.ROUND_DOWN));
			}

			result.add(builder.build());
		}

		return result;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public List<Map<String, ?>> readOsStats(int queryYear) {
		JPQLQuery query = new JPAQuery(this.entityManager).from(accessLog);
		query.where(accessLog.logIn.year().eq(queryYear));
		query.groupBy(accessLog.operatingSystem);
		List<Tuple> queryResult = query
				.list(accessLog.operatingSystem, accessLog.count());

		List<Map<String, ?>> result = new ArrayList<>();

		for (Tuple tuple : queryResult) {
			String os = tuple.get(accessLog.operatingSystem);
			Long count = tuple.get(accessLog.count());
			result.add(ImmutableMap.of("name", os, "value", count));
		}

		return result;
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void addTestData() {
		if (!this.environment.acceptsProfiles("production")) {

			String[] users = { "admin", "user1", "user2", "user3", "user4", "user5",
					"user6" };
			String[] userAgent = {
					"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.52 Safari/537.36 OPR/15.0.1147.100",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1664.3 Safari/537.36",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",
					"Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25" };

			Random random = new Random();
			int currentYear = LocalDateTime.now().getYear();
			UserAgentStringParser parser = UADetectorServiceFactory
					.getResourceModuleParser();

			for (int i = 0; i < 1000; i++) {
				try {
					AccessLog log = new AccessLog();
					int rnd = random.nextInt(users.length);
					log.setUserName(users[rnd]);

					String ua = userAgent[rnd];
					log.setUserAgent(ua);
					ReadableUserAgent agent = parser.parse(ua);
					log.setUserAgentName(agent.getName());
					log.setUserAgentVersion(agent.getVersionNumber().getMajor());
					log.setOperatingSystem(agent.getOperatingSystem().getFamilyName());

					log.setSessionId(String.valueOf(i));

					LocalDateTime logIn = LocalDateTime.of(
							currentYear - random.nextInt(2), random.nextInt(12) + 1,
							random.nextInt(28) + 1, random.nextInt(24),
							random.nextInt(60), random.nextInt(60));
					log.setLogIn(logIn);
					log.setLogOut(logIn.plusMinutes(random.nextInt(120)).plusSeconds(
							random.nextInt(60)));

					this.entityManager.persist(log);
				}
				catch (IllegalArgumentException iae) {
					// do nothing here
				}
			}
		}
	}

}
