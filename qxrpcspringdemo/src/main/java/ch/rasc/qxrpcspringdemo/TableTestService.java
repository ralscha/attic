/**
 * Copyright 2014-2014 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.qxrpcspringdemo;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.rasc.qxrpcspring.annotation.QxrpcMethod;

@Service
public class TableTestService {

	public static class YearDto {

		public final int year;
		public final boolean leap;

		public YearDto(int year) {
			this.year = year;
			this.leap = Year.isLeap(year);
		}

		public int getYear() {
			return this.year;
		}

		public boolean isLeap() {
			return this.leap;
		}

	}

	@QxrpcMethod
	public int getRowCount() {
		return 121;
	}

	@QxrpcMethod
	public List<YearDto> getRowData(int firstRow, int lastRow, String sortIndex,
			SortOrder sortOrder) {

		List<YearDto> years = new ArrayList<>();
		for (int i = firstRow + 1900; i <= Math.min(2020, lastRow + 1900); i++) {
			years.add(new YearDto(i));
		}

		if (sortIndex != null) {
			Comparator<YearDto> comparing = null;

			if ("year".equalsIgnoreCase(sortIndex)) {
				comparing = Comparator.comparing(YearDto::getYear);
			}
			else if ("leap".equalsIgnoreCase(sortIndex)) {
				comparing = Comparator.comparing(YearDto::isLeap);
			}

			if (comparing != null) {
				if (sortOrder == SortOrder.DESC) {
					comparing = comparing.reversed();
				}

				years.sort(comparing);
			}
		}

		return years;
	}

}
