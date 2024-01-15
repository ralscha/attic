package ch.rasc.changelog.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.changelog.entity.Configuration;
import ch.rasc.changelog.entity.QConfiguration;

@Service
public class ConfigService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public LinkedList<String> synchronize(Set<String> modifiedConfigurations) {
		Configuration dbConfiguration = getPersistentConfiguration();
		if (modifiedConfigurations.size() > 0) {
			for (Iterator<String> iterator = modifiedConfigurations.iterator(); iterator
					.hasNext();) {
				String key = iterator.next();
				String value = iterator.next();
				dbConfiguration.getConfigurationMap().put(key, value);
			}
			dbConfiguration.updateValues();
			updateLogger(dbConfiguration);
		}
		LinkedList<String> result = Lists.newLinkedList();
		for (Map.Entry<String, String> entry : dbConfiguration.getConfigurationMap()
				.entrySet()) {
			result.add(entry.getKey());
			result.add(entry.getValue());
		}
		return result;
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public String getValue(String key) {
		Configuration configuration = getPersistentConfiguration();
		return configuration.getValue(key);
	}

	private Configuration getPersistentConfiguration() {
		List<Configuration> dbConfigurations = new JPAQuery(this.entityManager)
				.from(QConfiguration.configuration).list(QConfiguration.configuration);
		Configuration dbConfiguration = null;
		if (dbConfigurations.size() > 0) {
			dbConfiguration = dbConfigurations.get(0);
		}
		else {
			dbConfiguration = new Configuration();
			this.entityManager.persist(dbConfiguration);
		}
		return dbConfiguration;
	}

	private static void updateLogger(Configuration dbConfiguration) {
		if (null != dbConfiguration) {
			String levelString = dbConfiguration.getValue("logLevel");
			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
			ch.qos.logback.classic.Logger logger = lc.getLogger("ch.ess.changelog");
			Level level = Level.toLevel(levelString);
			if (level != null) {
				logger.setLevel(level);
			}
		}
	}
}
