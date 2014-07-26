package lambdaj;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.google.common.base.Stopwatch;

public class Bench {

	public static void main(String[] args) {

		List<User> users = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 100000; i++) {
			users.add(new User(RandomStringUtils.random(20, true, false),
					RandomStringUtils.random(20, true, true), random.nextInt(99) + 1));
		}

		Stopwatch stopWatch = Stopwatch.createStarted();
		List<User> sorted = sort(users, on(User.class).getAge());
		stopWatch.stop();
		System.out.println("Lambdaj sort normal: "
				+ stopWatch.elapsed(TimeUnit.MILLISECONDS));

		stopWatch.reset();
		stopWatch.start();
		sorted = sort(users, on(User.class).getAge(), Collections.reverseOrder());
		stopWatch.stop();
		System.out.println("Lambdaj sort reverse: "
				+ stopWatch.elapsed(TimeUnit.MILLISECONDS));

		sorted = new ArrayList<>(users);
		UserAgeComparator agecomparator = new UserAgeComparator();
		stopWatch.reset();
		stopWatch.start();
		Collections.sort(sorted, agecomparator);
		stopWatch.stop();
		System.out.println("Java sort normal: "
				+ stopWatch.elapsed(TimeUnit.MILLISECONDS));

		sorted = new ArrayList<>(users);
		Comparator<User> reverseOrder = Collections.reverseOrder(agecomparator);
		stopWatch.reset();
		stopWatch.start();
		Collections.sort(sorted, reverseOrder);
		stopWatch.stop();
		System.out.println("Java sort reverse: "
				+ stopWatch.elapsed(TimeUnit.MILLISECONDS));

		SpelExpressionParser parser = new SpelExpressionParser();
		PropertyOrdering<User> ordering = new PropertyOrdering<>(
				parser.parseExpression("age"));
		stopWatch.reset();
		stopWatch.start();
		sorted = ordering.sortedCopy(users);
		stopWatch.stop();
		System.out.println("Spel sort normal: "
				+ stopWatch.elapsed(TimeUnit.MILLISECONDS));

		stopWatch.reset();
		stopWatch.start();
		sorted = ordering.reverse().sortedCopy(users);
		stopWatch.stop();
		System.out.println("Spel sort reverse: "
				+ stopWatch.elapsed(TimeUnit.MILLISECONDS));
	}

}
