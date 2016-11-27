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

import java.util.Comparator;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class PropertyComparator<T> implements Comparator<T> {
	private final static SpelExpressionParser parser = new SpelExpressionParser(
			new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, null));

	private final Expression readPropertyExpression;
	private final boolean ignoreCase;

	public PropertyComparator(String property) {
		this.readPropertyExpression = parser.parseExpression(property);
		this.ignoreCase = false;
	}

	public PropertyComparator(String property, boolean ignoreCase) {
		this.readPropertyExpression = parser.parseExpression(property);
		this.ignoreCase = ignoreCase;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(T o1, T o2) {
		Object left = this.readPropertyExpression.getValue(o1);
		Object right = this.readPropertyExpression.getValue(o2);

		if (left == right) {
			return 0;
		}
		if (left == null) {
			return -1;
		}
		if (right == null) {
			return 1;
		}

		if (left instanceof String && this.ignoreCase) {
			return ((String) left).compareToIgnoreCase((String) right);
		}

		return ((Comparable<Object>) left).compareTo(right);
	}

}
