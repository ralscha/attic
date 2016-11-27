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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import ch.rasc.edsutil.bean.ValidationMessages;

public abstract class ValidationUtil {

	public static <T> List<ValidationMessages> validateEntity(Validator validator,
			T entity, Class<?>... groups) {

		Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity,
				groups);
		Map<String, List<String>> fieldMessages = new HashMap<>();
		if (!constraintViolations.isEmpty()) {
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				String property = constraintViolation.getPropertyPath().toString();
				List<String> messages = fieldMessages.get(property);
				if (messages == null) {
					messages = new ArrayList<>();
					fieldMessages.put(property, messages);
				}
				messages.add(constraintViolation.getMessage());
			}
		}
		List<ValidationMessages> validationErrors = new ArrayList<>();
		fieldMessages.forEach((k, v) -> {
			ValidationMessages errors = new ValidationMessages();
			errors.setField(k);
			errors.setMessages(v.toArray(new String[v.size()]));
			validationErrors.add(errors);
		});

		return validationErrors;
	}

}
