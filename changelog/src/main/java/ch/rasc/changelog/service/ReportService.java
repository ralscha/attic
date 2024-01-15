package ch.rasc.changelog.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.changelog.entity.ChangeType;
import ch.rasc.changelog.entity.QChange;

@Service
public class ReportService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MessageSource messageSource;

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public List<Map<String, Object>> loadTypeChartData(Locale locale) {

		List<Map<String, Object>> result = new ArrayList<>();

		JPQLQuery query = new JPAQuery(this.entityManager).from(QChange.change);
		query.groupBy(QChange.change.typ);
		List<Tuple> types = query.list(QChange.change.typ, QChange.change.typ.count());

		for (Tuple typ : types) {
			Map<String, Object> data = new HashMap<>();
			ChangeType type = typ.get(0, ChangeType.class);
			String key = null;
			if (type == ChangeType.FIX) {
				key = "change_typ_fix";
			}
			else if (type == ChangeType.ENHANCEMENT) {
				key = "change_typ_enhancement";
			}
			else if (type == ChangeType.NEW) {
				key = "change_typ_new";
			}
			data.put("type",
					this.messageSource.getMessage(key, null, type.name(), locale));
			data.put("number", typ.get(1, Long.class));
			result.add(data);
		}
		return result;

	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public List<Map<String, Object>> loadChangeYears() {
		List<Map<String, Object>> result = new ArrayList<>();

		JPQLQuery query = new JPAQuery(this.entityManager).from(QChange.change);
		query.groupBy(QChange.change.implementationDate.year());
		query.orderBy(QChange.change.implementationDate.year().desc());
		List<Integer> years = query.list(QChange.change.implementationDate.year());
		for (Integer year : years) {
			Map<String, Object> data = new HashMap<>();
			data.put("year", year);
			result.add(data);
		}
		return result;
	}

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public List<Map<String, Object>> loadTypePerYearChartData(int year) {

		List<Map<String, Object>> result = new ArrayList<>();

		JPQLQuery query = new JPAQuery(this.entityManager).from(QChange.change);
		query.where(QChange.change.implementationDate.year().eq(year));
		query.groupBy(QChange.change.typ);
		query.groupBy(QChange.change.implementationDate.month());
		List<Tuple> types = query.list(QChange.change.typ,
				QChange.change.implementationDate.month(), QChange.change.typ.count());

		Map<Integer, Count> monthMap = Maps.newTreeMap();

		for (Tuple typ : types) {

			ChangeType type = typ.get(0, ChangeType.class);
			Integer month = typ.get(1, Integer.class);
			Long count = typ.get(2, Long.class);

			Count c = monthMap.get(month);
			if (c == null) {
				c = new Count();
				monthMap.put(month, c);
			}

			if (type == ChangeType.FIX) {
				c.fix = count;
			}
			else if (type == ChangeType.ENHANCEMENT) {
				c.enhancement = count;
			}
			else if (type == ChangeType.NEW) {
				c.newChange = count;
			}

		}

		for (Map.Entry<Integer, Count> entry : monthMap.entrySet()) {
			Map<String, Object> data = new HashMap<>();

			data.put("month", entry.getKey());
			data.put("fix", entry.getValue().fix);
			data.put("enhancement", entry.getValue().enhancement);
			data.put("new", entry.getValue().newChange);

			result.add(data);
		}

		return result;

	}

	final static class Count {
		Long fix;

		Long enhancement;

		Long newChange;
	}

}
