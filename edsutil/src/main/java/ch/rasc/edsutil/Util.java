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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class Util {
	public static Date ldToDate(LocalDate ld) {
		return ldtToDate(ld.atStartOfDay());
	}

	public static Date ldtToDate(LocalDateTime ldt) {
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate dateToLd(Date date) {
		return dateToLdt(date).toLocalDate();
	}

	public static LocalDateTime dateToLdt(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	public static boolean isValidDomain(String domain) {
		try {
			Hashtable<String, String> env = new Hashtable<>();
			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
			DirContext ictx = new InitialDirContext(env);

			Attributes attrs = ictx.getAttributes(domain, new String[] { "MX", "A" });

			Attribute attr = attrs.get("MX");
			if (attr != null) {
				return true;
			}
			attr = attrs.get("A");
			if (attr != null) {
				return true;
			}
		}
		catch (NamingException e) {
			// ignore error and return true
			return true;
		}

		return false;

	}
}
