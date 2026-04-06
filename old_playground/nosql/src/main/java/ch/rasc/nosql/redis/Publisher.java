package ch.rasc.nosql.redis;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

public class Publisher {

	public static void main(String[] args) throws InterruptedException {
		try (Jedis jedis = new Jedis("192.168.178.35")) {

			for (int i = 0; i < 10; i++) {
				System.out.println("Publish: " + i);
				jedis.publish("channel1", String.valueOf(i));
				TimeUnit.SECONDS.sleep(1);
			}
		}

	}

}
