package ch.rasc.nosql.redis;

import java.util.Map;

import redis.clients.jedis.Jedis;

public class Hashes {

	public static void main(String[] args) {

		try (Jedis jedis = new Jedis("192.168.178.35")) {

			jedis.hset("user:1", "username", "sr");
			jedis.hset("user:1", "firstName", "Ralph");
			jedis.hset("user:1", "name", "Schaer");

			Map<String, String> data = jedis.hgetAll("user:1");
			for (String key : data.keySet()) {
				System.out.println(key + "->" + data.get(key));
			}
		}

	}

}
