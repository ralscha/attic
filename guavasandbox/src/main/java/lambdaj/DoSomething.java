package lambdaj;

import static ch.lambdaj.Lambda.avgFrom;
import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.join;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;
import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.greaterThan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

public class DoSomething {

	public static void main(String[] args) {
		List<User> users = Lists.newArrayList(new User("admin", "admin@test.com", 31),
				new User("user1", "user1@test.com", 22), new User("user2",
						"user2@test.com", 27), new User("user3", "user3@test.com", 39));

		List<User> sorted = sort(users, on(User.class).getAge());
		System.out.println(join(sorted, "\n"));
		System.out.println(avgFrom(users).getAge());
		System.out.println();

		List<User> sorted2 = with(users).sort(on(User.class).getAge());
		System.out.println(join(sorted2, "\n"));

		Stopwatch stopWatch = Stopwatch.createStarted();
		stopWatch.start();
		List<User> older = filter(having(on(User.class).getAge(), greaterThan(30)), users);
		stopWatch.stop();

		for (User user : older) {
			System.out.println(user);
		}

		System.out.println(stopWatch.elapsed(TimeUnit.MILLISECONDS));

		stopWatch.reset();
		stopWatch.start();
		List<User> older2 = new ArrayList<>();
		for (User user : users) {
			if (user.getAge() > 30) {
				older2.add(user);
			}
		}
		stopWatch.stop();

		for (User user : older2) {
			System.out.println(user);
		}

		System.out.println(stopWatch.elapsed(TimeUnit.MILLISECONDS));
	}

}
