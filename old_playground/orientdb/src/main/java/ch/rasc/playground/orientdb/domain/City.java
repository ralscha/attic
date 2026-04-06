/*
 * Copyright 2010-2012 Luca Garulli (l.garulli--at--orientechnologies.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.playground.orientdb.domain;

import javax.persistence.Id;
import javax.persistence.Version;

public class City {
	@Id
	private Long id;

	@Version
	private Long version;

	private String name;
	private Country country;

	public City() {
	}

	public City(String iName) {
		this.name = iName;
	}

	public City(String iName, Country iCountry) {
		this.country = iCountry;
		this.name = iName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return this.country;
	}

	public Object setCountry(Country iCountry) {
		return this.country = iCountry;
	}

	public Long getId() {
		return this.id;
	}

	public Long getVersion() {
		return this.version;
	}
}