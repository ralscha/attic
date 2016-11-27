package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;
import static ch.rasc.eds.starter.entity.QAccessLog.accessLog;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.eds.starter.config.security.JpaUserDetails;
import ch.rasc.eds.starter.entity.AccessLog;
import ch.rasc.eds.starter.entity.PersistentLogin;
import ch.rasc.eds.starter.entity.QPersistentLogin;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;
import eu.bitwalker.useragentutils.UserAgent;

@Service
public class AccessLogService {

	public static Map<Long, AccessLog> CURRENT_LOGGED_IN_USERS = new ConcurrentHashMap<>();

	private final Environment environment;

	private final JPAQueryFactory jpaQueryFactory;

	private final GeoIPCityService geoIpCityService;

	@Autowired
	public AccessLogService(Environment environment, JPAQueryFactory jpaQueryFactory,
			GeoIPCityService geoIpCityService) {
		this.environment = environment;
		this.jpaQueryFactory = jpaQueryFactory;
		this.geoIpCityService = geoIpCityService;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<AccessLog> read(ExtDirectStoreReadRequest request) {

		JPAQuery<AccessLog> query = this.jpaQueryFactory.selectFrom(accessLog);

		if (!request.getFilters().isEmpty()) {
			StringFilter loginNameFilter = (StringFilter) request.getFilters().iterator()
					.next();
			String loginName = loginNameFilter.getValue();
			query.where(accessLog.loginName.containsIgnoreCase(loginName));
		}

		QuerydslUtil.addPagingAndSorting(query, request, AccessLog.class, accessLog,
				Collections.<String, String> emptyMap(),
				Collections.singleton("browser"));

		QueryResults<AccessLog> searchResult = query.fetchResults();

		return new ExtDirectStoreResult<>(searchResult.getTotal(),
				searchResult.getResults());
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public Collection<Map<String, Integer>> readAccessLogYears() {
		JPAQuery<Integer> query = this.jpaQueryFactory
				.select(accessLog.loginTimestamp.year()).from(accessLog).distinct();
		List<Integer> years = query.fetch();
		return years.stream().map(year -> Collections.singletonMap("year", year))
				.collect(Collectors.toList());
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void deleteAll() {
		this.jpaQueryFactory.delete(accessLog).execute();
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public long[][] readUserAgentsStats(int queryYear) {
		DateTimePath<ZonedDateTime> loginTimestamp = accessLog.loginTimestamp;
		JPAQuery<Tuple> query = this.jpaQueryFactory.select(loginTimestamp.month(),
				accessLog.userAgentName, accessLog.count()).from(accessLog);

		query.where(loginTimestamp.year().eq(queryYear));
		query.groupBy(loginTimestamp.month(), accessLog.userAgentName);
		query.orderBy(loginTimestamp.month().asc());
		List<Tuple> queryResult = query.fetch();

		String[] browsers = new String[] { "Chrome", "Firefox", "IE", "Safari", "Other" };
		long[][] result = new long[5][12];

		for (Tuple tuple : queryResult) {
			Integer month = tuple.get(loginTimestamp.month());
			String userAgentName = tuple.get(accessLog.userAgentName);
			Long count = tuple.get(accessLog.count());

			int ix = Arrays.binarySearch(browsers, userAgentName);
			if (ix < 0) {
				ix = 4;
			}
			result[ix][month - 1] += count;
		}

		return result;
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public List<Object[]> readOsStats(int queryYear) {
		List<Tuple> queryResult = this.jpaQueryFactory
				.select(accessLog.operatingSystem, accessLog.count()).from(accessLog)
				.where(accessLog.loginTimestamp.year().eq(queryYear))
				.groupBy(accessLog.operatingSystem).fetch();

		return queryResult.stream().map(t -> new Object[] {
				t.get(accessLog.operatingSystem), t.get(accessLog.count()) })
				.collect(Collectors.toList());
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public List<AccessLog> last10Logs(
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails) {
		return this.jpaQueryFactory.selectFrom(accessLog)
				.where(accessLog.loginName.eq(jpaUserDetails.getUsername()))
				.orderBy(accessLog.loginTimestamp.desc()).limit(10).fetch();
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public List<PersistentLogin> readPersistentLogins(
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails) {
		List<PersistentLogin> persistentLogins = this.jpaQueryFactory
				.selectFrom(QPersistentLogin.persistentLogin)
				.where(QPersistentLogin.persistentLogin.user.id
						.eq(jpaUserDetails.getUserDbId()))
				.fetch();

		persistentLogins.forEach(p -> {
			p.setLocation(this.geoIpCityService.lookupCity(p.getIpAddress()));
			String ua = p.getUserAgent();
			if (StringUtils.hasText(ua)) {
				UserAgent userAgent = UserAgent.parseUserAgentString(ua);
				p.setUserAgentName(userAgent.getBrowser().getGroup().getName());
				p.setUserAgentVersion(userAgent.getBrowserVersion().getMajorVersion());
				p.setOperatingSystem(userAgent.getOperatingSystem().getName());
			}
		});

		return persistentLogins;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	public Collection<AccessLog> readLoggedInUsers() {
		return AccessLogService.CURRENT_LOGGED_IN_USERS.values();
	}

	@ExtDirectMethod(STORE_MODIFY)
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public void destroyPersistentLogin(String series,
			@AuthenticationPrincipal JpaUserDetails jpaUserDetails) {
		this.jpaQueryFactory.delete(QPersistentLogin.persistentLogin)
				.where(QPersistentLogin.persistentLogin.series.eq(series)
						.and(QPersistentLogin.persistentLogin.user.id
								.eq(jpaUserDetails.getUserDbId())))
				.execute();
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void addTestData() {
		if (!this.environment.acceptsProfiles("default")) {

			String[] users = { "admin@starter.com", "user1@starter.com",
					"user2@starter.com", "user3@starter.com", "user4@starter.com",
					"user5@starter.com", "user6@starter.com" };
			String[] userAgent = {
					"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0Mozilla/5.0 (Windows NT 6.3; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.52 Safari/537.36 OPR/15.0.1147.100",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1664.3 Safari/537.36",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",
					"Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25" };
			String[] ipAddresses = { "64.4.11.42", "173.194.113.145", "31.13.93.129",
					"23.67.141.15", "54.225.87.37" };
			String[] locations = { "Redmond,United States", "Mountain View,United States",
					"Ireland", "Amsterdam,Netherlands", "Ashburn,United States" };

			Random random = new Random();
			int currentYear = ZonedDateTime.now(ZoneOffset.UTC).getYear();

			for (int i = 0; i < 1000; i++) {
				AccessLog log = new AccessLog();
				int rnd = random.nextInt(users.length);
				log.setLoginName(users[rnd]);

				String ua = userAgent[rnd];
				log.setUserAgent(ua);
				UserAgent agent = UserAgent.parseUserAgentString(ua);
				log.setUserAgentName(agent.getBrowser().getGroup().getName());
				log.setUserAgentVersion(agent.getBrowserVersion().getMajorVersion());
				log.setOperatingSystem(agent.getOperatingSystem().getName());

				log.setSessionId(String.valueOf(i));

				ZonedDateTime loginTimestamp = ZonedDateTime.of(
						currentYear - random.nextInt(2), random.nextInt(12) + 1,
						random.nextInt(28) + 1, random.nextInt(24), random.nextInt(60),
						random.nextInt(60), 0, ZoneOffset.UTC);
				log.setLoginTimestamp(loginTimestamp);

				int l = random.nextInt(ipAddresses.length);
				log.setIpAddress(ipAddresses[l]);
				log.setLocation(locations[l]);

				this.jpaQueryFactory.getEntityManager().persist(log);
			}
		}
	}

}
