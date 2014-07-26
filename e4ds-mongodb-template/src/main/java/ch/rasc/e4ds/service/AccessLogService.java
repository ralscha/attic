package ch.rasc.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.e4ds.domain.AccessLog;
import ch.rasc.e4ds.util.Util;

@Service
public class AccessLogService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ExtDirectStoreReadResult<AccessLog> load(ExtDirectStoreReadRequest request) {

		Pageable pageRequest = Util.createPageRequest(request);

		Query query = null;
		if (!request.getFilters().isEmpty()) {
			StringFilter userNameFilter = (StringFilter) request.getFilters().iterator().next();
			String userName = userNameFilter.getValue();
			query = Query.query(Criteria.where("userName").is(userName));
		} else {
			query = new Query();
		}

		Long count = mongoTemplate.count(query, AccessLog.class);
		Util.applyPagination(query, pageRequest);
		List<AccessLog> accessLogs = mongoTemplate.find(query, AccessLog.class);

		return new ExtDirectStoreReadResult<>(count.intValue(), accessLogs);
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteAll() {
		mongoTemplate.remove(new Query(), AccessLog.class);
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void addTestData() {
		String[] users = { "admin", "user" };
		Random random = new Random();

		for (int i = 0; i < 1000; i++) {
			try {
				AccessLog accessLog = new AccessLog();
				accessLog.setUserName(users[random.nextInt(2)]);
				accessLog.setSessionId(RandomStringUtils.randomAlphanumeric(16));

				DateTime logIn = new DateTime(2011, random.nextInt(12) + 1, random.nextInt(31) + 1, random.nextInt(24),
						random.nextInt(60), random.nextInt(60));
				accessLog.setLogIn(logIn);
				accessLog.setLogOut(logIn.plusMinutes(random.nextInt(120)));

				mongoTemplate.save(accessLog);
			} catch (IllegalArgumentException iae) {
				// do nothing here
			}
		}

	}

}
