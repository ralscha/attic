package ch.rasc.java8.slang;

import java.time.LocalDate;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.Tuple3;
import javaslang.match.annotation.Patterns;
import javaslang.match.annotation.Unapply;

@Patterns
class Starter {

	@Unapply
	static Tuple2<String, Address> Person(Person person) {
		return Tuple.of(person.getName(), person.getAddress());
	}

	@Unapply
	static Tuple2<String, Integer> Address(Address address) {
		return Tuple.of(address.getStreet(), address.getNumber());
	}

	@Unapply
	static Tuple3<Integer, Integer, Integer> LocalDate(LocalDate date) {
		return Tuple.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
	}
}