package ch.rasc.jpa.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorMain {

	public static void main(String[] args) {

		Book book = new Book();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		for (ConstraintViolation<Book> violation : violations) {
			System.out.format("%s: %s%n", violation.getPropertyPath(),
					violation.getMessage());
		}
		System.out.println("PRINTING");
		// Validate for the printing phase
		violations = validator.validate(book, Printing.class);
		for (ConstraintViolation<Book> violation : violations) {
			System.out.format("%s: %s%n", violation.getPropertyPath(),
					violation.getMessage());
		}
		System.out.println("DRAFT");
		// Validate the Draft phases
		violations = validator.validate(book, Draft.class);
		for (ConstraintViolation<Book> violation : violations) {
			System.out.format("%s: %s%n", violation.getPropertyPath(),
					violation.getMessage());
		}

		Author a = new Author();
		System.out.println("Author: Default");
		Set<ConstraintViolation<Author>> aviolations = validator.validate(a);
		for (ConstraintViolation<Author> violation : aviolations) {
			System.out.format("%s: %s%n", violation.getPropertyPath(),
					violation.getMessage());
		}

		System.out.println("Author: Draft");
		aviolations = validator.validate(a, Draft.class);
		for (ConstraintViolation<Author> violation : aviolations) {
			System.out.format("%s: %s%n", violation.getPropertyPath(),
					violation.getMessage());
		}

	}

}
