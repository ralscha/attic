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

import java.math.BigDecimal;
import java.time.LocalDate;

public class User {
	private long id;
	private String name;
	private LocalDate dayOfBirth;
	private BigDecimal salary;

	public User(long id, String name, LocalDate dayOfBirth, BigDecimal salary) {
		this.id = id;
		this.name = name;
		this.dayOfBirth = dayOfBirth;
		this.salary = salary;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDayOfBirth() {
		return this.dayOfBirth;
	}

	public void setDayOfBirth(LocalDate dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public BigDecimal getSalary() {
		return this.salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

}
