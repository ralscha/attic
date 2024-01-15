package ch.rasc.proto.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.edsutil.ValidationUtil;
import ch.rasc.edsutil.bean.ValidationMessages;
import ch.rasc.edsutil.bean.ValidationMessagesResult;
import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.entity.Person;

@Service
@Lazy
@PreAuthorize("isAuthenticated()")
public class PersonService {

	private final Validator validator;

	private final DbManager dbManager;

	@Autowired
	public PersonService(DbManager dbManager, Validator validator) {
		this.dbManager = dbManager;
		this.validator = validator;
	}

	@ExtDirectMethod(STORE_READ)
	public ExtDirectStoreResult<Person> read() {
		return this.dbManager.runInTx(db -> {
			Collection<Person> persons = DbManager.getAll(db, Person.class);
			return new ExtDirectStoreResult<>(persons.size(), persons);
		});
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<Person> destroy(Person destroyPerson) {
		ExtDirectStoreResult<Person> result = new ExtDirectStoreResult<>();
		this.dbManager.remove(destroyPerson);
		result.setSuccess(Boolean.TRUE);
		return result;
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ValidationMessagesResult<Person> update(Person updatedEntity, Locale locale) {
		List<ValidationMessages> violations = validateEntity(updatedEntity, locale);
		if (violations.isEmpty()) {
			this.dbManager.put(updatedEntity);
			return new ValidationMessagesResult<>(updatedEntity);
		}

		ValidationMessagesResult<Person> result = new ValidationMessagesResult<>(
				updatedEntity);
		result.setValidations(violations);
		return result;
	}

	private List<ValidationMessages> validateEntity(Person person,
			@SuppressWarnings("unused") Locale locale) {
		List<ValidationMessages> validations = ValidationUtil.validateEntity(
				this.validator, person);

		return validations;
	}

}
