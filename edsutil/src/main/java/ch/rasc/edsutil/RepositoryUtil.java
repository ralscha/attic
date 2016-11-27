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
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.ralscha.extdirectspring.bean.SortInfo;

public abstract class RepositoryUtil {

	public static Pageable createPageable(ExtDirectStoreReadRequest request) {

		List<Order> orders = new ArrayList<>();
		for (SortInfo sortInfo : request.getSorters()) {

			if (sortInfo.getDirection() == SortDirection.ASCENDING) {
				orders.add(new Order(Direction.ASC, sortInfo.getProperty()));
			}
			else {
				orders.add(new Order(Direction.DESC, sortInfo.getProperty()));
			}
		}

		// Ext JS pages starts with 1, Spring Data starts with 0
		int page = Math.max(request.getPage() - 1, 0);

		if (orders.isEmpty()) {
			return new PageRequest(page, request.getLimit());
		}

		Sort sort = new Sort(orders);
		return new PageRequest(page, request.getLimit(), sort);

	}

}