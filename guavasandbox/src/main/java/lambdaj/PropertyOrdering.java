/**
 * Copyright 2010-2011 Ralph Schaer <ralphschaer@gmail.com>
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
package lambdaj;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.google.common.collect.Ordering;

public class PropertyOrdering<T> extends Ordering<T> {

	private final Expression readPropertyExpression;

	EvaluationContext context = new StandardEvaluationContext();

	public PropertyOrdering(Expression readPropertyExpression) {
		this.readPropertyExpression = readPropertyExpression;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(T o1, T o2) {
		Object value1 = readPropertyExpression.getValue(context, o1);
		Object value2 = readPropertyExpression.getValue(context, o2);
		return ((Comparable<Object>) value1).compareTo(value2);
	}

}
