package ch.rasc.nosql.redis;

import redis.clients.jedis.Jedis;

public class Subscriber {

	public static void main(String[] args) {
		try (Jedis jedis = new Jedis("192.168.178.35")) {
			MyPubSub myPubSub = new MyPubSub();
			jedis.subscribe(myPubSub, "channel1");
		}
	}

}
