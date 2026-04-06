package ch.rasc.jool;

import java.util.ArrayList;
import java.util.List;

import org.jooq.lambda.Agg;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;

public class Main {

	public static void main(String[] args) {
		List<Person> personsList = new ArrayList<>();

		personsList.add(new Person("John", "Doe", 25, 1.80, 80));
		personsList.add(new Person("Jane", "Doe", 30, 1.69, 60));
		personsList.add(new Person("John", "Smith", 35, 174, 70));

		Tuple result = Seq.seq(personsList).collect(Agg.count(), Agg.max(Person::getAge),
				Agg.min(Person::getHeight), Agg.avg(Person::getWeight));

		System.out.println(result);
	}

}
