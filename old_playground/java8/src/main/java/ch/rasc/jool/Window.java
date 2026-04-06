package ch.rasc.jool;

import java.math.BigDecimal;
import java.util.Comparator;

import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

public class Window {

	// https://blog.jooq.org/2016/01/06/2016-will-be-the-year-remembered-as-when-java-finally-had-window-functions/
	public static void main(String[] args) {
		System.out.println(Seq.of("a", "a", "a", "b", "c", "c", "d", "e")
				.window(Comparator.naturalOrder())
				.map(w -> Tuple.tuple(w.value(), w.rowNumber(), w.rank(), w.denseRank()))
				.format());

		System.out.println(Seq.of("a", "a", "a", "b", "c", "c", "d", "e")
				.window(Comparator.naturalOrder()).map(w -> Tuple.tuple(w.value(), // v1
						w.count(), // v2
						w.median(), // v3
						w.lead(), // v4
						w.lag(), // v5
						w.toString() // v6
				)).format());

		System.out.println(Seq.of("a", "a", "a", "b", "c", "c", "d", "e")
				.window(Comparator.naturalOrder(), -1, 1) // frame here
				.map(w -> Tuple.tuple(w.value(), // v1
						w.count(), // v2
						w.median(), // v3
						w.lead(), // v4
						w.lag(), // v5
						w.toString() // v6
				)).format());

		BigDecimal currentBalance = new BigDecimal("19985.81");

		String output = Seq
				.of(Tuple.tuple(9997, "2014-03-18", new BigDecimal("99.17")),
						Tuple.tuple(9981, "2014-03-16", new BigDecimal("71.44")),
						Tuple.tuple(9979, "2014-03-16", new BigDecimal("-94.60")),
						Tuple.tuple(9977, "2014-03-16", new BigDecimal("-6.96")),
						Tuple.tuple(9971, "2014-03-15", new BigDecimal("-65.95")))
				.window(Comparator
						.comparing((Tuple3<Integer, String, BigDecimal> t) -> t.v1,
								Comparator.reverseOrder())
						.thenComparing(t -> t.v2), Long.MIN_VALUE, -1)
				.map(w -> w.value()
						.concat(currentBalance
								.subtract(w.sum(t -> t.v3).orElse(BigDecimal.ZERO))))
				.format();
		System.out.println(output);
	}

}
