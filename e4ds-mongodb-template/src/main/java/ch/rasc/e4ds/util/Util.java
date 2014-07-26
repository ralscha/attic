package ch.rasc.e4ds.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.ralscha.extdirectspring.bean.SortInfo;

import com.google.common.collect.Lists;

public class Util {

	private Util() {
		// do not instantiate this class
	}

	public static Pageable createPageRequest(ExtDirectStoreReadRequest request) {
		return createPageRequest(request, Collections.<String, String> emptyMap());
	}

	public static Pageable createPageRequest(ExtDirectStoreReadRequest request,
			final Map<String, String> mapGuiColumn2Dbfield) {

		List<Order> orders = Lists.newArrayList();
		for (SortInfo sortInfo : request.getSorters()) {

			String property = mapGuiColumn2Dbfield.get(sortInfo.getProperty());
			if (property == null) {
				property = sortInfo.getProperty();
			}

			if (sortInfo.getDirection() == SortDirection.ASCENDING) {
				orders.add(new Order(Direction.ASC, property));
			} else {
				orders.add(new Order(Direction.DESC, property));
			}
		}

		int page = Math.max(request.getPage() - 1, 0);

		if (orders.isEmpty()) {
			return new PageRequest(page, request.getLimit());
		}

		Sort sort = new Sort(orders);
		return new PageRequest(page, request.getLimit(), sort);

	}

	public static Query applyPagination(Query query, Pageable pageable) {
		if (pageable == null) {
			return query;
		}

		query.limit(pageable.getPageSize());
		query.skip(pageable.getOffset());

		return applySorting(query, pageable.getSort());
	}

	public static Query applySorting(Query query, Sort sort) {

		if (sort == null) {
			return query;
		}

		org.springframework.data.mongodb.core.query.Sort bSort = query.sort();

		for (Order order : sort) {
			bSort.on(order.getProperty(), toOrder(order));
		}

		return query;
	}

	public static org.springframework.data.mongodb.core.query.Order toOrder(Order order) {
		return order.isAscending() ? org.springframework.data.mongodb.core.query.Order.ASCENDING
				: org.springframework.data.mongodb.core.query.Order.DESCENDING;
	}

}
