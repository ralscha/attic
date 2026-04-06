package ch.rasc.nosql.redis;

import redis.clients.jedis.Jedis;

public class Sets {

	public static void main(String[] args) {
		try (Jedis jedis = new Jedis("192.168.178.35")) {

			jedis.sadd("numbers", "1", "2", "3", "4");

			Long numberOf = jedis.scard("numbers");
			System.out.println("Number of Entries: " + numberOf);

			System.out.println("Is 4 member: " + jedis.sismember("numbers", "4"));
			System.out.println("Is 5 member: " + jedis.sismember("numbers", "5"));

			jedis.srem("numbers", "4");
			System.out.println("Is 4 member: " + jedis.sismember("numbers", "4"));
		}
	}

}
