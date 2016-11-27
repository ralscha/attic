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
package ch.rasc.edsutil.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;

@JsonInclude(Include.NON_NULL)
public class ValidationMessagesResult<T> extends ExtDirectStoreResult<T> {
	private List<ValidationMessages> validations;

	public ValidationMessagesResult(T record) {
		super(record);
	}

	public ValidationMessagesResult(T record, List<ValidationMessages> validations) {
		super(record);
		setValidations(validations);
	}

	public List<ValidationMessages> getValidations() {
		return this.validations;
	}

	public void setValidations(List<ValidationMessages> validations) {
		this.validations = validations;
		if (this.validations != null && !this.validations.isEmpty()) {
			setSuccess(Boolean.FALSE);
		}
	}

}
