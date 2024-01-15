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

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.format.annotation.DateTimeFormat;

import ch.rasc.qxrpcspring.annotation.QxrpcService;

@QxrpcService
public class TestService {

	public String echo(String input) {
		return "Client said: " + input;
	}

	public int sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds * 2);
		}
		catch (InterruptedException e) {
			// ignore this
		}
		return seconds;
	}

	public Map<String, Object> getCurrentTimestamp() {
		Map<String, Object> retVal = new HashMap<>();
		retVal.put("now", new Long(System.currentTimeMillis()));
		retVal.put("json", new Date());
		return retVal;
	}

	public int getInteger() {
		return 1;
	}

	public boolean isInteger(@SuppressWarnings("unused") int param) {
		return true; // if we get here, we are guaranteed to have
						// an integer
	}

	public String getString() {
		return "Hello world";
	}

	public boolean isString(String param) {
		return param != null;
	}

	public Object getNull() {
		return null;
	}

	public boolean isNull(Object param) {
		return param == null;
	}

	public int[] getArrayInteger() {
		return new int[] { 1, 2, 3, 4 };
	}

	public String[] getArrayString() {
		return new String[] { "one", "two", "three", "four" };
	}

	public boolean isArray(@SuppressWarnings("unused") Object[] array) {
		return true; // if we get here, we are guaranteed to have an
						// array
	}

	public double getFloat() {
		return 1.0 / 3;
	}

	public boolean isFloat(@SuppressWarnings("unused") double param) {
		return true;
	}

	public boolean getTrue() {
		return true;
	}

	public boolean getFalse() {
		return false;
	}

	public boolean isBoolean(@SuppressWarnings("unused") boolean param) {
		return true;
	}

	public String getOldDate(String date) {
		System.out.println(date);
		return date;
	}

	public String getDate(
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX") Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		return sdf.format(date);
	}

	public Object getParam(Object param) {
		return param;
	}

	public Object[] getParams(Object param1, Object param2, Object param3, Object param4,
			Object param5, Object param6, Object param7) {

		return new Object[] { param1, param2, param3, param4, param5, param6, param7 };
	}

	public void getError() {
		throw new NullPointerException("Demo error");
	}

	public Map<String, Object> getObject() {
		return Collections.emptyMap();
	}

	public boolean isObject(Object param) {
		return param != null;
	}

}