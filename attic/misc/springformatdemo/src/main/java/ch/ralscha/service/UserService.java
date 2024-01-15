/**
 * Copyright 2010 Ralph Schaer <ralphschaer@gmail.com>
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

package ch.ralscha.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.core.io.Resource;

import au.com.bytecode.opencsv.CSVReader;
import ch.ralscha.model.User;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

@Named
public class UserService {

	@Inject
	private Resource randomdata;

	private Multimap<String, User> users;

	@PostConstruct
	public void readData() throws IOException, ParseException {
		users = HashMultimap.create();
		InputStream is = randomdata.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		CSVReader reader = new CSVReader(br, '|');
		String[] line;
		while ((line = reader.readNext()) != null) {
			User user = new User(line);
			users.put(user.getBirthDayLocalDate().toString("yyyyMMdd"), user);
		}

		br.close();
		is.close();
	}

	public List<User> findUsers(final LocalDate birthDay) {
		if (birthDay == null) {
			return ImmutableList.copyOf(users.values());
		}
		return ImmutableList.copyOf(users.get(birthDay.toString("yyyyMMdd")));
	}

	public List<User> findUsers(final Date birthDay) {
		return findUsers(new DateTime(birthDay).toLocalDate());
	}

	public List<User> findUsers(final Calendar birthDay) {
		return findUsers(new DateTime(birthDay).toLocalDate());
	}

	public List<User> findUsers(final String birthDay) throws ParseException {
		return findUsers(ISODateTimeFormat.date().parseDateTime(birthDay).toLocalDate());
	}

}
