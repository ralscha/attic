package ch.rasc.java8.slang;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.API.run;
import static javaslang.Predicates.instanceOf;
import static javaslang.Predicates.is;
import static javaslang.Predicates.isIn;

import javaslang.control.Option;

public class Matching {

	public static void main(String[] args) {
		Address adr = new Address("Road", 1);
		Person person = new Person("Carl", adr);

		if ("Carl".equals(person.getName())) {
			Address address = person.getAddress();
			if (address != null) {
				String street = address.getStreet();
				int number = address.getNumber();
				System.out.println(street);
				System.out.println(number);
			}
		}

		String result2 = Match(person).of(
				Case(StarterPatterns.Person($("Carl"), StarterPatterns.Address($(), $())),
						(name, address) -> name + " lives in " + address.getStreet() + " "
								+ address.getNumber()),
				Case($(), () -> "not found"));
		System.out.println(result2);

		int in = 4;
		Option<String> s = Match(in).option(Case(1, "one"), Case(is(2), "two"),
				Case(3, "three"), Case(isIn(4, 5, 6), "others"),
				Case(is(10).negate(), "not 10"));
		System.out.println(s.getOrElse("no match"));

		String arg = "-h";
		Match(arg).of(Case(isIn("-h", "--help"), o -> run(Matching::displayHelp)),
				Case(isIn("-v", "--version"), o -> run(Matching::displayVersion)),
				Case($(), o -> {
					throw new IllegalArgumentException(arg);
				}));

		Object obj = 2.2d;
		Number plusOne = Match(obj).of(Case(instanceOf(Integer.class), i -> i + 1),
				Case(instanceOf(Double.class), d -> d + 1), Case($(), o -> {
					throw new NumberFormatException();
				}));
		System.out.println(plusOne);

	}

	private static void displayHelp() {
		System.out.println("help");
	}

	private static void displayVersion() {
		System.out.println("version");
	}

}
