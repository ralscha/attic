package ch.rasc.nosql.redis;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

public class Simple {

	public static void main(String[] args) throws InterruptedException {

		try (Jedis jedis = new Jedis("192.168.178.72", 6380)) {
			jedis.set("2", "two");
			jedis.set("3", "three");

			jedis.set("1", "one");

			String value = jedis.get("1");
			System.out.println("Key 1 = " + value);

			jedis.expire("1", 5);
			TimeUnit.SECONDS.sleep(7);

			value = jedis.get("1");
			System.out.println("Key 1 = " + value);

			System.out.println();
			System.out.println("List of Keys:");
			for (String key : jedis.keys("*")) {
				System.out.println(key);
			}

			jedis.set("counter", "100");
			jedis.incr("counter");
			jedis.incrBy("counter", 7);
			System.out.println("Counter: " + jedis.get("counter"));

			jedis.disconnect();
		}
	}

}
