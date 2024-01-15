package ch.rasc.todo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import ch.rasc.todo.config.Db;
import ch.rasc.todo.dto.ExtRequest;
import ch.rasc.todo.dto.Sort;

@Component
public class QuerydslUtil {
	
	@Autowired
	private Db db;

	private final static ObjectMapper om = new ObjectMapper();

	public void addPagingAndSorting(JPQLQuery<?> query, ExtRequest request,
			Class<?> clazz, EntityPathBase<?> entityPathBase) {
		addPagingAndSorting(query, request, clazz, entityPathBase,
				Collections.<String, String>emptyMap(), Collections.<String>emptySet());
	}

	public void addSorting(JPQLQuery<?> query, ExtRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase) {
		addSorting(query, request, clazz, entityPathBase,
				Collections.<String, String>emptyMap(), Collections.<String>emptySet());
	}

	public void addPagingAndSorting(JPQLQuery<?> query, ExtRequest request,
			Class<?> clazz, EntityPathBase<?> entityPathBase,
			Map<String, String> mapGuiColumn2Dbfield, Set<String> sortIgnoreProperties) {

		if (request.getStart() != null && request.getLimit() != null
				&& request.getLimit() > 0) {
			query.offset(request.getStart()).limit(request.getLimit());
		}

		addSorting(query, request, clazz, entityPathBase, mapGuiColumn2Dbfield,
				sortIgnoreProperties);
	}

	public void addSorting(JPQLQuery<?> query, ExtRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase, Map<String, String> mapGuiColumn2Dbfield,
			Set<String> sortIgnoreProperties) {

		if (request.getSort() != null && !request.getSort().isEmpty()) {
			query.orderBy(createOrderSpecifiers(request, clazz, entityPathBase,
					mapGuiColumn2Dbfield, sortIgnoreProperties));
		}

	}
	
	// PostgreSQL
	private static final String REGEXPG = "substring(%s, '\"%s\"[ :]+((?=\\[)\\[[^]]*\\]|(?=\\{)\\{[^\\}]*\\}|\\\"[^\"]*\\\")')";

	// SQL Server
	private static final String REGEXSQL = "substring(%1$s, patindex('\"%2$s\":\"',%1$s), len(%1$s))";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OrderSpecifier[] createOrderSpecifiers(ExtRequest request,
			Class<?> clazz, EntityPathBase<?> entityPathBase,
			Map<String, String> mapGuiColumn2Dbfield, Set<String> sortIgnoreProperties) {

		List<OrderSpecifier> orders;

		if (!request.getSort().isEmpty()) {
			orders = new ArrayList<>();
			PathBuilder<?> entityPath = new PathBuilder<>(clazz,
					entityPathBase.getMetadata());
			for (Sort sortInfo : request.getSortObjects(om)) {

				if (!sortIgnoreProperties.contains(sortInfo.getProperty())) {
					Order order;
					if (sortInfo.getDirection().equals("ASC")) {
						order = Order.ASC;
					}
					else {
						order = Order.DESC;
					}

					String property = mapGuiColumn2Dbfield.get(sortInfo.getProperty());
					if (property == null) {
						property = sortInfo.getProperty();
					}
					
					if("title".equals(property)) {
						String sql = null;
						if(db.isSqlServer()) {
							sql = String.format(REGEXSQL, property, "fr");
						} else {
							sql = String.format(REGEXPG, property, "fr");
						}
						orders.add(new OrderSpecifier(order, Expressions.stringPath(sql)));
					} else {
						orders.add(new OrderSpecifier(order, entityPath.get(property)));
					}
				}
			}

		}
		else {
			orders = Collections.emptyList();
		}

		return orders.toArray(new OrderSpecifier[orders.size()]);
	}
}
