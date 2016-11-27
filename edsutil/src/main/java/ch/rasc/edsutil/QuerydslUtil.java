/**
 * Copyright 2013-2016 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.edsutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.ralscha.extdirectspring.bean.SortInfo;

public abstract class QuerydslUtil {

	public static void addPagingAndSorting(JPQLQuery<?> query,
			ExtDirectStoreReadRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase) {
		addPagingAndSorting(query, request, clazz, entityPathBase,
				Collections.<String, String> emptyMap(), Collections.<String> emptySet());
	}

	public static void addSorting(JPQLQuery<?> query, ExtDirectStoreReadRequest request,
			Class<?> clazz, EntityPathBase<?> entityPathBase) {
		addSorting(query, request, clazz, entityPathBase,
				Collections.<String, String> emptyMap(), Collections.<String> emptySet());
	}

	public static void addPagingAndSorting(JPQLQuery<?> query,
			ExtDirectStoreReadRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase, Map<String, String> mapGuiColumn2Dbfield,
			Set<String> sortIgnoreProperties) {

		if (request.getStart() != null && request.getLimit() != null
				&& request.getLimit() > 0) {
			query.offset(request.getStart()).limit(request.getLimit());
		}

		addSorting(query, request, clazz, entityPathBase, mapGuiColumn2Dbfield,
				sortIgnoreProperties);
	}

	public static void addSorting(JPQLQuery<?> query, ExtDirectStoreReadRequest request,
			Class<?> clazz, EntityPathBase<?> entityPathBase,
			Map<String, String> mapGuiColumn2Dbfield, Set<String> sortIgnoreProperties) {

		if (!request.getSorters().isEmpty()) {
			query.orderBy(createOrderSpecifiers(request, clazz, entityPathBase,
					mapGuiColumn2Dbfield, sortIgnoreProperties));
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static OrderSpecifier[] createOrderSpecifiers(
			ExtDirectStoreReadRequest request, Class<?> clazz,
			EntityPathBase<?> entityPathBase, Map<String, String> mapGuiColumn2Dbfield,
			Set<String> sortIgnoreProperties) {

		List<OrderSpecifier> orders;

		if (!request.getSorters().isEmpty()) {
			orders = new ArrayList<>();
			PathBuilder<?> entityPath = new PathBuilder<>(clazz,
					entityPathBase.getMetadata());
			for (SortInfo sortInfo : request.getSorters()) {

				if (!sortIgnoreProperties.contains(sortInfo.getProperty())) {
					Order order;
					if (sortInfo.getDirection() == SortDirection.ASCENDING) {
						order = Order.ASC;
					}
					else {
						order = Order.DESC;
					}

					String property = mapGuiColumn2Dbfield.get(sortInfo.getProperty());
					if (property == null) {
						property = sortInfo.getProperty();
					}

					orders.add(new OrderSpecifier(order, entityPath.get(property)));
				}
			}

		}
		else {
			orders = Collections.emptyList();
		}

		return orders.toArray(new OrderSpecifier[orders.size()]);
	}
}
