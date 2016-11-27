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

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.ParseException;

public class PropertyComparatorTest {

	private List<User> users;
	private User user1;
	private User user2;
	private User user3;
	private User user4;
	private User user5;

	@Before
	public void setup() {
		this.users = new ArrayList<>();
		this.user1 = new User(1, "Ralph", LocalDate.of(1989, 1, 23),
				new BigDecimal("100.05"));
		this.users.add(this.user1);
		this.user3 = new User(3, "Lamar", LocalDate.of(1989, 2, 15),
				new BigDecimal("100.15"));
		this.users.add(this.user3);
		this.user2 = new User(2, "jeremy", LocalDate.of(1989, 1, 22),
				new BigDecimal("100.25"));
		this.users.add(this.user2);
		this.user5 = new User(5, "Peter", LocalDate.of(1989, 12, 12),
				new BigDecimal("99.25"));
		this.users.add(this.user5);
		this.user4 = new User(4, "Branden", LocalDate.of(1989, 6, 7),
				new BigDecimal("99.15"));
		this.users.add(this.user4);
	}

	@Test(expected = ParseException.class)
	public void testThrowException() {
		@SuppressWarnings("unused")
		PropertyComparator<String> pc = new PropertyComparator<>("....");
	}

	@Test
	public void testSortLong() {
		PropertyComparator<User> pc = new PropertyComparator<>("id");
		this.users.sort(pc);
		assertThat(this.users).containsExactly(this.user1, this.user2, this.user3,
				this.user4, this.user5);
	}

	@Test
	public void testSortString() {
		PropertyComparator<User> pc = new PropertyComparator<>("name");
		this.users.sort(pc);
		assertThat(this.users).containsExactly(this.user4, this.user3, this.user5,
				this.user1, this.user2);

		pc = new PropertyComparator<>("name", true);
		this.users.sort(pc);
		assertThat(this.users).containsExactly(this.user4, this.user2, this.user3,
				this.user5, this.user1);
	}

	@Test
	public void testSortBigDecimal() {
		PropertyComparator<User> pc = new PropertyComparator<>("salary");
		this.users.sort(pc);
		assertThat(this.users).containsExactly(this.user4, this.user5, this.user1,
				this.user3, this.user2);
	}

	@Test
	public void testSortLocalDate() {
		PropertyComparator<User> pc = new PropertyComparator<>("dayOfBirth");
		this.users.sort(pc);
		assertThat(this.users).containsExactly(this.user2, this.user1, this.user3,
				this.user4, this.user5);
	}

}
