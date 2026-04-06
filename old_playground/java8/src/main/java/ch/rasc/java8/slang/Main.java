package ch.rasc.java8.slang;

import static javaslang.API.List;
import static javaslang.API.Map;
import static javaslang.API.Queue;
import static javaslang.API.Vector;

import javaslang.Function1;
import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.collection.Queue;
import javaslang.collection.Vector;

public class Main {

	public static void main(String[] args) {

		List<Integer> seq = List(1, 2, 3, 4);
		System.out.println(seq.sum());

		Queue<String> queue = Queue();
		queue = queue.enqueue("one");

		System.out.println(queue.dequeue());

		Function1<Integer, Integer> plusOne = a -> a + 1;
		Function1<Integer, Integer> multiplyByTwo = a -> a * 2;

		Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);

		System.out.println(add1AndMultiplyBy2.apply(2));

		List<User> users = List(new User("john", User.Gender.MALE),
				new User("ralph", User.Gender.MALE),
				new User("olivia", User.Gender.FEMALE));

		Map<User.Gender, List<User>> usersGroupedByGender = users
				.groupBy(User::getGender);
		System.out.println(usersGroupedByGender);

		Map<Integer, String> m = Map(1, "one", 2, "two");
		System.out.println(m);

		Vector<Integer> v1 = Vector(1, 2, 3);
		Vector<Integer> v2 = v1.append(4);
		System.out.println(v1.remove(2));
		System.out.println(v1);
		System.out.println(v2);

	}

}
